package com.project.creditservice.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantFeatures {

    private Long merchantId;

    private BigDecimal totalRevenue;

    private BigDecimal averageTransactionValue;

    private Integer transactionCount;

    private BigDecimal revenueVolatility;

    private BigDecimal monthlyRevenue;

    private BigDecimal weekendTransactionRatio;
}