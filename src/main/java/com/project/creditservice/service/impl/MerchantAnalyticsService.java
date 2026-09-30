package com.project.creditservice.service.impl;

import com.project.creditservice.model.MerchantFeatures;
import com.project.creditservice.model.Transaction;
import com.project.creditservice.repository.TransactionRepository;
import com.project.creditservice.service.interfac.IMerchantAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantAnalyticsService implements IMerchantAnalyticsService {

    private final TransactionRepository transactionRepository;

    @Override
    public MerchantFeatures calculateMerchantFeatures(Long merchantId) {

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);

        List<Transaction> transactions =
                transactionRepository
                        .findByMerchant_IdAndTransactionDateBetween(
                                merchantId,
                                startDate,
                                endDate
                        );

        if (transactions.isEmpty()) {
            return new MerchantFeatures(
                    merchantId,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        BigDecimal revenueLast30Days = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageTransactionValue =
                revenueLast30Days.divide(
                        BigDecimal.valueOf(transactions.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal revenueVolatility =
                calculateRevenueVolatility(transactions);

        BigDecimal weekendTransactionRatio =
                calculateWeekendRatio(transactions);

        return new MerchantFeatures(
                merchantId,
                revenueLast30Days,
                averageTransactionValue,
                transactions.size(),
                revenueVolatility,
                weekendTransactionRatio
        );
    }

    private BigDecimal calculateRevenueVolatility(
            List<Transaction> transactions) {

        BigDecimal average = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(transactions.size()),
                        4,
                        RoundingMode.HALF_UP
                );

        BigDecimal variance = transactions.stream()
                .map(Transaction::getAmount)
                .map(amount -> amount.subtract(average))
                .map(value -> value.multiply(value))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(
                        BigDecimal.valueOf(transactions.size()),
                        4,
                        RoundingMode.HALF_UP
                );

        double standardDeviation =
                Math.sqrt(variance.doubleValue());

        return BigDecimal.valueOf(standardDeviation)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateWeekendRatio(
            List<Transaction> transactions) {

        long weekendTransactions = transactions.stream()
                .filter(transaction -> {

                    DayOfWeek day = transaction.getTransactionDate()
                            .getDayOfWeek();

                    return day == DayOfWeek.SATURDAY
                            || day == DayOfWeek.SUNDAY;
                })
                .count();

        return BigDecimal.valueOf(weekendTransactions)
                .divide(
                        BigDecimal.valueOf(transactions.size()),
                        4,
                        RoundingMode.HALF_UP
                );
    }
}