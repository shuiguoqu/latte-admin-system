package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 订单金额分布统计DTO
 */
@Data
public class OrderAmountDistributionDTO {

    /** 低金额订单数 (<100) */
    private Long lowCount;

    /** 中金额订单数 (100-1000) */
    private Long midCount;

    /** 高金额订单数 (1000-5000) */
    private Long highCount;

    /** VIP订单数 (>=5000) */
    private Long vipCount;

    /** 平均订单金额 */
    private BigDecimal avgAmount;

    /** 最小订单金额 */
    private BigDecimal minAmount;

    /** 最大订单金额 */
    private BigDecimal maxAmount;
}
