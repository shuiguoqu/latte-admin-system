package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单状态统计DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusStats {

    /** 订单状态：0-待支付，1-已支付，2-已发货，3-已完成，4-已取消 */
    private Integer status;

    /** 状态名称 */
    private String statusName;

    /** 订单数量 */
    private Long count;

    /** 订单总金额 */
    private BigDecimal totalAmount;
}
