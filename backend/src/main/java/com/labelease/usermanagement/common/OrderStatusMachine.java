package com.labelease.usermanagement.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 订单状态机
 * 负责校验订单状态转换的合法性
 */
public class OrderStatusMachine {

    /**
     * 状态码定义
     */
    public static final int STATUS_PENDING_PAYMENT = 0;  // 待支付
    public static final int STATUS_PAID = 1;             // 已支付
    public static final int STATUS_SHIPPED = 2;          // 已发货
    public static final int STATUS_COMPLETED = 3;        // 已完成
    public static final int STATUS_CANCELLED = 4;        // 已取消

    /**
     * 状态码到中文名称的映射
     */
    private static final Map<Integer, String> STATUS_NAME_MAP = new HashMap<>();

    /**
     * 合法的状态转换规则
     * key: 当前状态, value: 允许转换到的目标状态集合
     */
    private static final Map<Integer, Set<Integer>> VALID_TRANSITIONS = new HashMap<>();

    static {
        // 初始化状态名称映射
        STATUS_NAME_MAP.put(STATUS_PENDING_PAYMENT, "待支付");
        STATUS_NAME_MAP.put(STATUS_PAID, "已支付");
        STATUS_NAME_MAP.put(STATUS_SHIPPED, "已发货");
        STATUS_NAME_MAP.put(STATUS_COMPLETED, "已完成");
        STATUS_NAME_MAP.put(STATUS_CANCELLED, "已取消");

        // 初始化合法状态转换规则
        // 待支付 -> 已支付, 已取消
        VALID_TRANSITIONS.put(STATUS_PENDING_PAYMENT, new HashSet<>(Arrays.asList(STATUS_PAID, STATUS_CANCELLED)));
        // 已支付 -> 已发货
        VALID_TRANSITIONS.put(STATUS_PAID, new HashSet<>(Arrays.asList(STATUS_SHIPPED)));
        // 已发货 -> 已完成
        VALID_TRANSITIONS.put(STATUS_SHIPPED, new HashSet<>(Arrays.asList(STATUS_COMPLETED)));
        // 已完成 -> 无后续状态
        VALID_TRANSITIONS.put(STATUS_COMPLETED, new HashSet<>());
        // 已取消 -> 无后续状态
        VALID_TRANSITIONS.put(STATUS_CANCELLED, new HashSet<>());
    }

    /**
     * 校验状态转换是否合法
     *
     * @param currentStatus 当前状态
     * @param targetStatus  目标状态
     * @throws IllegalStateException 如果转换不合法，抛出异常并附带中文说明
     */
    public static void validateTransition(Integer currentStatus, Integer targetStatus) {
        if (currentStatus == null) {
            throw new IllegalStateException("当前订单状态不能为空");
        }
        if (targetStatus == null) {
            throw new IllegalStateException("目标状态不能为空");
        }

        // 校验状态码是否有效
        if (!STATUS_NAME_MAP.containsKey(currentStatus)) {
            throw new IllegalStateException("当前状态码无效: " + currentStatus);
        }
        if (!STATUS_NAME_MAP.containsKey(targetStatus)) {
            throw new IllegalStateException("目标状态码无效: " + targetStatus);
        }

        // 校验转换是否合法
        Set<Integer> allowedTargets = VALID_TRANSITIONS.getOrDefault(currentStatus, new HashSet<>());
        if (!allowedTargets.contains(targetStatus)) {
            String currentName = STATUS_NAME_MAP.get(currentStatus);
            String targetName = STATUS_NAME_MAP.get(targetStatus);
            throw new IllegalStateException(
                String.format("不允许从【%s】变更为【%s】", currentName, targetName)
            );
        }
    }

    /**
     * 判断状态转换是否合法（不抛异常）
     *
     * @param currentStatus 当前状态
     * @param targetStatus  目标状态
     * @return true 如果转换合法，否则 false
     */
    public static boolean canTransition(Integer currentStatus, Integer targetStatus) {
        if (currentStatus == null || targetStatus == null) {
            return false;
        }
        Set<Integer> allowedTargets = VALID_TRANSITIONS.getOrDefault(currentStatus, new HashSet<>());
        return allowedTargets.contains(targetStatus);
    }

    /**
     * 获取状态中文名称
     *
     * @param status 状态码
     * @return 状态中文名称，如果状态码无效则返回 null
     */
    public static String getStatusName(Integer status) {
        return STATUS_NAME_MAP.get(status);
    }

    /**
     * 获取所有合法的目标状态
     *
     * @param currentStatus 当前状态
     * @return 允许转换的目标状态集合
     */
    public static Set<Integer> getAllowedTargetStatuses(Integer currentStatus) {
        return VALID_TRANSITIONS.getOrDefault(currentStatus, new HashSet<>());
    }
}
