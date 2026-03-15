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

        Set<Integer> fromZero = new HashSet<>();
        fromZero.add(1);
        fromZero.add(4);
        ALLOWED_TRANSITIONS.put(0, fromZero);

        Set<Integer> fromOne = new HashSet<>();
        fromOne.add(2);
        ALLOWED_TRANSITIONS.put(1, fromOne);

        Set<Integer> fromTwo = new HashSet<>();
        fromTwo.add(3);
        ALLOWED_TRANSITIONS.put(2, fromTwo);
    }

    public static void validateTransition(Integer currentStatus, Integer newStatus) {
        if (currentStatus == null || newStatus == null) {
            throw new IllegalArgumentException("状态值不能为空");
        }

        if (!STATUS_NAMES.containsKey(currentStatus)) {
            throw new IllegalArgumentException("无效的当前状态: " + currentStatus);
        }

        if (!STATUS_NAMES.containsKey(newStatus)) {
            throw new IllegalArgumentException("无效的目标状态: " + newStatus);
        }

        if (currentStatus.equals(newStatus)) {
            return;
        }

        Set<Integer> allowed = ALLOWED_TRANSITIONS.get(currentStatus);
        if (allowed == null || !allowed.contains(newStatus)) {
            String currentName = STATUS_NAMES.get(currentStatus);
            String newName = STATUS_NAMES.get(newStatus);
            throw new IllegalStateException("不允许从【" + currentName + "】变更为【" + newName + "】");
        }
    }

    public static String getStatusName(Integer status) {
        return STATUS_NAMES.getOrDefault(status, "未知状态");
    }
}
