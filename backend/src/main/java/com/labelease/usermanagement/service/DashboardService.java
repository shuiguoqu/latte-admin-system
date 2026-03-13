package com.labelease.usermanagement.service;

import com.labelease.usermanagement.dto.*;

import java.util.List;

/**
 * 看板服务接口
 */
public interface DashboardService {

    /** 获取看板全部数据 */
    DashboardData getDashboardData();

    /** 获取用户注册统计 */
    UserRegistrationStats getUserRegistrationStats();

    /** 获取月度注册趋势（近12个月） */
    List<MonthlyRegistration> getMonthlyRegistrationTrend();

    /** 获取订单状态统计 */
    List<OrderStatusStats> getOrderStatusStats();

    /** 获取金额最高的前10个订单 */
    List<TopOrderDTO> getTop10Orders();

    /** 获取系统总览 */
    SystemOverview getSystemOverview();
}
