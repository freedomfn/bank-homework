package com.homework.bank.service;

import com.homework.bank.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccountService {

    private static ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public Account getAccountByUserId(String userId){

        Account account = accounts.get(userId);

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        return account;
    }

    public Account createAccount(String userId){

        Account account = new Account();

        account.setUserId(userId);
        // 简单起见直接设置账户金额
        account.setAmount(new BigDecimal(100000));

        accounts.put(userId, account);
        return account;
    }
}
