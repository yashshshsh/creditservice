package com.project.creditservice.service.impl;

import com.project.creditservice.model.CreditScoreResponse;
import com.project.creditservice.model.MerchantFeatures;
import com.project.creditservice.service.interfac.ICreditScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CreditScoringService implements ICreditScoringService {

    private final MerchantAnalyticsService merchantAnalyticsService;

    @Value("${eta.ml.url}")
    private String mlServiceUrl;

    @Override
    public CreditScoreResponse calculateCreditScore(Long merchantId) {

        MerchantFeatures features =
                merchantAnalyticsService.calculateMerchantFeatures(merchantId);

        RestClient restClient = RestClient.builder()
                .baseUrl(mlServiceUrl)
                .build();

        return restClient.post()
                .uri("/score-merchant")
                .body(features)
                .retrieve()
                .body(CreditScoreResponse.class);
    }
}