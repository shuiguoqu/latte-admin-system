package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 金额最高订单DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopOrderDTO {

    /** 订单ID */
    private Long id;

    /** 订单编号 */
    private String orderNo;

    /** 用户名 */
    private String username;

    /** 用户真实姓名 */
    private String realName;

    /** 商品名称 */
    private String productName;

    /** 订单金额 */
    private BigDecimal amount;

    /** 订单状态 */
    private Integer status;

    /** 状态名称 */
    private String statusName;

    /** 创建时间 */
    private LocalDateTime createTime;
}
