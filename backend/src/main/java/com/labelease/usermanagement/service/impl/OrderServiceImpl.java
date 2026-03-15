package com.labelease.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labelease.usermanagement.entity.Order;
import com.labelease.usermanagement.mapper.OrderMapper;
import com.labelease.usermanagement.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单服务实现
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page<Order> pageOrders(int current, int size, String keyword, BigDecimal minAmount, BigDecimal maxAmount) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Order::getOrderNo, keyword)
                   .or().like(Order::getProductName, keyword));
        }
        if (minAmount != null) {
            wrapper.ge(Order::getAmount, minAmount);
        }
        if (maxAmount != null) {
            wrapper.le(Order::getAmount, maxAmount);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public List<Order> listByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
}
