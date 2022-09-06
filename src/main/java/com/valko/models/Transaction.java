package com.valko.models;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Transaction
{
    long transactionID;
    Timestamp transactionDate;
    TransactionType transactionType;
    int transactionAmount;
    long sourceAccountID;
    String sourceFirstName;
    String sourceLastName;
    String sourceIBAN;
    long destinationAccountID;
    String destinationFirstName;
    String destinationLastName;
    String destinationIBAN;


    // Constructor to insert a transaction
    public Transaction(TransactionType transactionType, int transactionAmount, Account sourceAccount, Account destinationAccount)
    {
        this.transactionDate = new Timestamp(new Date().getTime());
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.sourceAccountID = sourceAccount.getAccountID();
        this.destinationAccountID = (destinationAccount == null) ? -1 : destinationAccount.getAccountID();
    }

    // Constructor to retrieve transactions
    public Transaction(long transactionID, Timestamp transactionDate, TransactionType transactionType, int transactionAmount, long sourceAccountID, String sourceFirstName, String sourceLastName, String sourceIBAN, long destinationAccountID, String destinationFirstName, String destinationLastName, String destinationIBAN)
    {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.sourceAccountID = sourceAccountID;
        this.sourceFirstName = sourceFirstName;
        this.sourceLastName = sourceLastName;
        this.sourceIBAN = sourceIBAN;
        this.destinationAccountID = destinationAccountID;
        this.destinationFirstName = destinationFirstName;
        this.destinationLastName = destinationLastName;
        this.destinationIBAN = destinationIBAN;
    }


    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public long getSourceAccountID() {
        return sourceAccountID;
    }

    public String getSourceFirstName() {
        return sourceFirstName;
    }

    public String getSourceLastName() {
        return sourceLastName;
    }

    public String getSourceIBAN() {
        return sourceIBAN;
    }

    public long getDestinationAccountID() {
        return destinationAccountID;
    }

    public String getDestinationFirstName() {
        return destinationFirstName;
    }

    public String getDestinationLastName() {
        return destinationLastName;
    }

    public String getDestinationIBAN() {
        return destinationIBAN;
    }
}
