package com.project.creditservice.service.interfac;

import com.project.creditservice.model.Transaction;

import java.util.List;

public interface ITransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    Transaction updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);
}