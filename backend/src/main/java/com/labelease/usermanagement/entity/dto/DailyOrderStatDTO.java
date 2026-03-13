package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日订单统计DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyOrderStatDTO {

    /** 日期 */
    private LocalDate date;

    /** 订单数量 */
    private Long count;

    /** 订单金额 */
    private BigDecimal amount;
}
