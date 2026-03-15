package com.labelease.usermanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.labelease.usermanagement.entity.Order;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService extends IService<Order> {

    /** 分页查询订单 */
    Page<Order> pageOrders(int current, int size, String keyword);

    /** 根据用户ID查询订单列表 */
    List<Order> listByUserId(Long userId);

    /**
     * 修改订单状态
     *
     * @param orderId      订单ID
     * @param targetStatus 目标状态
     * @return 更新后的订单
     * @throws IllegalStateException 如果状态转换不合法
     */
    Order updateOrderStatus(Long orderId, Integer targetStatus);
}
