package com.labelease.usermanagement.service;

import com.labelease.usermanagement.vo.*;
import java.util.List;

public interface DashboardService {

    UserRegisterStatsVO getUserRegisterStats();

    List<MonthlyRegisterVO> getMonthlyRegisterStats();

    List<OrderStatusStatsVO> getOrderStatusStats();

    List<TopOrderVO> getTop10OrdersByAmount();

    SystemOverviewVO getSystemOverview();
}
