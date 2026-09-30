package com.project.creditservice.service.impl;

import com.project.creditservice.model.Merchant;
import com.project.creditservice.repository.MerchantRepository;
import com.project.creditservice.service.interfac.IMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService implements IMerchantService {

    private final MerchantRepository merchantRepository;

    @Override
    public Merchant createMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public Merchant getMerchantById(Long id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant updateMerchant(Long id, Merchant merchant) {

        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));

        existingMerchant.setBusinessName(merchant.getBusinessName());
        existingMerchant.setPhoneNumber(merchant.getPhoneNumber());
        existingMerchant.setBusinessType(merchant.getBusinessType());
        existingMerchant.setCity(merchant.getCity());
        existingMerchant.setActive(merchant.getActive());

        return merchantRepository.save(existingMerchant);
    }

    @Override
    public void deleteMerchant(Long id) {

        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));

        merchantRepository.delete(merchant);
    }
}