package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 订单状态统计DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusStatDTO {

    /** 状态码 */
    private Integer status;

    /** 状态名称 */
    private String statusName;

    /** 该状态订单数量 */
    private Long count;

    /** 该状态订单金额 */
    private java.math.BigDecimal amount;
}
