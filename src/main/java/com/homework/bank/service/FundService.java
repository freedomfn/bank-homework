package com.homework.bank.service;

import com.homework.bank.model.Fund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class FundService {
    private static ConcurrentHashMap<Integer, Fund> funds;

    static {
        funds = new ConcurrentHashMap<>();

        Fund fund1 = new Fund();
        fund1.setId(1);
        fund1.setAmount(new BigDecimal(1000000));
        fund1.setName("保利1号");

        Fund fund2 = new Fund();
        fund2.setId(2);
        fund2.setAmount(new BigDecimal(2000000));
        fund2.setName("万科2号");

        Fund fund3 = new Fund();
        fund3.setId(3);
        fund3.setAmount(new BigDecimal(3000000));
        fund3.setName("恒大3号");

        funds.put(fund1.getId(), fund1);
        funds.put(fund2.getId(), fund2);
        funds.put(fund3.getId(), fund3);
    }

    public ConcurrentHashMap<Integer, Fund> getFunds() {
        return funds;
    }
}
