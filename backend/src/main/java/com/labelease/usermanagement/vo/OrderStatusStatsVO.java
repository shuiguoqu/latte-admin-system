package com.labelease.usermanagement.vo;

import lombok.Data;

@Data
public class OrderStatusStatsVO {
    private Integer status;
    private String statusName;
    private Long count;
}
