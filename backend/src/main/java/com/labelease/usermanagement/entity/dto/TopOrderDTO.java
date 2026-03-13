package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 高额订单DTO - 用于展示TOP订单
 */
@Data
public class TopOrderDTO {

    /** 订单ID */
    private Long id;

    /** 订单编号 */
    private String orderNo;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String username;

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
