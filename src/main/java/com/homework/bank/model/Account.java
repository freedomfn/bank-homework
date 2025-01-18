package com.homework.bank.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private String userId;

    private BigDecimal amount;

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
}
