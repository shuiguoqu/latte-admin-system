package com.labelease.usermanagement.service.impl;

import com.labelease.usermanagement.dto.*;
import com.labelease.usermanagement.mapper.DashboardMapper;
import com.labelease.usermanagement.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 看板服务实现
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    @Override
    public DashboardData getDashboardData() {
        return DashboardData.builder()
                .overview(getSystemOverview())
                .userRegistration(getUserRegistrationStats())
                .monthlyTrend(getMonthlyRegistrationTrend())
                .orderStatusStats(getOrderStatusStats())
                .topOrders(getTop10Orders())
                .build();
    }

    @Override
    public UserRegistrationStats getUserRegistrationStats() {
        return UserRegistrationStats.builder()
                .todayCount(dashboardMapper.countTodayRegistrations())
                .weekCount(dashboardMapper.countWeekRegistrations())
                .monthCount(dashboardMapper.countMonthRegistrations())
                .build();
    }

    @Override
    public List<MonthlyRegistration> getMonthlyRegistrationTrend() {
        return dashboardMapper.selectMonthlyRegistrations();
    }

    @Override
    public List<OrderStatusStats> getOrderStatusStats() {
        return dashboardMapper.selectOrderStatusStats();
    }

    @Override
    public List<TopOrderDTO> getTop10Orders() {
        return dashboardMapper.selectTop10Orders();
    }

    @Override
    public SystemOverview getSystemOverview() {
        return SystemOverview.builder()
                .totalUsers(dashboardMapper.countTotalUsers())
                .totalOrders(dashboardMapper.countTotalOrders())
                .totalAmount(dashboardMapper.sumTotalAmount())
                .completedOrders(dashboardMapper.countCompletedOrders())
                .completedAmount(dashboardMapper.sumCompletedAmount())
                .build();
    }
}
