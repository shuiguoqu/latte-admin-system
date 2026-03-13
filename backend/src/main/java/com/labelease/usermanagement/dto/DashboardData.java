package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 看板总数据DTO - 聚合所有看板数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {

    /** 系统总览 */
    private SystemOverview overview;

    /** 用户注册统计 */
    private UserRegistrationStats userRegistration;

    /** 月度注册趋势（近12个月） */
    private List<MonthlyRegistration> monthlyTrend;

    /** 订单状态统计 */
    private List<OrderStatusStats> orderStatusStats;

    /** 金额最高的前10个订单 */
    private List<TopOrderDTO> topOrders;
}
