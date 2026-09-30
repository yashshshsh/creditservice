package com.project.creditservice.service.impl;

import com.project.creditservice.model.LoanApplication;
import com.project.creditservice.repository.LoanApplicationRepository;
import com.project.creditservice.service.interfac.ILoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationService implements ILoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    @Override
    public LoanApplication createLoanApplication(
            LoanApplication loanApplication) {

        if (loanApplication.getStatus() == null) {
            loanApplication.setStatus("PENDING");
        }

        if (loanApplication.getApplicationDate() == null) {
            loanApplication.setApplicationDate(
                    java.time.LocalDateTime.now()
            );
        }

        return loanApplicationRepository.save(loanApplication);
    }

    @Override
    public LoanApplication getLoanApplicationById(Long id) {

        return loanApplicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Loan application not found with id: " + id
                        ));
    }

    @Override
    public List<LoanApplication> getAllLoanApplications() {

        return loanApplicationRepository.findAll();
    }

    @Override
    public LoanApplication updateLoanApplication(
            Long id,
            LoanApplication loanApplication) {

        LoanApplication existingApplication =
                loanApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found with id: " + id
                                ));

        existingApplication.setMerchant(
                loanApplication.getMerchant()
        );

        existingApplication.setRequestedAmount(
                loanApplication.getRequestedAmount()
        );

        existingApplication.setTenureMonths(
                loanApplication.getTenureMonths()
        );

        existingApplication.setPurpose(
                loanApplication.getPurpose()
        );

        existingApplication.setStatus(
                loanApplication.getStatus()
        );

        existingApplication.setCreditScore(
                loanApplication.getCreditScore()
        );

        existingApplication.setApprovedAmount(
                loanApplication.getApprovedAmount()
        );

        existingApplication.setApplicationDate(
                loanApplication.getApplicationDate()
        );

        return loanApplicationRepository.save(existingApplication);
    }

    @Override
    public void deleteLoanApplication(Long id) {

        LoanApplication loanApplication =
                loanApplicationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found with id: " + id
                                ));

        loanApplicationRepository.delete(loanApplication);
    }
}