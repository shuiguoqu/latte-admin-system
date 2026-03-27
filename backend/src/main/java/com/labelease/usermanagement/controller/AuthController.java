package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.JwtUtil;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.config.LoginLockConfig;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.service.LoginLockService;
import com.labelease.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 认证控制器 - 登录认证
 * 采用无状态 JWT 方案，退出由前端清理 token 实现，无需后端 /logout 接口。
 * 密码验证采用 BCrypt 哈希比对，不再明文比较。
 * 登录成功后返回 token、username、realName、role、id 供前端鉴权使用。
 * 
 * 安全特性：
 * - 登录防爆破：连续失败达到阈值后锁定账号
 * - 锁定期间禁止登录，即使密码正确
 * - 并发安全：使用数据库原子操作保证计数准确
 */
@Tag(name = "认证管理", description = "登录与令牌管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final LoginLockService loginLockService;
    private final LoginLockConfig loginLockConfig;

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

        Long lockedMinutes = loginLockService.checkLocked(user);
        if (lockedMinutes != null) {
            return Result.error(423, 
                    String.format("账号已被锁定，请 %d 分钟后重试", lockedMinutes));
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            int failCount = loginLockService.recordLoginFailure(user.getId());
            int remaining = loginLockConfig.getMaxFailCount() - failCount;
            
            if (remaining > 0) {
                return Result.error(401, 
                        String.format("用户名或密码错误，剩余尝试次数：%d 次", remaining));
            } else {
                return Result.error(423, 
                        String.format("连续登录失败次数过多，账号已被锁定 %d 分钟", 
                                loginLockConfig.getLockDurationMinutes()));
            }
        }

        if (user.getStatus() != 1) {
            return Result.error(403, "账户已被禁用");
        }

        loginLockService.clearLoginFailure(user.getId());

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
