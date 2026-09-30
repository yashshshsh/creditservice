package com.project.creditservice.controller;

import com.project.creditservice.model.Merchant;
import com.project.creditservice.service.impl.MerchantService;
import com.project.creditservice.service.interfac.IMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(merchantService.createMerchant(merchant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchantById(@PathVariable Long id) {
        return ResponseEntity.ok(merchantService.getMerchantById(id));
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(
            @PathVariable Long id,
            @RequestBody Merchant merchant) {

        return ResponseEntity.ok(
                merchantService.updateMerchant(id, merchant)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {

        merchantService.deleteMerchant(id);

        return ResponseEntity.noContent().build();
    }
}