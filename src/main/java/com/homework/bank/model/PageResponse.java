package com.homework.bank.model;

import java.util.List;

public class PageResponse {
    private List<Transaction> list;
    private int totalPage;

    public void setList(List<Transaction> list) {
        this.list = list;
    }

    public List<Transaction> getList() {
        return list;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
