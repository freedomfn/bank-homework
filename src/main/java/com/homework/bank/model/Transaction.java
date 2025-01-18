package com.homework.bank.model;

import java.math.BigDecimal;

public class Transaction {
    private String id;
    private String userId;
//    private String accountNumber;
    private BigDecimal amount;
    private String transactionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getAccountNumber() {
//        return accountNumber;
//    }
//public void setAccountNumber(String accountNumber) {
//    this.accountNumber = accountNumber;
//}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}