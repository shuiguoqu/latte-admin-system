package com.labelease.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.labelease.usermanagement.common.OrderStatusMachine;
import com.labelease.usermanagement.entity.Order;
import com.labelease.usermanagement.mapper.OrderMapper;
import com.labelease.usermanagement.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 订单服务实现
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page<Order> pageOrders(int current, int size, String keyword) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Order::getOrderNo, keyword)
                   .or().like(Order::getProductName, keyword);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public List<Order> listByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, Integer targetStatus) {
        // 查询订单
        Order order = getById(orderId);
        if (order == null) {
            throw new IllegalStateException("订单不存在");
        }

        // 使用状态机校验状态转换是否合法
        OrderStatusMachine.validateTransition(order.getStatus(), targetStatus);

        // 更新状态
        order.setStatus(targetStatus);
        updateById(order);

        return order;
    }
}
