package com.labelease.usermanagement.service;

import com.labelease.usermanagement.config.LoginLockConfig;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 登录锁定服务
 * 管理登录失败次数和账号锁定状态
 * 
 * 并发安全设计：
 * 1. 使用数据库原子操作（UPDATE ... SET count = count + 1）保证计数准确
 * 2. 使用事务确保锁定操作的原子性
 * 3. 锁定检查使用 FOR UPDATE 行锁防止并发穿透
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLockService {

    private final LoginLockConfig lockConfig;
    private final UserMapper userMapper;

    /**
     * 检查账号是否被锁定
     * @param user 用户
     * @return 锁定状态，null表示未锁定，否则返回剩余锁定时间（分钟）
     */
    public Long checkLocked(User user) {
        if (user.getLockedUntil() == null) {
            return null;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(user.getLockedUntil())) {
            return null;
        }
        
        return ChronoUnit.MINUTES.between(now, user.getLockedUntil()) + 1;
    }

    /**
     * 记录登录失败（原子操作，并发安全）
     * 使用数据库原子操作保证计数准确，即使并发请求也能正确累加
     * 
     * @param userId 用户ID
     * @return 更新后的失败次数
     */
    @Transactional(rollbackFor = Exception.class)
    public int recordLoginFailure(Long userId) {
        userMapper.incrementLoginFailCount(userId);
        
        User user = userMapper.selectById(userId);
        int failCount = user.getLoginFailCount() != null ? user.getLoginFailCount() : 0;
        
        if (failCount >= lockConfig.getMaxFailCount()) {
            LocalDateTime lockedUntil = LocalDateTime.now().plusMinutes(lockConfig.getLockDurationMinutes());
            userMapper.setLockedUntil(userId, lockedUntil);
            log.warn("账号 {} 因连续 {} 次登录失败被锁定至 {}", 
                    user.getUsername(), failCount, lockedUntil);
        }
        
        return failCount;
    }

    /**
     * 清除登录失败记录（登录成功后调用）
     * @param userId 用户ID
     */
    public void clearLoginFailure(Long userId) {
        userMapper.clearLoginFailure(userId);
        log.info("用户ID {} 登录成功，已清除失败记录", userId);
    }

    /**
     * 解锁账号（管理员操作）
     * @param userId 用户ID
     * @return 是否解锁成功
     */
    public boolean unlockAccount(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        
        int affected = userMapper.clearLoginFailure(userId);
        if (affected > 0) {
            log.info("管理员解锁账号: {}", user.getUsername());
            return true;
        }
        return false;
    }

    /**
     * 获取剩余可尝试次数
     */
    public int getRemainingAttempts(User user) {
        int failCount = user.getLoginFailCount() == null ? 0 : user.getLoginFailCount();
        return Math.max(0, lockConfig.getMaxFailCount() - failCount);
    }
}
