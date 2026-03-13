package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 用户注册趋势DTO - 按月统计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationTrendDTO {

    /** 年份-月份 (如: 2025-01) */
    private String month;

    /** 该月注册用户数 */
    private Long count;
}
