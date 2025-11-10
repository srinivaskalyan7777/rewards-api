package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.exception.RewardException;
import com.assignment.rewardsapi.model.Customer;
import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that contains business logic for computing reward points.
 * Uses in-memory mock data (customers + transactions) for demo purposes.
 */
@Service
public class RewardService {

    private static final Map<String, Customer> customersDB = new HashMap<>();
    private static final List<Transaction> transactionsDB = new ArrayList<>();

    static {
        // seed customers
        customersDB.put("CUST001", new Customer("CUST001", "John Doe", "john.doe@example.com"));
        customersDB.put("CUST002", new Customer("CUST002", "Jane Smith", "jane.smith@example.com"));

        // seed transactions (dates relative to now for easy testing)
        transactionsDB.add(new Transaction("T1", "CUST001", LocalDate.now().minusDays(5), 120.00));
        transactionsDB.add(new Transaction("T2", "CUST001", LocalDate.now().minusDays(30), 75.00));
        transactionsDB.add(new Transaction("T3", "CUST001", LocalDate.now().minusDays(70), 200.00));
        transactionsDB.add(new Transaction("T4", "CUST002", LocalDate.now().minusDays(10), 150.00));
        transactionsDB.add(new Transaction("T5", "CUST002", LocalDate.now().minusDays(95), 60.00));
    }

    /**
     * Calculate rewards for a customer within the last `months`.
     * Returns a CustomerRewardResponse (customer info + transactions + monthly map + total).
     */
    public CustomerRewardResponse calculateRewards(String customerId, int months) {
        // service-level validation
        if (months <= 0) {
            throw new RewardException("Timeframe (months) must be greater than 0.");
        }
        if (months > 3) {
            throw new RewardException("Timeframe (months) must not exceed 3.");
        }

        Customer customer = customersDB.get(customerId);
        if (customer == null) {
            throw new RewardException("Customer not found: " + customerId);
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months);

        // filter transactions for this customer and timeframe
        List<Transaction> filtered = transactionsDB.stream()
                .filter(t -> t.getCustomerId().equals(customerId)
                        && ( !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate) ))
                .collect(Collectors.toList());

        // compute per-transaction points and monthly aggregation
        Map<YearMonth, Integer> monthlyPoints = new TreeMap<>();
        int totalPoints = 0;

        for (Transaction t : filtered) {
            int pts = calculatePoints(t.getAmount());
            totalPoints += pts;
            YearMonth ym = YearMonth.from(t.getDate());
            monthlyPoints.merge(ym, pts, Integer::sum);
        }

        // build response (use existing CustomerRewardResponse naming)
        CustomerRewardResponse resp = new CustomerRewardResponse();
        resp.setCustomerId(customerId);
        resp.setName(customer.getName());
        resp.setTransactions(filtered);
        resp.setMonthlyRewards(monthlyPoints);
        resp.setTotalRewards(totalPoints);

        return resp;
    }

    /**
     * Points rule:
     * - For every dollar over 100: 2 points per dollar
     * - For every dollar between 50 and 100: 1 point per dollar
     * - <=50: 0
     */
    private int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        if (amount <= 100) return (int) Math.floor(amount - 50);
        // amount > 100
        return 50 + (int) Math.floor((amount - 100) * 2);
    }
}
