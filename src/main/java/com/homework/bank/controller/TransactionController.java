package com.homework.bank.controller;

import com.homework.bank.model.PageResponse;
import com.homework.bank.model.Transaction;
import com.homework.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping
    public String getTransactions( @RequestParam String userId,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   Model model) {

        PageResponse pageResponse = transactionService.getPagedListByUserId(userId, pageIndex, pageSize);
        model.addAttribute("userId", userId);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", pageSize);

        if (pageResponse == null) {
            model.addAttribute("transactions", new ArrayList<Transaction>());
            model.addAttribute("totalPage", 1);
        }
        else {
            List<Transaction> transactions = pageResponse.getList();
            model.addAttribute("transactions", transactions);
            model.addAttribute("totalPage", pageResponse.getTotalPage());
        }

        return "transactions";
    }

    @GetMapping("/{userId}/{id}")
    public String getTransactionById(@PathVariable String id, @PathVariable String userId, Model model) {
        Transaction transaction = transactionService.findById(id, userId);

        if (transaction == null) {
            transaction = new Transaction();
        }

        model.addAttribute("transaction", transaction);
        return "transactionDetail";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam String userId, Model model) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        model.addAttribute("transaction", transaction);
        model.addAttribute("userId", userId);
        return "transactionDetail";
    }

    @PostMapping("/create")
    public String createTransaction(@Valid @ModelAttribute Transaction transaction, RedirectAttributes redirectAttributes) {
        transaction.setId(UUID.randomUUID().toString());
        transactionService.save(transaction);
        redirectAttributes.addAttribute("userId", transaction.getUserId());
        return "redirect:/transactions";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam String id, @RequestParam String userId, Model model) {
        Transaction transaction = transactionService.findById(id, userId);
        model.addAttribute("transaction", transaction);
        model.addAttribute("userId", userId);
        return "transactionDetail";
    }

    @PostMapping("/edit")
    public String updateTransaction(@Valid @ModelAttribute Transaction transaction, RedirectAttributes redirectAttributes) {
        transactionService.save(transaction);
        redirectAttributes.addAttribute("userId", transaction.getUserId());
        return "redirect:/transactions";
    }

    @GetMapping("/delete")
    public String deleteTransaction(@RequestParam String id, @RequestParam String userId, RedirectAttributes redirectAttributes) {
        transactionService.deleteById(id, userId);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/transactions";
    }
}
