package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.dto.*;
import com.labelease.usermanagement.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据看板控制器
 * 提供系统运营数据统计接口
 */
@Tag(name = "数据看板", description = "系统运营数据统计与可视化")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "获取看板全部数据", description = "一次性获取所有看板数据，减少请求次数")
    @GetMapping
    public Result<DashboardData> getDashboardData(HttpServletRequest request) {
        return Result.success(dashboardService.getDashboardData());
    }

    @Operation(summary = "获取用户注册统计", description = "今日、本周、本月新注册用户数")
    @GetMapping("/user-registration")
    public Result<UserRegistrationStats> getUserRegistrationStats() {
        return Result.success(dashboardService.getUserRegistrationStats());
    }

    @Operation(summary = "获取月度注册趋势", description = "近12个月每月注册人数趋势")
    @GetMapping("/monthly-trend")
    public Result<List<MonthlyRegistration>> getMonthlyTrend() {
        return Result.success(dashboardService.getMonthlyRegistrationTrend());
    }

    @Operation(summary = "获取订单状态统计", description = "各状态订单数量和金额统计")
    @GetMapping("/order-status")
    public Result<List<OrderStatusStats>> getOrderStatusStats() {
        return Result.success(dashboardService.getOrderStatusStats());
    }

    @Operation(summary = "获取金额最高订单TOP10", description = "按金额降序排列的前10个订单")
    @GetMapping("/top-orders")
    public Result<List<TopOrderDTO>> getTopOrders() {
        return Result.success(dashboardService.getTop10Orders());
    }

    @Operation(summary = "获取系统总览", description = "总用户数、总订单数、总金额等核心指标")
    @GetMapping("/overview")
    public Result<SystemOverview> getSystemOverview() {
        return Result.success(dashboardService.getSystemOverview());
    }
}
