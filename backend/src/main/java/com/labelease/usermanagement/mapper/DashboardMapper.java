package com.labelease.usermanagement.mapper;

import com.labelease.usermanagement.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * 看板数据访问层
 */
@Mapper
public interface DashboardMapper {

    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND DATE(create_time) = CURDATE()")
    Long countTodayRegistrations();

    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEARWEEK(create_time, 1) = YEARWEEK(CURDATE(), 1)")
    Long countWeekRegistrations();

    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0 AND YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE())")
    Long countMonthRegistrations();

    @Select("""
        SELECT 
            YEAR(create_time) AS year,
            MONTH(create_time) AS month,
            COUNT(*) AS count
        FROM t_user 
        WHERE deleted = 0 
          AND create_time >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
        GROUP BY YEAR(create_time), MONTH(create_time)
        ORDER BY year ASC, month ASC
    """)
    List<MonthlyRegistration> selectMonthlyRegistrations();

    @Select("""
        SELECT 
            status,
            CASE status
                WHEN 0 THEN '待支付'
                WHEN 1 THEN '已支付'
                WHEN 2 THEN '已发货'
                WHEN 3 THEN '已完成'
                WHEN 4 THEN '已取消'
            END AS statusName,
            COUNT(*) AS count,
            COALESCE(SUM(amount), 0) AS totalAmount
        FROM t_order 
        WHERE deleted = 0
        GROUP BY status
        ORDER BY status ASC
    """)
    List<OrderStatusStats> selectOrderStatusStats();

    @Select("""
        SELECT 
            o.id,
            o.order_no AS orderNo,
            u.username,
            u.real_name AS realName,
            o.product_name AS productName,
            o.amount,
            o.status,
            CASE o.status
                WHEN 0 THEN '待支付'
                WHEN 1 THEN '已支付'
                WHEN 2 THEN '已发货'
                WHEN 3 THEN '已完成'
                WHEN 4 THEN '已取消'
            END AS statusName,
            o.create_time AS createTime
        FROM t_order o
        LEFT JOIN t_user u ON o.user_id = u.id
        WHERE o.deleted = 0
        ORDER BY o.amount DESC
        LIMIT 10
    """)
    List<TopOrderDTO> selectTop10Orders();

    @Select("SELECT COUNT(*) FROM t_user WHERE deleted = 0")
    Long countTotalUsers();

    @Select("SELECT COUNT(*) FROM t_order WHERE deleted = 0")
    Long countTotalOrders();

    @Select("SELECT COALESCE(SUM(amount), 0) FROM t_order WHERE deleted = 0")
    BigDecimal sumTotalAmount();

    @Select("SELECT COUNT(*) FROM t_order WHERE deleted = 0 AND status = 3")
    Long countCompletedOrders();

    @Select("SELECT COALESCE(SUM(amount), 0) FROM t_order WHERE deleted = 0 AND status = 3")
    BigDecimal sumCompletedAmount();
}
