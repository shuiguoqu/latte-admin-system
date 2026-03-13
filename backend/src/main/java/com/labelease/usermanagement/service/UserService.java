package com.labelease.usermanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.labelease.usermanagement.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /** 分页查询用户（支持关键词搜索） */
    Page<User> pageUsers(int current, int size, String keyword);

    /** 根据用户名查询 */
    User getByUsername(String username);

    /** 增加登录失败次数 */
    void increaseLoginFailedCount(String username);

    /** 重置登录失败次数 */
    void resetLoginFailedCount(String username);

    /** 锁定账户 */
    void lockAccount(String username, int lockDurationMinutes);

    /** 解锁账户 */
    void unlockAccount(String username);
}
