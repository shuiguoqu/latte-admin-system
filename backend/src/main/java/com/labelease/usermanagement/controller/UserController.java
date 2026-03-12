package com.labelease.usermanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.dto.ChangePasswordDTO;
import com.labelease.usermanagement.entity.Order;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.service.OrderService;
import com.labelease.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器 - CRUD + 分页查询 + 关联订单查询
 * 权限模型（R4 RBAC）：
 * ADMIN - 可管理全量用户（增删改查）
 * USER - 仅可查看/修改本人信息，禁止新增/删除/编辑他人
 */
@Tag(name = "用户管理", description = "用户的增删改查与关联查询")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @Operation(summary = "分页查询用户列表")
    @GetMapping
    public Result<?> list(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        String username = (String) request.getAttribute("currentUsername");

        if (!"ADMIN".equals(role)) {
            // 普通用户仅返回本人信息
            User self = userService.getByUsername(username);
            if (self == null) {
                return Result.error(404, "用户不存在");
            }
            Page<User> singlePage = new Page<>(1, 1, 1);
            singlePage.setRecords(Collections.singletonList(self));
            return Result.success(singlePage);
        }

        return Result.success(userService.pageUsers(current, size, keyword));
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        String username = (String) request.getAttribute("currentUsername");

        User user = userService.getById(id);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }

        // 普通用户只能查看自己
        if (!"ADMIN".equals(role) && !user.getUsername().equals(username)) {
            return Result.forbidden("权限不足，无法查看其他用户信息");
        }

        return Result.success(user);
    }

    @Operation(summary = "新增用户（仅管理员）")
    @PostMapping
    public Result<User> create(@Valid @RequestBody User user, HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        if (!"ADMIN".equals(role)) {
            return Result.forbidden("权限不足，仅管理员可创建用户");
        }

        userService.save(user);
        return Result.success("创建成功", user);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id, @Valid @RequestBody User user, HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        String username = (String) request.getAttribute("currentUsername");

        User existing = userService.getById(id);
        if (existing == null) {
            return Result.error(404, "用户不存在");
        }

        if (!"ADMIN".equals(role)) {
            // 普通用户只能修改自己
            if (!existing.getUsername().equals(username)) {
                return Result.forbidden("权限不足，无法修改其他用户信息");
            }
            // 普通用户禁止修改角色和状态
            user.setRole(null);
            user.setStatus(null);
        }

        user.setId(id);
        boolean updated = userService.updateById(user);
        if (!updated) {
            return Result.error(404, "用户不存在或更新失败");
        }
        return Result.success("更新成功", user);
    }

    @Operation(summary = "删除用户（仅管理员，逻辑删除）")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        if (!"ADMIN".equals(role)) {
            return Result.forbidden("权限不足，仅管理员可删除用户");
        }

        boolean removed = userService.removeById(id);
        if (!removed) {
            return Result.error(404, "用户不存在");
        }
        return Result.success("删除成功", null);
    }

    @Operation(summary = "查询用户详情及关联订单")
    @GetMapping("/{id}/orders")
    public Result<Map<String, Object>> getUserWithOrders(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("currentRole");
        String username = (String) request.getAttribute("currentUsername");

        User user = userService.getById(id);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }

        // 普通用户只能查看自己的订单
        if (!"ADMIN".equals(role) && !user.getUsername().equals(username)) {
            return Result.forbidden("权限不足，无法查看其他用户的订单");
        }

        List<Order> orders = orderService.listByUserId(id);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("orders", orders);
        return Result.success(result);
    }

    @Operation(summary = "修改当前用户密码")
    @PutMapping("/change-password")
    public Result<Void> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        String username = (String) request.getAttribute("currentUsername");
        User currentUser = userService.getByUsername(username);
        if (currentUser == null) {
            return Result.error(404, "用户不存在");
        }

        boolean success = userService.changePassword(currentUser.getId(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        if (!success) {
            return Result.error(400, "旧密码错误或修改失败");
        }
        return Result.success("密码修改成功", null);
    }
}
