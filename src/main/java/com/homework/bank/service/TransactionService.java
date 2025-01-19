package com.homework.bank.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.homework.bank.model.PageResponse;
import com.homework.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    // userId -> (transactionId -> transaction)
    private static ConcurrentHashMap<String, LinkedHashMap<String, Transaction>> transactionsMap;

    // 创建一个 Guava Cache 实例，缓存时间为 10 秒
    private final Cache<String, Boolean> cacheForCheckDuplicate = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    //TODO: 添加一个索引进行分类查询

    public TransactionService() {

        transactionsMap = new ConcurrentHashMap<>();
    }

    public PageResponse getPagedListByUserId(String userId, Integer pageIndex, Integer pageSize) {

        if (userId == null || pageIndex == null || pageSize == null) {
            return null;
        }

        if (transactionsMap.containsKey(userId)) {
            LinkedHashMap<String, Transaction> transactions = transactionsMap.get(userId);
            if (transactions != null && !transactions.isEmpty()) {
                PageResponse pageResponse = pageHelper(transactions, pageIndex, pageSize);
                return pageResponse;
            }
        }

        return null;
    }

    //TODO: 分类查询要加索引
//    public List<Transaction> getPagedListByUserIdAndType(String userId, String type, Integer pageIndex, Integer pageSize) {
//
//        if (userId == null || pageIndex == null || pageSize == null) {
//            return null;
//        }
//
//        if (transactionsMap.containsKey(userId)) {
//            List<Transaction> transactions = transactionsMap.get(userId);
//            if (transactions != null && transactions.isEmpty()) {
//                return pageHelper(transactions, pageIndex, pageSize);
//            }
//        }
//
//        return null;
//    }

    /**
     * 分页后取出对应页码的数据，由于原来的数据是逆序存进来的，所以这里做了逆序取出的方法
     * @param map 用户的交易列表
     * @param pageIndex 页码
     * @param pageSize 页的大小
     * @return
     */
    private PageResponse pageHelper(LinkedHashMap<String, Transaction> map, Integer pageIndex, Integer pageSize) {

        List<Transaction> list = new ArrayList<>(map.values());

        int total = list.size();
        int totalPage = total / pageSize + 1;
        int end = total - (pageIndex - 1) * pageSize;
        int begin = Math.max(0, end - 10);
        List<Transaction> pagedList = list.subList(begin, end);
        Collections.reverse(pagedList);

        PageResponse pageResponse = new PageResponse();
        pageResponse.setList(pagedList);
        pageResponse.setTotalPage(totalPage);

        return pageResponse;

    }

    public Transaction save(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        checkDuplicate(transaction);

        if (transactionsMap.containsKey(transaction.getUserId())) {
            LinkedHashMap<String, Transaction> transactions = transactionsMap.get(transaction.getUserId());
            transactions.put(transaction.getId(), transaction);
        } else {
            LinkedHashMap<String, Transaction> transactions = new LinkedHashMap<>();
            transactions.put(transaction.getId(), transaction);
            transactionsMap.put(transaction.getUserId(), transactions);
        }

        return transaction;
    }

    public void deleteById(String id, String userId) {
        LinkedHashMap<String, Transaction> transactions = new LinkedHashMap<>();

        if (transactionsMap.containsKey(userId)) {
            transactions = transactionsMap.get(userId);
            if (transactions.isEmpty() || !transactions.containsKey(id)) {
                throw new RuntimeException("Transaction not exists!");
            }
            transactions.remove(id);
        }
    }

    public Transaction findById(String id, String userId) {

        if (transactionsMap.containsKey(userId)) {
            LinkedHashMap<String, Transaction> transactions = transactionsMap.get(userId);
            return transactions.get(id);
        }

        return null;
    }

    // 检测重复提交
    private void checkDuplicate(Transaction transaction){

        // 生成唯一键
        String key = transaction.getUserId() + "-" + transaction.getAmount() + transaction.getTransactionType();

        if (cacheForCheckDuplicate.getIfPresent(key) != null) {
            throw new RuntimeException("Duplicate submission detected. Please wait for a while before performing the operation.");
        }

        cacheForCheckDuplicate.put(key, true);
    }
}