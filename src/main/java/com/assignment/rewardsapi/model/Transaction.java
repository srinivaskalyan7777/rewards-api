package com.assignment.rewardsapi.model;

import java.time.LocalDate;
import java.math.*;

public class Transaction {

    private String transactionId;
    private String customerId;
    private LocalDate date;
    private BigDecimal amount;

    public Transaction() {}

    public Transaction(String transactionId, String customerId, LocalDate date, BigDecimal amount) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
