package com.labelease.usermanagement.service.impl;

import com.labelease.usermanagement.config.LoginSecurityProperties;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.mapper.UserMapper;
import com.labelease.usermanagement.service.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 登录尝试服务实现
 * 处理登录防爆破相关逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final UserMapper userMapper;
    private final LoginSecurityProperties securityProperties;

    @Override
    @Transactional
    public void recordFailedAttempt(String username) {
        if (!securityProperties.isEnabled()) {
            return;
        }

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }

        // 检查是否已经被锁定
        if (isAccountLocked(username)) {
            return;
        }

        int newFailedAttempts = (user.getLoginFailedAttempts() != null ? user.getLoginFailedAttempts() : 0) + 1;
        LocalDateTime lockUntil = null;

        // 如果达到最大失败次数，锁定账户
        if (newFailedAttempts >= securityProperties.getMaxAttempts()) {
            lockUntil = LocalDateTime.now().plusMinutes(securityProperties.getLockDurationMinutes());
            log.warn("用户 [{}] 登录失败次数达到 {} 次，账户被锁定 {} 分钟",
                    username, newFailedAttempts, securityProperties.getLockDurationMinutes());
        }

        userMapper.updateLoginAttempts(user.getId(), newFailedAttempts, lockUntil);
    }

    @Override
    @Transactional
    public void recordSuccessAttempt(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return;
        }

        // 登录成功，清除失败计数和锁定状态
        if (user.getLoginFailedAttempts() != null && user.getLoginFailedAttempts() > 0) {
            userMapper.updateLoginAttempts(user.getId(), 0, null);
            log.info("用户 [{}] 登录成功，清除登录失败计数", username);
        }
    }

    @Override
    public boolean isAccountLocked(String username) {
        if (!securityProperties.isEnabled()) {
            return false;
        }

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return false;
        }

        LocalDateTime lockUntil = user.getLockUntil();
        if (lockUntil == null) {
            return false;
        }

        // 如果锁定时间已过，自动解锁
        if (LocalDateTime.now().isAfter(lockUntil)) {
            // 异步清除锁定状态
            userMapper.updateLoginAttempts(user.getId(), 0, null);
            return false;
        }

        return true;
    }

    @Override
    public LockInfo getLockInfo(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return new LockInfo(false, 0, 0, null);
        }

        LocalDateTime lockUntil = user.getLockUntil();
        int failedAttempts = user.getLoginFailedAttempts() != null ? user.getLoginFailedAttempts() : 0;

        if (lockUntil == null || LocalDateTime.now().isAfter(lockUntil)) {
            return new LockInfo(false, 0, failedAttempts, null);
        }

        int remainingMinutes = (int) ChronoUnit.MINUTES.between(LocalDateTime.now(), lockUntil);
        if (remainingMinutes < 1) {
            remainingMinutes = 1;
        }

        return new LockInfo(true, remainingMinutes, failedAttempts, lockUntil);
    }

    @Override
    @Transactional
    public boolean unlockAccount(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }

        userMapper.updateLoginAttempts(userId, 0, null);
        log.info("管理员手动解锁用户 [{}]", user.getUsername());
        return true;
    }
}
