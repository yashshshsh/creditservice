package com.project.creditservice.service.interfac;

import com.project.creditservice.model.CreditScoreResponse;

public interface ICreditScoringService {

    CreditScoreResponse calculateCreditScore(Long merchantId);
}