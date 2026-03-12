package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.JwtUtil;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.dto.ChangePasswordRequest;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.service.ChangePasswordResult;
import com.labelease.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "认证管理", description = "登录与令牌管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return Result.badRequest("用户名和密码不能为空");
        }

        User user = userService.getByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return Result.error(401, "用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            return Result.error(403, "账户已被禁用");
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

    @Operation(summary = "修改密码")
    @PostMapping("/change-password")
    public Result<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getAttribute("currentUsername");
        if (username == null) {
            return Result.error(401, "未登录");
        }

        ChangePasswordResult result = userService.changePassword(
                username,
                request.getOldPassword(),
                request.getNewPassword());

        if (!result.isSuccess()) {
            return Result.error(400, result.getMessage());
        }
        return Result.success(result.getMessage(), null);
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("服务运行正常");
    }
}
