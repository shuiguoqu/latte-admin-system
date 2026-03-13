package com.labelease.usermanagement.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SystemOverviewVO {
    private Long totalUsers;
    private Long totalOrders;
    private BigDecimal totalAmount;
}
