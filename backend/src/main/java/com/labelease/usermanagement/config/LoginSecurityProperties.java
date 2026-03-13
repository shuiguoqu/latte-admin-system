package com.labelease.usermanagement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录安全配置属性
 * 防爆破攻击相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.security")
public class LoginSecurityProperties {

    /**
     * 最大登录失败次数，超过则锁定账户
     */
    private int maxAttempts = 5;

    /**
     * 锁定持续时间（分钟）
     */
    private int lockDurationMinutes = 30;

    /**
     * 是否启用防爆破功能
     */
    private boolean enabled = true;
}
