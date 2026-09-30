package com.project.creditservice.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScoreResponse {

    private Long merchantId;

    private Integer creditScore;

    private BigDecimal recommendedLoanAmount;

    private String riskLevel;
}