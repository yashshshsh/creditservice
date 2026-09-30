package com.project.creditservice.repository;

import com.project.creditservice.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {
}