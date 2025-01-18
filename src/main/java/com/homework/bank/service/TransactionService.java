package com.homework.bank.service;

import com.homework.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionService {

    private Transaction mock;

    // userId -> (transactionId -> transaction)
    private ConcurrentHashMap<String, LinkedHashMap<String, Transaction>> transactionsMap;

    //TODO: 使用hashmap会怎样
//    private HashMap<String, List<Transaction>> transactions;
    //TODO: 分类查询要加索引

    public TransactionService() {

        transactionsMap = new ConcurrentHashMap<>();

        mock = new Transaction();
        mock.setId(UUID.randomUUID().toString());
//        mock.setAccountNumber("123456789");
        mock.setAmount(BigDecimal.valueOf(1000));
        mock.setTransactionType("DEPOSIT");

//        transactionsMap.put(mock.getId(), List.of(mock));
    }

    public List<Transaction> getPagedListByUserId(String userId, Integer pageIndex, Integer pageSize) {

        if (userId == null || pageIndex == null || pageSize == null) {
            return null;
        }

        if (transactionsMap.containsKey(userId)) {
            LinkedHashMap<String, Transaction> transactions = transactionsMap.get(userId);
//            List<Transaction> transactions = transactionsMap.get(userId);
            if (transactions != null && !transactions.isEmpty()) {
                return pageHelper(transactions, pageIndex, pageSize);
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
    private List<Transaction> pageHelper(LinkedHashMap<String, Transaction> map, Integer pageIndex, Integer pageSize) {

        List<Transaction> list = new ArrayList<>(map.values());

        int total = list.size();
        int end = total - (pageIndex - 1) * pageSize;
        int begin = Math.max(0, end - 10);
        List<Transaction> pagedList = list.subList(begin, end);
        Collections.reverse(pagedList);

        return pagedList;

    }

//    /**
//     * 分页后取出对应页码的数据，由于原来的数组是逆序存进来的，所以这里做了逆序取出的方法
//     * @param list 用户的交易列表
//     * @param pageIndex 页码
//     * @param pageSize 页的大小
//     * @return
//     */
//    private List<Transaction> pageHelper(List<Transaction> list, Integer pageIndex, Integer pageSize) {
//
//        Integer total = list.size();
//        Integer end = total - (pageIndex - 1) * pageSize - 1;
//        Integer begin = Math.max(0, end - 9);
//        List<Transaction> pagedList = list.subList(begin, end);
//        Collections.reverse(pagedList);
//
//        return pagedList;
//
//    }

    public Transaction save(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

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
        if (transactionsMap.containsKey(userId)) {
            LinkedHashMap<String, Transaction> transactions = transactionsMap.get(userId);
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
}