package com.project.creditservice.controller;

import com.project.creditservice.model.LoanApplication;
import com.project.creditservice.service.impl.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<LoanApplication> createLoanApplication(
            @RequestBody LoanApplication loanApplication) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        loanApplicationService
                                .createLoanApplication(loanApplication)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanApplicationService
                        .getLoanApplicationById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {

        return ResponseEntity.ok(
                loanApplicationService
                        .getAllLoanApplications()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanApplication> updateLoanApplication(
            @PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {

        return ResponseEntity.ok(
                loanApplicationService.updateLoanApplication(
                        id,
                        loanApplication
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanApplication(
            @PathVariable Long id) {

        loanApplicationService.deleteLoanApplication(id);

        return ResponseEntity.noContent().build();
    }
}