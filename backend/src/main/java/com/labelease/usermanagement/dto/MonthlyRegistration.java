package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 月度注册统计DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRegistration {

    /** 年份 */
    private Integer year;

    /** 月份 */
    private Integer month;

    /** 注册人数 */
    private Long count;
}
