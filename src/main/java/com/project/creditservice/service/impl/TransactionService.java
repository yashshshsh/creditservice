package com.project.creditservice.service.impl;

import com.project.creditservice.model.Transaction;
import com.project.creditservice.repository.TransactionRepository;
import com.project.creditservice.service.interfac.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found with id: " + id));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {

        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found with id: " + id));

        existingTransaction.setMerchant(transaction.getMerchant());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setTransactionDate(transaction.getTransactionDate());
        existingTransaction.setTransactionType(transaction.getTransactionType());
        existingTransaction.setPaymentMethod(transaction.getPaymentMethod());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found with id: " + id));

        transactionRepository.delete(transaction);
    }
}