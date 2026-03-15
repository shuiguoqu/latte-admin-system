package com.labelease.usermanagement.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderStatusMachine {

    private static final Map<Integer, String> STATUS_NAMES = new HashMap<>();
    private static final Map<Integer, Set<Integer>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        STATUS_NAMES.put(0, "待支付");
        STATUS_NAMES.put(1, "已支付");
        STATUS_NAMES.put(2, "已发货");
        STATUS_NAMES.put(3, "已完成");
        STATUS_NAMES.put(4, "已取消");

        ALLOWED_TRANSITIONS.put(0, Set.of(1, 4));
        ALLOWED_TRANSITIONS.put(1, Set.of(2));
        ALLOWED_TRANSITIONS.put(2, Set.of(3));
        ALLOWED_TRANSITIONS.put(3, new HashSet<>());
        ALLOWED_TRANSITIONS.put(4, new HashSet<>());
    }

    public static String getStatusName(Integer status) {
        return STATUS_NAMES.getOrDefault(status, "未知状态");
    }

    public static void validateTransition(Integer fromStatus, Integer toStatus) {
        if (fromStatus == null || toStatus == null) {
            throw new OrderStatusException("订单状态不能为空");
        }

        if (fromStatus.equals(toStatus)) {
            throw new OrderStatusException("订单状态未发生变化");
        }

        Set<Integer> allowedTargets = ALLOWED_TRANSITIONS.get(fromStatus);
        if (allowedTargets == null || !allowedTargets.contains(toStatus)) {
            String fromName = getStatusName(fromStatus);
            String toName = getStatusName(toStatus);
            throw new OrderStatusException(String.format("不允许从【%s】变更为【%s】", fromName, toName));
        }
    }

    public static boolean canTransitionTo(Integer fromStatus, Integer toStatus) {
        Set<Integer> allowedTargets = ALLOWED_TRANSITIONS.get(fromStatus);
        return allowedTargets != null && allowedTargets.contains(toStatus);
    }
}
