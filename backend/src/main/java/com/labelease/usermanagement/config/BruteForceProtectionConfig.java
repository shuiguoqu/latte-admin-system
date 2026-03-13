package com.labelease.usermanagement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.brute-force")
public class BruteForceProtectionConfig {

    private int maxFailedAttempts = 5;

    private int lockDurationMinutes = 30;
}
