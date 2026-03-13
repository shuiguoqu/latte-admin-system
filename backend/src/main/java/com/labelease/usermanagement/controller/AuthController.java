package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.JwtUtil;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.config.LoginSecurityProperties;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.service.LoginAttemptService;
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
 * 集成登录防爆破功能：连续输错密码达到一定次数后锁定账户
 */
@Tag(name = "认证管理", description = "登录与令牌管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final LoginSecurityProperties securityProperties;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return Result.badRequest("用户名和密码不能为空");
        }

        // 检查账户是否被锁定
        if (securityProperties.isEnabled() && loginAttemptService.isAccountLocked(username)) {
            LoginAttemptService.LockInfo lockInfo = loginAttemptService.getLockInfo(username);
            int remainingMinutes = lockInfo.getRemainingMinutes();
            return Result.error(423, "账户已被锁定，请" + remainingMinutes + "分钟后再试");
        }

        User user = userService.getByUsername(username);

        // 验证密码
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            // 记录登录失败
            if (securityProperties.isEnabled()) {
                loginAttemptService.recordFailedAttempt(username);

                // 获取更新后的锁定信息
                LoginAttemptService.LockInfo lockInfo = loginAttemptService.getLockInfo(username);
                if (lockInfo.isLocked()) {
                    return Result.error(423, "密码错误次数过多，账户已被锁定，请" + lockInfo.getRemainingMinutes() + "分钟后再试");
                } else {
                    int remainingAttempts = securityProperties.getMaxAttempts() - lockInfo.getFailedAttempts();
                    return Result.error(401, "用户名或密码错误，还剩 " + remainingAttempts + " 次尝试机会");
                }
            }
            return Result.error(401, "用户名或密码错误");
        }

        // 检查账户状态
        if (user.getStatus() != 1) {
            return Result.error(403, "账户已被禁用");
        }

        // 再次检查锁定状态（防止在验证密码期间被锁定）
        if (securityProperties.isEnabled() && loginAttemptService.isAccountLocked(username)) {
            LoginAttemptService.LockInfo lockInfo = loginAttemptService.getLockInfo(username);
            return Result.error(423, "账户已被锁定，请" + lockInfo.getRemainingMinutes() + "分钟后再试");
        }

        // 登录成功，清除失败计数
        if (securityProperties.isEnabled()) {
            loginAttemptService.recordSuccessAttempt(username);
        }

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
