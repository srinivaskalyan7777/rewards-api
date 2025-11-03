package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private static final Logger logger = LoggerFactory.getLogger(RewardService.class);

    public CustomerRewardResponse calculateRewards(String customerId, int months, List<Transaction> transactions) {

    	if (months <= 0) {
            throw new IllegalArgumentException("Timeframe (months) must be greater than 0");
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusMonths(months);

        logger.info("Filtering transactions from {} to {}", startDate, currentDate);

        // Filter transactions in timeframe
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getDate().isAfter(startDate.minusDays(1)) && t.getDate().isBefore(currentDate.plusDays(1)))
                .collect(Collectors.toList());

        // Calculate rewards
        int totalRewardPoints = filteredTransactions.stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();

        double totalAmount = filteredTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        filteredTransactions.forEach(t -> t.setRewardPoints(calculatePoints(t.getAmount())));

        logger.info("Total rewards for {}: {} points", customerId, totalRewardPoints);

        return new CustomerRewardResponse(
                customerId,
                "Customer " + customerId,
                months,
                filteredTransactions.size(),
                totalAmount,
                totalRewardPoints,
                filteredTransactions
        );
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 50 && amount <= 100) {
            points += (int) (amount - 50);
        } else if (amount > 100) {
            points += 50 + (int) (amount - 100) * 2;
        }
        return points;
    }
}
