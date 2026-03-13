package com.labelease.usermanagement.entity.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 数据验证结果DTO - 用于对比看板数据与原始数据
 */
@Data
public class DataValidationDTO {

    /** 验证时间 */
    private LocalDateTime validationTime;

    /** 用户数据验证 */
    private UserValidation userValidation;

    /** 订单数据验证 */
    private OrderValidation orderValidation;

    /** 趋势数据验证 */
    private TrendValidation trendValidation;

    /** 验证是否通过 */
    private Boolean isValid;

    /** 验证详情 */
    private List<ValidationDetail> details;

    /**
     * 用户数据验证
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserValidation {
        /** 数据库总用户数 */
        private Long dbTotalUsers;
        /** 看板显示总用户数 */
        private Long dashboardTotalUsers;
        /** 数据库今日新增 */
        private Long dbTodayNew;
        /** 看板今日新增 */
        private Long dashboardTodayNew;
        /** 数据库本周新增 */
        private Long dbWeekNew;
        /** 看板本周新增 */
        private Long dashboardWeekNew;
        /** 数据库本月新增 */
        private Long dbMonthNew;
        /** 看板本月新增 */
        private Long dashboardMonthNew;
        /** 是否一致 */
        private Boolean isConsistent;
    }

    /**
     * 订单数据验证
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderValidation {
        /** 数据库总订单数 */
        private Long dbTotalOrders;
        /** 看板显示总订单数 */
        private Long dashboardTotalOrders;
        /** 数据库总金额 */
        private BigDecimal dbTotalAmount;
        /** 看板显示总金额 */
        private BigDecimal dashboardTotalAmount;
        /** 各状态订单数对比 */
        private Map<String, StatusCountComparison> statusComparison;
        /** 是否一致 */
        private Boolean isConsistent;
    }

    /**
     * 状态数量对比
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatusCountComparison {
        private String statusName;
        private Integer statusCode;
        private Long dbCount;
        private Long dashboardCount;
        private BigDecimal dbAmount;
        private BigDecimal dashboardAmount;
        private Boolean isConsistent;
    }

    /**
     * 趋势数据验证
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrendValidation {
        /** 数据库月度注册数据 */
        private List<MonthData> dbMonthlyData;
        /** 看板月度注册数据 */
        private List<MonthData> dashboardMonthlyData;
        /** 是否一致 */
        private Boolean isConsistent;
    }

    /**
     * 月度数据
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthData {
        private String month;
        private Long count;
    }

    /**
     * 验证详情
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationDetail {
        private String checkItem;
        private String dbValue;
        private String dashboardValue;
        private Boolean isMatch;
        private String message;
    }
}
