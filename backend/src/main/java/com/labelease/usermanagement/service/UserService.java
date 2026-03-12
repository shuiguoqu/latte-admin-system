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

    /** 修改用户密码
     * @return 0-成功, 1-用户不存在, 2-旧密码错误, 3-修改失败 */
    int changePassword(Long userId, String oldPassword, String newPassword);
}
