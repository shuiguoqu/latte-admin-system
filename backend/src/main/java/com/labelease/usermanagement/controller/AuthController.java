package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.JwtUtil;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.labelease.usermanagement.config.BruteForceProtectionConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 认证控制器 - 登录认证
 * 采用无状态 JWT 方案，退出由前端清理 token 实现，无需后端 /logout 接口。
 * 密码验证采用 BCrypt 哈希比对，不再明文比较。
 * 登录成功后返回 token、username、realName、role、id 供前端鉴权使用。
 */
@Tag(name = "认证管理", description = "登录与令牌管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final BruteForceProtectionConfig bruteForceProtectionConfig;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return Result.badRequest("用户名和密码不能为空");
        }

        User user = userService.getByUsername(username);
        if (user == null) {
            return Result.error(401, "用户名或密码错误");
        }

        if (user.getLockUntil() != null && user.getLockUntil().isAfter(LocalDateTime.now())) {
            long minutes = Duration.between(LocalDateTime.now(), user.getLockUntil()).toMinutes();
            if (minutes <= 0) {
                minutes = 1;
            }
            return Result.error(423, "账户已被锁定，请 " + minutes + " 分钟后再试");
        }

        if (user.getStatus() != 1) {
            return Result.error(403, "账户已被禁用");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            userService.increaseLoginFailedCount(username);
            int failedCount = user.getLoginFailedCount() == null ? 1 : user.getLoginFailedCount() + 1;
            int remaining = bruteForceProtectionConfig.getMaxFailedAttempts() - failedCount;
            if (remaining > 0) {
                return Result.error(401, "用户名或密码错误，还有 " + remaining + " 次机会");
            } else {
                userService.lockAccount(username, bruteForceProtectionConfig.getLockDurationMinutes());
                return Result.error(423, "密码错误次数过多，账户已被锁定 " + bruteForceProtectionConfig.getLockDurationMinutes() + " 分钟");
            }
        }

        userService.resetLoginFailedCount(username);

        String role = user.getRole() != null ? user.getRole() : "USER";
        String token = jwtUtil.generateToken(username, role);
        return Result.success("登录成功", Map.of(
                "token", token,
                "username", user.getUsername(),
                "realName", user.getRealName(),
                "role", role,
                "id", user.getId()));
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("服务运行正常");
    }
}
