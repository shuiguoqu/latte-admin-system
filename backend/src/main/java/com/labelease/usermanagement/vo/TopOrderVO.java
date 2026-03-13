package com.labelease.usermanagement.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TopOrderVO {
    private Long id;
    private String orderNo;
    private String productName;
    private BigDecimal amount;
    private Integer status;
    private String statusName;
    private LocalDateTime createTime;
}
