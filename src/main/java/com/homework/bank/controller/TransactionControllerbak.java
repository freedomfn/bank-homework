//package com.homework.bank.controller;
//
//import com.homework.bank.model.Transaction;
//import com.homework.bank.service.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/transactions")
//public class TransactionControllerbak {
//    @Autowired
//    private TransactionService transactionService;
//
//    @GetMapping
//    public List<Transaction> getAllTransactions(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
//        return transactionService.getPagedListByUserId(id, page, size);
//    }
//
//    @GetMapping("/{userId}/{id}")
//    public Transaction getTransactionById(@PathVariable String id, @PathVariable String userId) {
//        return transactionService.findById(id, userId);
//    }
//
//    @PostMapping
//    public Transaction createTransaction(@RequestBody Transaction transaction) {
//        return transactionService.save(transaction);
//    }
//
//    @PutMapping("/{id}")
//    public Transaction updateTransaction(@RequestBody Transaction transactionDetails) {
//        Transaction transaction = transactionService.save(transactionDetails);
//        return transaction;
//    }
//
//    @DeleteMapping("/{userId}/{id}")
//    public void deleteTransaction(@PathVariable String id, @PathVariable String userId) {
//        transactionService.deleteById(id, userId);
//    }
//}