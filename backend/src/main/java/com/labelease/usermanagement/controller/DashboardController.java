package com.labelease.usermanagement.controller;

import com.labelease.usermanagement.common.Result;
import com.labelease.usermanagement.entity.dto.*;
import com.labelease.usermanagement.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据看板控制器 - 提供看板统计数据接口
 */
@Tag(name = "数据看板", description = "系统数据统计与可视化")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "获取完整看板数据", description = "一次性获取所有看板数据，包括总览、趋势、订单统计等")
    @GetMapping
    public Result<DashboardDataDTO> getDashboardData() {
        return Result.success(dashboardService.getFullDashboardData());
    }

    @Operation(summary = "获取系统总览数据", description = "获取总用户数、总订单数、总金额、今日/本周/本月新增用户数")
    @GetMapping("/overview")
    public Result<DashboardOverviewDTO> getOverview() {
        return Result.success(dashboardService.getOverview());
    }

    @Operation(summary = "获取用户注册趋势", description = "获取近12个月的用户注册趋势数据，用于绘制趋势图")
    @GetMapping("/user-trend")
    public Result<List<UserRegistrationTrendDTO>> getUserRegistrationTrend() {
        return Result.success(dashboardService.getUserRegistrationTrend());
    }

    @Operation(summary = "获取订单状态统计", description = "获取各状态订单数量和金额分布")
    @GetMapping("/order-stats")
    public Result<List<OrderStatusStatDTO>> getOrderStatusStats() {
        return Result.success(dashboardService.getOrderStatusStats());
    }

    @Operation(summary = "获取TOP订单", description = "获取金额最高的前N个订单")
    @GetMapping("/top-orders")
    public Result<List<TopOrderDTO>> getTopOrders(
            @Parameter(description = "返回数量限制，默认10") @RequestParam(defaultValue = "10") int limit) {
        return Result.success(dashboardService.getTopOrders(limit));
    }

    @Operation(summary = "验证看板数据一致性", description = "对比看板展示数据与数据库原始数据，验证数据准确性")
    @GetMapping("/validate")
    public Result<DataValidationDTO> validateDashboardData() {
        return Result.success(dashboardService.validateDashboardData());
    }

    @Operation(summary = "获取每日订单统计", description = "获取近30天每日订单数量和金额统计")
    @GetMapping("/daily-stats")
    public Result<List<DailyOrderStatDTO>> getDailyOrderStats() {
        return Result.success(dashboardService.getDailyOrderStats());
    }

    @Operation(summary = "获取订单金额分布", description = "获取订单金额区间分布统计")
    @GetMapping("/amount-distribution")
    public Result<OrderAmountDistributionDTO> getOrderAmountDistribution() {
        return Result.success(dashboardService.getOrderAmountDistribution());
    }
}
