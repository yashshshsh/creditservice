package com.project.creditservice.service.interfac;

import com.project.creditservice.model.Merchant;

import java.util.List;

public interface IMerchantService {

    Merchant createMerchant(Merchant merchant);

    Merchant getMerchantById(Long id);

    List<Merchant> getAllMerchants();

    Merchant updateMerchant(Long id, Merchant merchant);

    void deleteMerchant(Long id);
}