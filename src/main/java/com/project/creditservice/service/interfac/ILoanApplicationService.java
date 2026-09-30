package com.project.creditservice.service.interfac;

import com.project.creditservice.model.LoanApplication;

import java.util.List;

public interface ILoanApplicationService {

    LoanApplication createLoanApplication(LoanApplication loanApplication);

    LoanApplication getLoanApplicationById(Long id);

    List<LoanApplication> getAllLoanApplications();

    LoanApplication updateLoanApplication(
            Long id,
            LoanApplication loanApplication
    );

    void deleteLoanApplication(Long id);
}