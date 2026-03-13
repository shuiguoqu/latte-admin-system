package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import java.util.List;

/**
 * 看板完整数据DTO - 聚合所有看板数据
 */
@Data
public class DashboardDataDTO {

    /** 系统总览数据 */
    private DashboardOverviewDTO overview;

    /** 近一年用户注册趋势（按月） */
    private List<UserRegistrationTrendDTO> userRegistrationTrend;

    /** 订单状态分布统计 */
    private List<OrderStatusStatDTO> orderStatusStats;

    /** 金额最高的前10个订单 */
    private List<TopOrderDTO> topOrders;
}
