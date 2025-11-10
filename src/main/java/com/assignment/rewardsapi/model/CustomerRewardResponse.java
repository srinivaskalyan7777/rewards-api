package com.assignment.rewardsapi.model;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class CustomerRewardResponse {

    private String customerId;
    private String name; // customer name
    private List<Transaction> transactions;
    private Map<YearMonth, Integer> monthlyRewards;
    private int totalRewards;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public Map<YearMonth, Integer> getMonthlyRewards() { return monthlyRewards; }
    public void setMonthlyRewards(Map<YearMonth, Integer> monthlyRewards) { this.monthlyRewards = monthlyRewards; }

    public int getTotalRewards() { return totalRewards; }
    public void setTotalRewards(int totalRewards) { this.totalRewards = totalRewards; }
}
