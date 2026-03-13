package com.labelease.usermanagement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录锁定配置
 * 支持通过 application.yml 配置锁定次数和锁定时长
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.lock")
public class LoginLockConfig {

    /** 最大失败次数，超过后锁定账号 */
    private int maxFailCount = 5;

    /** 锁定时长（分钟） */
    private int lockDurationMinutes = 30;
}
