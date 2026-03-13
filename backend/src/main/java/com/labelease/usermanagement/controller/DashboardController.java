package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.service.DashboardService;
import com.labelease.usermanagement.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "数据看板", description = "Dashboard统计数据接口")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "获取用户注册统计", description = "今日、本周、本月注册用户数")
    @GetMapping("/user-register-stats")
    public Result<UserRegisterStatsVO> getUserRegisterStats() {
        return Result.success(dashboardService.getUserRegisterStats());
    }

    @Operation(summary = "获取近一年每月注册统计", description = "用于趋势图展示")
    @GetMapping("/monthly-register")
    public Result<List<MonthlyRegisterVO>> getMonthlyRegister() {
        return Result.success(dashboardService.getMonthlyRegisterStats());
    }

    @Operation(summary = "获取订单状态统计", description = "各种状态的订单数量")
    @GetMapping("/order-status-stats")
    public Result<List<OrderStatusStatsVO>> getOrderStatusStats() {
        return Result.success(dashboardService.getOrderStatusStats());
    }

    @Operation(summary = "获取金额最高的前10个订单", description = "按金额降序排列")
    @GetMapping("/top-orders")
    public Result<List<TopOrderVO>> getTopOrders() {
        return Result.success(dashboardService.getTop10OrdersByAmount());
    }

    @Operation(summary = "获取系统总览数据", description = "总用户数、总订单数、总金额")
    @GetMapping("/overview")
    public Result<SystemOverviewVO> getSystemOverview() {
        return Result.success(dashboardService.getSystemOverview());
    }
}
