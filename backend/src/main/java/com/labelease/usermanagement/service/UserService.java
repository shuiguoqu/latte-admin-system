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

    /**
     * 修改用户密码
     *
     * @param username    用户名
     * @param oldPassword 旧密码（明文）
     * @param newPassword 新密码（明文）
     * @return true-修改成功, false-修改失败
     * @throws com.labelease.usermanagement.common.BusinessException 业务异常（用户不存在、旧密码错误等）
     */
    boolean changePassword(String username, String oldPassword, String newPassword);
}
