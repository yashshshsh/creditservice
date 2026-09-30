package com.project.creditservice.service.impl;

import com.project.creditservice.model.CreditScoreResponse;
import com.project.creditservice.model.MerchantFeatures;
import com.project.creditservice.service.interfac.ICreditScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoringService implements ICreditScoringService {

    private final MerchantAnalyticsService merchantAnalyticsService;

    @Override
    public CreditScoreResponse calculateCreditScore(Long merchantId) {

        MerchantFeatures features =
                merchantAnalyticsService.calculateMerchantFeatures(merchantId);

        return new CreditScoreResponse(
                merchantId,
                0,
                null,
                "PENDING"
        );
    }
}