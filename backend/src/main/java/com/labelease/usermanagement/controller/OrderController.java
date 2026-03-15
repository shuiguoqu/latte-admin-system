package com.labelease.usermanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.entity.Order;
import com.labelease.usermanagement.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理控制器 - CRUD + 分页查询
 */
@Tag(name = "订单管理", description = "订单的增删改查")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "分页查询订单列表")
    @GetMapping
    public Result<Page<Order>> list(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "最低金额") @RequestParam(required = false) BigDecimal minAmount,
            @Parameter(description = "最高金额") @RequestParam(required = false) BigDecimal maxAmount) {
        return Result.success(orderService.pageOrders(current, size, keyword, minAmount, maxAmount));
    }

    @Operation(summary = "根据ID查询订单")
    @GetMapping("/{id}")
    public Result<Order> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order == null) {
            return Result.error(404, "订单不存在");
        }
        return Result.success(order);
    }

    @Operation(summary = "根据用户ID查询订单")
    @GetMapping("/user/{userId}")
    public Result<List<Order>> listByUserId(@PathVariable Long userId) {
        return Result.success(orderService.listByUserId(userId));
    }

    @Operation(summary = "新增订单")
    @PostMapping
    public Result<Order> create(@Valid @RequestBody Order order) {
        orderService.save(order);
        return Result.success("创建成功", order);
    }

    @Operation(summary = "更新订单")
    @PutMapping("/{id}")
    public Result<Order> update(@PathVariable Long id, @Valid @RequestBody Order order) {
        order.setId(id);
        boolean updated = orderService.updateById(order);
        if (!updated) {
            return Result.error(404, "订单不存在或更新失败");
        }
        return Result.success("更新成功", order);
    }

    @Operation(summary = "删除订单（逻辑删除）")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        boolean removed = orderService.removeById(id);
        if (!removed) {
            return Result.error(404, "订单不存在");
        }
        return Result.success("删除成功", null);
    }
}
