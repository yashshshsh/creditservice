package com.project.creditservice.service.interfac;

import com.project.creditservice.model.MerchantFeatures;

public interface IMerchantAnalyticsService {

    MerchantFeatures calculateMerchantFeatures(Long merchantId);
}