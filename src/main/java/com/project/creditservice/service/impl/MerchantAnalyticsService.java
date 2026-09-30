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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantAnalyticsService implements IMerchantAnalyticsService {

    private final TransactionRepository transactionRepository;

    @Override
    public MerchantFeatures calculateMerchantFeatures(Long merchantId) {

        List<Transaction> transactions =
                transactionRepository.findByMerchantId(merchantId);

        if (transactions.isEmpty()) {
            return new MerchantFeatures(
                    merchantId,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    0,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        BigDecimal totalRevenue = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageTransactionValue =
                totalRevenue.divide(
                        BigDecimal.valueOf(transactions.size()),
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal revenueVolatility =
                calculateRevenueVolatility(transactions);

        BigDecimal monthlyRevenue =
                calculateMonthlyRevenue(transactions);

        BigDecimal weekendTransactionRatio =
                calculateWeekendRatio(transactions);

        return new MerchantFeatures(
                merchantId,
                totalRevenue,
                averageTransactionValue,
                transactions.size(),
                revenueVolatility,
                monthlyRevenue,
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

    private BigDecimal calculateMonthlyRevenue(
            List<Transaction> transactions) {

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateWeekendRatio(
            List<Transaction> transactions) {

        long weekendTransactions = transactions.stream()
                .filter(transaction -> {

                    DayOfWeek day =
                            transaction.getTransactionDate()
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