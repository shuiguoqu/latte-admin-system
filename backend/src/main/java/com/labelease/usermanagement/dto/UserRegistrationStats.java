package com.labelease.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册统计DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationStats {

    /** 今日新注册用户数 */
    private Long todayCount;

    /** 本周新注册用户数 */
    private Long weekCount;

    /** 本月新注册用户数 */
    private Long monthCount;
}
