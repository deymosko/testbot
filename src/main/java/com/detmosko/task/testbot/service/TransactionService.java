package com.detmosko.task.testbot.service;

import com.detmosko.task.testbot.entity.Transaction;
import com.detmosko.task.testbot.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService
{
    @Autowired
    public TransactionRepo transactionRepo;

    public void add(Transaction transaction)
    {
        transactionRepo.save(transaction);
    }
    public List<Transaction> getAll()
    {
        return transactionRepo.findAll();
    }
    public Transaction findById(int id)
    {
        return transactionRepo.findTransactionById(id);
    }

}
