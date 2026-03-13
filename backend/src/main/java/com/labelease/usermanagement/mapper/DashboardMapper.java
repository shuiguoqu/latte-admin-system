package com.labelease.usermanagement.mapper;

import com.labelease.usermanagement.entity.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 看板数据Mapper - 统计查询
 */
@Mapper
public interface DashboardMapper {

    /**
     * 获取总用户数
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0")
    Long getTotalUsers();

    /**
     * 获取总订单数
     */
    @Select("SELECT COUNT(*) FROM t_order WHERE deleted = 0")
    Long getTotalOrders();

    /**
     * 获取订单总金额
     */
    @Select("SELECT COALESCE(SUM(amount), 0) FROM t_order WHERE deleted = 0")
    BigDecimal getTotalAmount();

    /**
     * 获取今日新增用户数
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND DATE(create_time) = CURDATE()")
    Long getTodayNewUsers();

    /**
     * 获取本周新增用户数
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEARWEEK(create_time) = YEARWEEK(CURDATE())")
    Long getWeekNewUsers();

    /**
     * 获取本月新增用户数
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE())")
    Long getMonthNewUsers();

    /**
     * 获取近一年每月注册用户数
     */
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') as month, COUNT(*) as count " +
            "FROM t_user WHERE deleted = 0 AND create_time >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m') ORDER BY month")
    List<UserRegistrationTrendDTO> getUserRegistrationTrend();

    /**
     * 获取订单状态统计
     */
    @Select("SELECT status, COUNT(*) as count, COALESCE(SUM(amount), 0) as amount " +
            "FROM t_order WHERE deleted = 0 GROUP BY status ORDER BY status")
    List<OrderStatusStatDTO> getOrderStatusStats();

    /**
     * 获取金额最高的前N个订单
     */
    @Select("SELECT o.id, o.order_no, o.user_id, u.username, o.product_name, o.amount, o.status, o.create_time " +
            "FROM t_order o LEFT JOIN t_user u ON o.user_id = u.id " +
            "WHERE o.deleted = 0 ORDER BY o.amount DESC LIMIT #{limit}")
    List<TopOrderDTO> getTopOrders(@Param("limit") int limit);

    // ==================== 数据验证查询方法 ====================

    /**
     * 验证：获取数据库原始总用户数
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0")
    Long validateTotalUsers();

    /**
     * 验证：获取数据库原始今日新增用户
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND DATE(create_time) = CURDATE()")
    Long validateTodayNewUsers();

    /**
     * 验证：获取数据库原始本周新增用户
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEARWEEK(create_time) = YEARWEEK(CURDATE())")
    Long validateWeekNewUsers();

    /**
     * 验证：获取数据库原始本月新增用户
     */
    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE())")
    Long validateMonthNewUsers();

    /**
     * 验证：获取数据库原始订单统计
     */
    @Select("SELECT status, COUNT(*) as count, COALESCE(SUM(amount), 0) as amount " +
            "FROM t_order WHERE deleted = 0 GROUP BY status ORDER BY status")
    List<OrderStatusStatDTO> validateOrderStats();

    /**
     * 验证：获取数据库原始月度用户注册数据
     */
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m') as month, COUNT(*) as count " +
            "FROM t_user WHERE deleted = 0 AND create_time >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m') ORDER BY month")
    List<UserRegistrationTrendDTO> validateMonthlyRegistration();

    /**
     * 验证：获取近30天每日订单统计
     */
    @Select("SELECT DATE(create_time) as date, COUNT(*) as count, COALESCE(SUM(amount), 0) as amount " +
            "FROM t_order WHERE deleted = 0 AND create_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
            "GROUP BY DATE(create_time) ORDER BY date DESC")
    List<DailyOrderStatDTO> getDailyOrderStats();

    /**
     * 验证：获取订单金额分布统计
     */
    @Select("SELECT " +
            "  SUM(CASE WHEN amount < 100 THEN 1 ELSE 0 END) as low_count," +
            "  SUM(CASE WHEN amount >= 100 AND amount < 1000 THEN 1 ELSE 0 END) as mid_count," +
            "  SUM(CASE WHEN amount >= 1000 AND amount < 5000 THEN 1 ELSE 0 END) as high_count," +
            "  SUM(CASE WHEN amount >= 5000 THEN 1 ELSE 0 END) as vip_count," +
            "  AVG(amount) as avg_amount," +
            "  MIN(amount) as min_amount," +
            "  MAX(amount) as max_amount " +
            "FROM t_order WHERE deleted = 0")
    OrderAmountDistributionDTO getOrderAmountDistribution();
}
