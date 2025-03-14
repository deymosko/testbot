package com.detmosko.task.testbot.controller;

import com.detmosko.task.testbot.entity.Transaction;
import com.detmosko.task.testbot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deymosko_bot/transactions")
public class TransactionController
{
    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAll")
    public List<Transaction> getTransactions()
    {
        return transactionService.getAll();
    }
    @GetMapping("/getById{id}")
    public Transaction getTransactionById(@PathVariable int id)
    {
        return transactionService.findById(id);
    }

}
