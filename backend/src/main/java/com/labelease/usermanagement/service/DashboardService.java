package com.labelease.usermanagement.service;

import com.labelease.usermanagement.entity.dto.*;

import java.util.List;

/**
 * 看板服务接口
 */
public interface DashboardService {

    /**
     * 获取系统总览数据
     */
    DashboardOverviewDTO getOverview();

    /**
     * 获取用户注册趋势（近12个月）
     */
    List<UserRegistrationTrendDTO> getUserRegistrationTrend();

    /**
     * 获取订单状态统计
     */
    List<OrderStatusStatDTO> getOrderStatusStats();

    /**
     * 获取金额最高的前N个订单
     */
    List<TopOrderDTO> getTopOrders(int limit);

    /**
     * 获取完整看板数据
     */
    DashboardDataDTO getFullDashboardData();

    /**
     * 验证看板数据与数据库一致性
     */
    DataValidationDTO validateDashboardData();

    /**
     * 获取每日订单统计（近30天）
     */
    List<DailyOrderStatDTO> getDailyOrderStats();

    /**
     * 获取订单金额分布统计
     */
    OrderAmountDistributionDTO getOrderAmountDistribution();
}
