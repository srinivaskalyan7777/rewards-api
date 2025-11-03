package com.assignment.rewardsapi.model;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private LocalDate date;
    private double amount;
    private int rewardPoints;

    public Transaction() {}

    public Transaction(String transactionId, LocalDate date, double amount) {
        this.transactionId = transactionId;
        this.date = date;
        this.amount = amount;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getRewardPoints() { return rewardPoints; }
    public void setRewardPoints(int rewardPoints) { this.rewardPoints = rewardPoints; }
}
