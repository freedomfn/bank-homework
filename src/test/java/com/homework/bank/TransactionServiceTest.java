package com.homework.bank;

import com.homework.bank.model.PageResponse;
import com.homework.bank.model.Transaction;
import com.homework.bank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPagedListByUserId() {
        // 准备数据
        String userId = "user1";
        int pageIndex = 1;
        int pageSize = 10;

        LinkedHashMap<String, Transaction> transactionsMap = new LinkedHashMap<>();
        transactionsMap.put("1", getNewTransaction("1", userId, BigDecimal.valueOf(100), "DEPOSIT"));
        transactionsMap.put("2", getNewTransaction("2", userId, BigDecimal.valueOf(200), "WITHDRAWAL"));

        transactionService.transactionsMap.put(userId, transactionsMap);

        // 执行方法
        PageResponse pageResponse = transactionService.getPagedListByUserId(userId, pageIndex, pageSize);

        // 验证结果
        assertNotNull(pageResponse);
        assertEquals(2, pageResponse.getList().size());
        assertEquals(1, pageResponse.getTotalPage());
    }

    @Test
    public void testGetPagedListByUserId_EmptyTransactions() {
        // 准备数据
        String userId = "user1";
        int pageIndex = 1;
        int pageSize = 10;

        transactionService.transactionsMap.put(userId, new LinkedHashMap<>());

        // 执行方法
        PageResponse pageResponse = transactionService.getPagedListByUserId(userId, pageIndex, pageSize);

        // 验证结果
        assertNull(pageResponse);
    }

    @Test
    public void testGetPagedListByUserId_NullUserId() {
        // 准备数据
        String userId = null;
        int pageIndex = 1;
        int pageSize = 10;

        // 执行方法
        PageResponse pageResponse = transactionService.getPagedListByUserId(userId, pageIndex, pageSize);

        // 验证结果
        assertNull(pageResponse);
    }

    @Test
    public void testSaveTransaction_Duplicate() {
        // 准备数据
        Transaction transaction = getNewTransaction("1", "user1", BigDecimal.valueOf(100), "DEPOSIT");

        // 第一次保存
        transactionService.save(transaction);

        // 第二次保存（重复）
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.save(transaction);
        });

        // 验证结果
        assertEquals("Duplicate submission detected. Please wait for a while before performing the operation.", exception.getMessage());
    }

    @Test
    public void testDeleteById() {
        // 准备数据
        String userId = "user1";
        String transactionId = "1";

        LinkedHashMap<String, Transaction> transactionsMap = new LinkedHashMap<>();
        transactionsMap.put(transactionId, getNewTransaction(transactionId, userId, BigDecimal.valueOf(100), "DEPOSIT"));

        transactionService.transactionsMap.put(userId, transactionsMap);

        // 执行方法
        transactionService.deleteById(transactionId, userId);

        // 验证结果
        assertTrue(transactionService.transactionsMap.get(userId).isEmpty());
    }

    @Test
    public void testDeleteById_TransactionNotFound() {
        // 准备数据
        String userId = "user1";
        String transactionId = "1";

        // 执行方法
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.deleteById(transactionId, userId);
        });

        // 验证结果
        assertEquals("Transaction not exists!", exception.getMessage());
    }

    @Test
    public void testFindById() {
        // 准备数据
        String userId = "user1";
        String transactionId = "1";

        LinkedHashMap<String, Transaction> transactionsMap = new LinkedHashMap<>();
        transactionsMap.put(transactionId, getNewTransaction(transactionId, userId, BigDecimal.valueOf(100), "DEPOSIT"));

        transactionService.transactionsMap.put(userId, transactionsMap);

        // 执行方法
        Transaction transaction = transactionService.findById(transactionId, userId);

        // 验证结果
        assertNotNull(transaction);
        assertEquals(transactionId, transaction.getId());
        assertEquals(userId, transaction.getUserId());
        assertEquals(BigDecimal.valueOf(100), transaction.getAmount());
        assertEquals("DEPOSIT", transaction.getTransactionType());
    }

    private Transaction getNewTransaction(String id, String userId, BigDecimal amount, String transactionType) {

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setId(id);
        transaction.setTransactionType(transactionType);

        return transaction;
    }
}
