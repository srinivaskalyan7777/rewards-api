package com.assignment.rewardsapi.model;

import java.util.List;

public class CustomerRewardResponse {
    private String customerId;
    private String customerName;
    private int timeFrameInMonths;
    private int transactionCount;
    private double totalAmount;
    private int totalRewardPoints;
    private List<Transaction> transactions;

    public CustomerRewardResponse(String customerId, String customerName, int timeFrameInMonths,
                                  int transactionCount, double totalAmount, int totalRewardPoints,
                                  List<Transaction> transactions) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.timeFrameInMonths = timeFrameInMonths;
        this.transactionCount = transactionCount;
        this.totalAmount = totalAmount;
        this.totalRewardPoints = totalRewardPoints;
        this.transactions = transactions;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getTimeFrameInMonths() { return timeFrameInMonths; }
    public void setTimeFrameInMonths(int timeFrameInMonths) { this.timeFrameInMonths = timeFrameInMonths; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getTotalRewardPoints() { return totalRewardPoints; }
    public void setTotalRewardPoints(int totalRewardPoints) { this.totalRewardPoints = totalRewardPoints; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
