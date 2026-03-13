package com.labelease.usermanagement.service.impl;

import com.labelease.usermanagement.entity.dto.*;
import com.labelease.usermanagement.mapper.DashboardMapper;
import com.labelease.usermanagement.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 看板服务实现
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    // 订单状态映射
    private static final Map<Integer, String> ORDER_STATUS_MAP = new HashMap<>();

    static {
        ORDER_STATUS_MAP.put(0, "待支付");
        ORDER_STATUS_MAP.put(1, "已支付");
        ORDER_STATUS_MAP.put(2, "已发货");
        ORDER_STATUS_MAP.put(3, "已完成");
        ORDER_STATUS_MAP.put(4, "已取消");
    }

    @Override
    public DashboardOverviewDTO getOverview() {
        DashboardOverviewDTO overview = new DashboardOverviewDTO();
        overview.setTotalUsers(dashboardMapper.getTotalUsers());
        overview.setTotalOrders(dashboardMapper.getTotalOrders());
        overview.setTotalAmount(dashboardMapper.getTotalAmount());
        overview.setTodayNewUsers(dashboardMapper.getTodayNewUsers());
        overview.setWeekNewUsers(dashboardMapper.getWeekNewUsers());
        overview.setMonthNewUsers(dashboardMapper.getMonthNewUsers());
        return overview;
    }

    @Override
    public List<UserRegistrationTrendDTO> getUserRegistrationTrend() {
        return dashboardMapper.getUserRegistrationTrend();
    }

    @Override
    public List<OrderStatusStatDTO> getOrderStatusStats() {
        List<OrderStatusStatDTO> stats = dashboardMapper.getOrderStatusStats();
        // 设置状态名称
        for (OrderStatusStatDTO stat : stats) {
            stat.setStatusName(ORDER_STATUS_MAP.getOrDefault(stat.getStatus(), "未知状态"));
        }
        return stats;
    }

    @Override
    public List<TopOrderDTO> getTopOrders(int limit) {
        List<TopOrderDTO> orders = dashboardMapper.getTopOrders(limit);
        // 设置状态名称
        for (TopOrderDTO order : orders) {
            order.setStatusName(ORDER_STATUS_MAP.getOrDefault(order.getStatus(), "未知状态"));
        }
        return orders;
    }

    @Override
    public DashboardDataDTO getFullDashboardData() {
        DashboardDataDTO dashboardData = new DashboardDataDTO();
        dashboardData.setOverview(getOverview());
        dashboardData.setUserRegistrationTrend(getUserRegistrationTrend());
        dashboardData.setOrderStatusStats(getOrderStatusStats());
        dashboardData.setTopOrders(getTopOrders(10));
        return dashboardData;
    }

    @Override
    public DataValidationDTO validateDashboardData() {
        DataValidationDTO validation = new DataValidationDTO();
        validation.setValidationTime(java.time.LocalDateTime.now());

        // 1. 验证用户数据
        DataValidationDTO.UserValidation userValidation = validateUserData();
        validation.setUserValidation(userValidation);

        // 2. 验证订单数据
        DataValidationDTO.OrderValidation orderValidation = validateOrderData();
        validation.setOrderValidation(orderValidation);

        // 3. 验证趋势数据
        DataValidationDTO.TrendValidation trendValidation = validateTrendData();
        validation.setTrendValidation(trendValidation);

        // 4. 生成验证详情
        List<DataValidationDTO.ValidationDetail> details = generateValidationDetails(userValidation, orderValidation, trendValidation);
        validation.setDetails(details);

        // 5. 总体验证结果
        validation.setIsValid(userValidation.getIsConsistent() && orderValidation.getIsConsistent() && trendValidation.getIsConsistent());

        return validation;
    }

    /**
     * 验证用户数据
     */
    private DataValidationDTO.UserValidation validateUserData() {
        DataValidationDTO.UserValidation validation = new DataValidationDTO.UserValidation();

        // 数据库原始值
        validation.setDbTotalUsers(dashboardMapper.validateTotalUsers());
        validation.setDbTodayNew(dashboardMapper.validateTodayNewUsers());
        validation.setDbWeekNew(dashboardMapper.validateWeekNewUsers());
        validation.setDbMonthNew(dashboardMapper.validateMonthNewUsers());

        // 看板显示值
        DashboardOverviewDTO overview = getOverview();
        validation.setDashboardTotalUsers(overview.getTotalUsers());
        validation.setDashboardTodayNew(overview.getTodayNewUsers());
        validation.setDashboardWeekNew(overview.getWeekNewUsers());
        validation.setDashboardMonthNew(overview.getMonthNewUsers());

        // 一致性检查
        validation.setIsConsistent(
            validation.getDbTotalUsers().equals(validation.getDashboardTotalUsers()) &&
            validation.getDbTodayNew().equals(validation.getDashboardTodayNew()) &&
            validation.getDbWeekNew().equals(validation.getDashboardWeekNew()) &&
            validation.getDbMonthNew().equals(validation.getDashboardMonthNew())
        );

        return validation;
    }

    /**
     * 验证订单数据
     */
    private DataValidationDTO.OrderValidation validateOrderData() {
        DataValidationDTO.OrderValidation validation = new DataValidationDTO.OrderValidation();

        // 数据库原始统计
        List<OrderStatusStatDTO> dbStats = dashboardMapper.validateOrderStats();
        Map<Integer, OrderStatusStatDTO> dbStatsMap = new HashMap<>();
        for (OrderStatusStatDTO stat : dbStats) {
            dbStatsMap.put(stat.getStatus(), stat);
        }

        // 看板显示统计
        List<OrderStatusStatDTO> dashboardStats = getOrderStatusStats();
        Map<Integer, OrderStatusStatDTO> dashboardStatsMap = new HashMap<>();
        for (OrderStatusStatDTO stat : dashboardStats) {
            dashboardStatsMap.put(stat.getStatus(), stat);
        }

        // 计算总数和总金额
        long dbTotalOrders = dbStats.stream().mapToLong(OrderStatusStatDTO::getCount).sum();
        java.math.BigDecimal dbTotalAmount = dbStats.stream()
            .map(OrderStatusStatDTO::getAmount)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        long dashboardTotalOrders = dashboardStats.stream().mapToLong(OrderStatusStatDTO::getCount).sum();
        java.math.BigDecimal dashboardTotalAmount = dashboardStats.stream()
            .map(OrderStatusStatDTO::getAmount)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        validation.setDbTotalOrders(dbTotalOrders);
        validation.setDbTotalAmount(dbTotalAmount);
        validation.setDashboardTotalOrders(dashboardTotalOrders);
        validation.setDashboardTotalAmount(dashboardTotalAmount);

        // 各状态对比
        Map<String, DataValidationDTO.StatusCountComparison> comparisonMap = new HashMap<>();
        boolean allConsistent = true;

        for (int status = 0; status <= 4; status++) {
            OrderStatusStatDTO dbStat = dbStatsMap.get(status);
            OrderStatusStatDTO dashboardStat = dashboardStatsMap.get(status);

            DataValidationDTO.StatusCountComparison comparison = new DataValidationDTO.StatusCountComparison();
            comparison.setStatusCode(status);
            comparison.setStatusName(ORDER_STATUS_MAP.getOrDefault(status, "未知"));
            comparison.setDbCount(dbStat != null ? dbStat.getCount() : 0L);
            comparison.setDashboardCount(dashboardStat != null ? dashboardStat.getCount() : 0L);
            comparison.setDbAmount(dbStat != null ? dbStat.getAmount() : java.math.BigDecimal.ZERO);
            comparison.setDashboardAmount(dashboardStat != null ? dashboardStat.getAmount() : java.math.BigDecimal.ZERO);

            boolean statusConsistent = comparison.getDbCount().equals(comparison.getDashboardCount()) &&
                comparison.getDbAmount().compareTo(comparison.getDashboardAmount()) == 0;
            comparison.setIsConsistent(statusConsistent);

            if (!statusConsistent) {
                allConsistent = false;
            }

            comparisonMap.put(ORDER_STATUS_MAP.getOrDefault(status, "未知"), comparison);
        }

        validation.setStatusComparison(comparisonMap);
        validation.setIsConsistent(allConsistent &&
            validation.getDbTotalOrders().equals(validation.getDashboardTotalOrders()) &&
            validation.getDbTotalAmount().compareTo(validation.getDashboardTotalAmount()) == 0);

        return validation;
    }

    /**
     * 验证趋势数据
     */
    private DataValidationDTO.TrendValidation validateTrendData() {
        DataValidationDTO.TrendValidation validation = new DataValidationDTO.TrendValidation();

        // 数据库原始数据
        List<UserRegistrationTrendDTO> dbData = dashboardMapper.validateMonthlyRegistration();
        // 看板显示数据
        List<UserRegistrationTrendDTO> dashboardData = getUserRegistrationTrend();

        // 转换为验证格式
        List<DataValidationDTO.MonthData> dbMonthData = dbData.stream()
            .map(d -> new DataValidationDTO.MonthData(d.getMonth(), d.getCount()))
            .collect(java.util.stream.Collectors.toList());

        List<DataValidationDTO.MonthData> dashboardMonthData = dashboardData.stream()
            .map(d -> new DataValidationDTO.MonthData(d.getMonth(), d.getCount()))
            .collect(java.util.stream.Collectors.toList());

        validation.setDbMonthlyData(dbMonthData);
        validation.setDashboardMonthlyData(dashboardMonthData);

        // 对比数据
        boolean consistent = dbMonthData.size() == dashboardMonthData.size();
        if (consistent) {
            for (int i = 0; i < dbMonthData.size(); i++) {
                DataValidationDTO.MonthData db = dbMonthData.get(i);
                DataValidationDTO.MonthData dashboard = dashboardMonthData.get(i);
                if (!db.getMonth().equals(dashboard.getMonth()) || !db.getCount().equals(dashboard.getCount())) {
                    consistent = false;
                    break;
                }
            }
        }
        validation.setIsConsistent(consistent);

        return validation;
    }

    /**
     * 生成验证详情列表
     */
    private List<DataValidationDTO.ValidationDetail> generateValidationDetails(
            DataValidationDTO.UserValidation userValidation,
            DataValidationDTO.OrderValidation orderValidation,
            DataValidationDTO.TrendValidation trendValidation) {

        List<DataValidationDTO.ValidationDetail> details = new java.util.ArrayList<>();

        // 用户数据验证详情
        details.add(new DataValidationDTO.ValidationDetail(
            "总用户数",
            String.valueOf(userValidation.getDbTotalUsers()),
            String.valueOf(userValidation.getDashboardTotalUsers()),
            userValidation.getDbTotalUsers().equals(userValidation.getDashboardTotalUsers()),
            userValidation.getDbTotalUsers().equals(userValidation.getDashboardTotalUsers()) ? "一致" : "不一致"
        ));

        details.add(new DataValidationDTO.ValidationDetail(
            "今日新增用户",
            String.valueOf(userValidation.getDbTodayNew()),
            String.valueOf(userValidation.getDashboardTodayNew()),
            userValidation.getDbTodayNew().equals(userValidation.getDashboardTodayNew()),
            userValidation.getDbTodayNew().equals(userValidation.getDashboardTodayNew()) ? "一致" : "不一致"
        ));

        details.add(new DataValidationDTO.ValidationDetail(
            "本周新增用户",
            String.valueOf(userValidation.getDbWeekNew()),
            String.valueOf(userValidation.getDashboardWeekNew()),
            userValidation.getDbWeekNew().equals(userValidation.getDashboardWeekNew()),
            userValidation.getDbWeekNew().equals(userValidation.getDashboardWeekNew()) ? "一致" : "不一致"
        ));

        details.add(new DataValidationDTO.ValidationDetail(
            "本月新增用户",
            String.valueOf(userValidation.getDbMonthNew()),
            String.valueOf(userValidation.getDashboardMonthNew()),
            userValidation.getDbMonthNew().equals(userValidation.getDashboardMonthNew()),
            userValidation.getDbMonthNew().equals(userValidation.getDashboardMonthNew()) ? "一致" : "不一致"
        ));

        // 订单数据验证详情
        details.add(new DataValidationDTO.ValidationDetail(
            "总订单数",
            String.valueOf(orderValidation.getDbTotalOrders()),
            String.valueOf(orderValidation.getDashboardTotalOrders()),
            orderValidation.getDbTotalOrders().equals(orderValidation.getDashboardTotalOrders()),
            orderValidation.getDbTotalOrders().equals(orderValidation.getDashboardTotalOrders()) ? "一致" : "不一致"
        ));

        details.add(new DataValidationDTO.ValidationDetail(
            "总金额",
            orderValidation.getDbTotalAmount().toString(),
            orderValidation.getDashboardTotalAmount().toString(),
            orderValidation.getDbTotalAmount().compareTo(orderValidation.getDashboardTotalAmount()) == 0,
            orderValidation.getDbTotalAmount().compareTo(orderValidation.getDashboardTotalAmount()) == 0 ? "一致" : "不一致"
        ));

        // 各状态订单验证
        for (Map.Entry<String, DataValidationDTO.StatusCountComparison> entry : orderValidation.getStatusComparison().entrySet()) {
            DataValidationDTO.StatusCountComparison comparison = entry.getValue();
            details.add(new DataValidationDTO.ValidationDetail(
                entry.getValue() + "订单数",
                String.valueOf(comparison.getDbCount()),
                String.valueOf(comparison.getDashboardCount()),
                comparison.getIsConsistent(),
                comparison.getIsConsistent() ? "一致" : "不一致"
            ));
        }

        // 趋势数据验证
        details.add(new DataValidationDTO.ValidationDetail(
            "月度注册趋势",
            dbMonthData.size() + "个月",
            dashboardMonthData.size() + "个月",
            trendValidation.getIsConsistent(),
            trendValidation.getIsConsistent() ? "一致" : "不一致"
        ));

        return details;
    }

    @Override
    public List<DailyOrderStatDTO> getDailyOrderStats() {
        return dashboardMapper.getDailyOrderStats();
    }

    @Override
    public OrderAmountDistributionDTO getOrderAmountDistribution() {
        return dashboardMapper.getOrderAmountDistribution();
    }
}
