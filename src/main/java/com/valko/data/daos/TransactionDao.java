package com.valko.data.daos;

import com.valko.models.Transaction;

import java.util.List;

public interface TransactionDao
{
    List<Transaction> getTransactionsByAccountID(long accountID);
    boolean insertTransaction(Transaction transaction);
}