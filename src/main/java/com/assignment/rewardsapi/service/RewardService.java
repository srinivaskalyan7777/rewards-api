package com.assignment.rewardsapi.service;

import com.assignment.rewardsapi.exception.RewardException;
import com.assignment.rewardsapi.model.Customer;
import com.assignment.rewardsapi.model.CustomerRewardResponse;
import com.assignment.rewardsapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

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
        transactionsDB.add(new Transaction("T1", "CUST001", LocalDate.now().minusDays(5), new BigDecimal("120.00")));
        transactionsDB.add(new Transaction("T2", "CUST001", LocalDate.now().minusDays(30), new BigDecimal("75.00")));
        transactionsDB.add(new Transaction("T3", "CUST001", LocalDate.now().minusDays(70), new BigDecimal("200.00")));
        transactionsDB.add(new Transaction("T4", "CUST002", LocalDate.now().minusDays(10), new BigDecimal("150.00")));
        transactionsDB.add(new Transaction("T5", "CUST002", LocalDate.now().minusDays(95), new BigDecimal("60.00")));
    }

    /**
     * Calculates rewards for a given customer between startDate and endDate.
     * If startDate or endDate are null, defaults to last 3 months.
     */
    public CustomerRewardResponse calculateRewards(String customerId, LocalDate startDate, LocalDate endDate) {

        Customer customer = customersDB.get(customerId);
        if (customer == null) {
            throw new RewardException("Customer not found: " + customerId);
        }

        // default to last 3 months if dates not provided
        LocalDate effectiveEndDate = (endDate != null) ? endDate : LocalDate.now();
        LocalDate effectiveStartDate = (startDate != null) ? startDate : effectiveEndDate.minusMonths(3);

        // validation
        if (effectiveStartDate.isAfter(effectiveEndDate)) {
            throw new RewardException("Start date cannot be after end date.");
        }

        Period diff = Period.between(effectiveStartDate, effectiveEndDate);
        int totalMonths = diff.getMonths() + (diff.getYears() * 12);
        if (totalMonths > 3 || (totalMonths == 3 && diff.getDays() > 0)) {
            throw new RewardException("Date range cannot exceed 3 months.");
        }
        // filter transactions
        List<Transaction> filtered = transactionsDB.stream()
                .filter(t -> t.getCustomerId().equals(customerId)
                        && !t.getDate().isBefore(effectiveStartDate)
                        && !t.getDate().isAfter(effectiveEndDate))
                .collect(Collectors.toList());

        // compute monthly and total rewards
        Map<YearMonth, Integer> monthlyPoints = new TreeMap<>();
        int totalPoints = 0;

        for (Transaction t : filtered) {
            int pts = calculatePoints(t.getAmount());
            totalPoints += pts;
            YearMonth ym = YearMonth.from(t.getDate());
            monthlyPoints.merge(ym, pts, Integer::sum);
        }

        // build response
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
    private int calculatePoints(BigDecimal amount) {
        BigDecimal fifty = new BigDecimal("50");
        BigDecimal hundred = new BigDecimal("100");

        if (amount.compareTo(fifty) <= 0) {
            return 0;
        }

        if (amount.compareTo(hundred) <= 0) {
            return amount.subtract(fifty).intValue();
        }

        BigDecimal overHundred = amount.subtract(hundred);
        return 50 + overHundred.multiply(new BigDecimal("2")).intValue();
    }
}
