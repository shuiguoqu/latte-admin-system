package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 系统总览数据DTO
 */
@Data
public class DashboardOverviewDTO {

    /** 总用户数 */
    private Long totalUsers;

    /** 总订单数 */
    private Long totalOrders;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 今日新增用户数 */
    private Long todayNewUsers;

    /** 本周新增用户数 */
    private Long weekNewUsers;

    /** 本月新增用户数 */
    private Long monthNewUsers;
}
