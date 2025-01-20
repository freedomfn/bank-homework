package com.homework.bank;

import com.homework.bank.controller.TransactionController;
import com.homework.bank.model.Transaction;
import com.homework.bank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMVC;

    @Mock
    private TransactionService transactionService;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        //初始化
        MockitoAnnotations.openMocks(this);
        //构建mvc环境
        mockMVC = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    // TODO:尚未能使接口的@valid注解在测试类中生效，先注释掉
//    @Test
//    public void testSaveTransaction_NullAmount() {
//        // 准备数据
//        Transaction transaction = getNewTransaction("1", "user1", null, "DEPOSIT");
//
//        // 执行方法并验证结果
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            transactionController.createTransaction(transaction, redirectAttributes);
//        });
//
//        // 验证异常信息
//        assertEquals("Transaction amount cannot be null", exception.getMessage());
//    }

    private Transaction getNewTransaction(String id, String userId, BigDecimal amount, String transactionType) {

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setId(id);
        transaction.setTransactionType(transactionType);

        return transaction;
    }
}
