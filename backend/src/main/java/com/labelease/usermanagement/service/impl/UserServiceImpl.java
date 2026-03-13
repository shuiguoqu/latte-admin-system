package com.labelease.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labelease.usermanagement.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.labelease.usermanagement.mapper.UserMapper;
import com.labelease.usermanagement.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Page<User> pageUsers(int current, int size, String keyword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(User::getUsername, keyword)
                   .or().like(User::getRealName, keyword)
                   .or().like(User::getEmail, keyword)
                   .or().like(User::getPhone, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public void increaseLoginFailedCount(String username) {
        User user = getByUsername(username);
        if (user != null) {
            user.setLoginFailedCount(user.getLoginFailedCount() == null ? 1 : user.getLoginFailedCount() + 1);
            user.setUpdateTime(LocalDateTime.now());
            updateById(user);
        }
    }

    @Override
    @Transactional
    public void resetLoginFailedCount(String username) {
        User user = getByUsername(username);
        if (user != null) {
            user.setLoginFailedCount(0);
            user.setLockUntil(null);
            user.setUpdateTime(LocalDateTime.now());
            updateById(user);
        }
    }

    @Override
    @Transactional
    public void lockAccount(String username, int lockDurationMinutes) {
        User user = getByUsername(username);
        if (user != null) {
            user.setLockUntil(LocalDateTime.now().plusMinutes(lockDurationMinutes));
            user.setUpdateTime(LocalDateTime.now());
            updateById(user);
        }
    }

    @Override
    @Transactional
    public void unlockAccount(String username) {
        User user = getByUsername(username);
        if (user != null) {
            user.setLoginFailedCount(0);
            user.setLockUntil(null);
            user.setUpdateTime(LocalDateTime.now());
            updateById(user);
        }
    }
}
