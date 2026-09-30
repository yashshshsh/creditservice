package com.project.creditservice.controller;

import com.project.creditservice.model.MerchantFeatures;
import com.project.creditservice.service.impl.MerchantAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantAnalyticsController {

    private final MerchantAnalyticsService merchantAnalyticsService;

    @GetMapping("/{merchantId}/features")
    public ResponseEntity<MerchantFeatures> getMerchantFeatures(
            @PathVariable Long merchantId) {

        return ResponseEntity.ok(
                merchantAnalyticsService
                        .calculateMerchantFeatures(merchantId)
        );
    }
}