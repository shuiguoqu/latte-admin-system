package com.labelease.usermanagement.service;

import com.labelease.usermanagement.entity.User;

import java.time.LocalDateTime;

/**
 * 登录尝试服务接口
 * 处理登录防爆破相关逻辑
 */
public interface LoginAttemptService {

    /**
     * 记录登录失败
     *
     * @param username 用户名
     */
    void recordFailedAttempt(String username);

    /**
     * 记录登录成功，清除失败计数
     *
     * @param username 用户名
     */
    void recordSuccessAttempt(String username);

    /**
     * 检查账户是否被锁定
     *
     * @param username 用户名
     * @return 是否被锁定
     */
    boolean isAccountLocked(String username);

    /**
     * 获取账户锁定信息
     *
     * @param username 用户名
     * @return 锁定信息，如果未锁定返回null
     */
    LockInfo getLockInfo(String username);

    /**
     * 手动解锁账户（管理员操作）
     *
     * @param userId 用户ID
     * @return 是否解锁成功
     */
    boolean unlockAccount(Long userId);

    /**
     * 锁定信息
     */
    class LockInfo {
        private final boolean locked;
        private final int remainingMinutes;
        private final int failedAttempts;
        private final LocalDateTime lockUntil;

        public LockInfo(boolean locked, int remainingMinutes, int failedAttempts, LocalDateTime lockUntil) {
            this.locked = locked;
            this.remainingMinutes = remainingMinutes;
            this.failedAttempts = failedAttempts;
            this.lockUntil = lockUntil;
        }

        public boolean isLocked() {
            return locked;
        }

        public int getRemainingMinutes() {
            return remainingMinutes;
        }

        public int getFailedAttempts() {
            return failedAttempts;
        }

        public LocalDateTime getLockUntil() {
            return lockUntil;
        }
    }
}
