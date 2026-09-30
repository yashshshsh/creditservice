package com.project.creditservice.controller;

import com.project.creditservice.model.CreditScoreResponse;
import com.project.creditservice.service.impl.CreditScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditScoringController {

    private final CreditScoringService creditScoringService;

    @GetMapping("/{merchantId}/score")
    public ResponseEntity<CreditScoreResponse> calculateCreditScore(
            @PathVariable Long merchantId) {

        return ResponseEntity.ok(
                creditScoringService.calculateCreditScore(merchantId)
        );
    }
}