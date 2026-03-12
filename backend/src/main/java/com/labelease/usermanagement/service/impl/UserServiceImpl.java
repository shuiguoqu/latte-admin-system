package com.labelease.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.mapper.UserMapper;
import com.labelease.usermanagement.service.ChangePasswordResult;
import com.labelease.usermanagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
    public ChangePasswordResult changePassword(String username, String oldPassword, String newPassword) {
        User user = getByUsername(username);
        if (user == null) {
            return ChangePasswordResult.USER_NOT_FOUND;
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ChangePasswordResult.OLD_PASSWORD_INCORRECT;
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return ChangePasswordResult.NEW_PASSWORD_SAME_AS_OLD;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
        return ChangePasswordResult.SUCCESS;
    }
}
