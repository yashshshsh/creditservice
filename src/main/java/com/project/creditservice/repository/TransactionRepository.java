package com.project.creditservice.repository;

import com.project.creditservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByMerchantId(Long merchantId);
}