package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 系统总览DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemOverview {

    /** 总用户数 */
    private Long totalUsers;

    /** 总订单数 */
    private Long totalOrders;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 已完成订单数 */
    private Long completedOrders;

    /** 已完成订单总金额 */
    private BigDecimal completedAmount;
}
