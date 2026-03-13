package com.labelease.usermanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.labelease.usermanagement.entity.Order;
import com.labelease.usermanagement.entity.User;
import com.labelease.usermanagement.mapper.OrderMapper;
import com.labelease.usermanagement.mapper.UserMapper;
import com.labelease.usermanagement.service.DashboardService;
import com.labelease.usermanagement.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    @Override
    public UserRegisterStatsVO getUserRegisterStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("create_time", startOfMonth);
        wrapper.select(
            "SUM(CASE WHEN DATE(create_time) = CURDATE() THEN 1 ELSE 0 END) as todayCount",
            "SUM(CASE WHEN YEARWEEK(create_time, 1) = YEARWEEK(CURDATE(), 1) THEN 1 ELSE 0 END) as weekCount",
            "COUNT(*) as monthCount"
        );
        Map<String, Object> map = userMapper.selectMaps(wrapper).get(0);

        UserRegisterStatsVO vo = new UserRegisterStatsVO();
        vo.setTodayCount(((Number) map.get("todayCount")).longValue());
        vo.setWeekCount(((Number) map.get("weekCount")).longValue());
        vo.setMonthCount(((Number) map.get("monthCount")).longValue());
        return vo;
    }

    @Override
    public List<MonthlyRegisterVO> getMonthlyRegisterStats() {
        List<MonthlyRegisterVO> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth now = YearMonth.now();

        LocalDateTime startOfYear = now.minusMonths(11).atDay(1).atStartOfDay();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("create_time", startOfYear);
        wrapper.select("DATE_FORMAT(create_time, '%Y-%m') as month", "COUNT(*) as count");
        wrapper.groupBy("month");
        wrapper.orderByAsc("month");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);

        Map<String, Long> countMap = new java.util.HashMap<>();
        for (Map<String, Object> map : maps) {
            countMap.put((String) map.get("month"), ((Number) map.get("count")).longValue());
        }

        for (int i = 11; i >= 0; i--) {
            YearMonth yearMonth = now.minusMonths(i);
            String monthStr = yearMonth.format(formatter);
            MonthlyRegisterVO vo = new MonthlyRegisterVO();
            vo.setMonth(monthStr);
            vo.setCount(countMap.getOrDefault(monthStr, 0L));
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<OrderStatusStatsVO> getOrderStatusStats() {
        List<OrderStatusStatsVO> result = new ArrayList<>();
        String[] statusNames = {"待支付", "已支付", "已发货", "已完成", "已取消"};

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.select("status", "COUNT(*) as count");
        wrapper.groupBy("status");
        List<Map<String, Object>> maps = orderMapper.selectMaps(wrapper);

        Map<Integer, Long> countMap = new java.util.HashMap<>();
        for (Map<String, Object> map : maps) {
            Integer status = ((Number) map.get("status")).intValue();
            Long count = ((Number) map.get("count")).longValue();
            countMap.put(status, count);
        }

        for (int i = 0; i < statusNames.length; i++) {
            OrderStatusStatsVO vo = new OrderStatusStatsVO();
            vo.setStatus(i);
            vo.setStatusName(statusNames[i]);
            vo.setCount(countMap.getOrDefault(i, 0L));
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<TopOrderVO> getTop10OrdersByAmount() {
        String[] statusNames = {"待支付", "已支付", "已发货", "已完成", "已取消"};

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("amount");
        wrapper.last("LIMIT 10");
        List<Order> orders = orderMapper.selectList(wrapper);

        List<TopOrderVO> result = new ArrayList<>();
        for (Order order : orders) {
            TopOrderVO vo = new TopOrderVO();
            vo.setId(order.getId());
            vo.setOrderNo(order.getOrderNo());
            vo.setProductName(order.getProductName());
            vo.setAmount(order.getAmount());
            vo.setStatus(order.getStatus());
            vo.setStatusName(statusNames[order.getStatus()]);
            vo.setCreateTime(order.getCreateTime());
            result.add(vo);
        }
        return result;
    }

    @Override
    public SystemOverviewVO getSystemOverview() {
        Long totalUsers = userMapper.selectCount(new QueryWrapper<>());
        Long totalOrders = orderMapper.selectCount(new QueryWrapper<>());

        QueryWrapper<Order> amountWrapper = new QueryWrapper<>();
        amountWrapper.select("IFNULL(SUM(amount), 0) as total");
        Map<String, Object> map = orderMapper.selectMaps(amountWrapper).get(0);
        BigDecimal totalAmount = (BigDecimal) map.get("total");

        SystemOverviewVO vo = new SystemOverviewVO();
        vo.setTotalUsers(totalUsers);
        vo.setTotalOrders(totalOrders);
        vo.setTotalAmount(totalAmount);
        return vo;
    }
}
