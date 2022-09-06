package com.valko.models;

import com.valko.models.exceptions.NegativeAmountException;
import com.valko.models.exceptions.NotEnoughMoneyException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.valko.models.TransactionType.*;

public class Account
{
    private long accountID;
    private String firstName;
    private String lastName;
    private String IBAN;
    private String creditCardNumber;
    private String secretCode;
    private int balance;
    private List<Transaction> transactions;


    public Account(long accountID, String firstName, String lastName, String IBAN, String creditCardNumber, String secretCode, int balance)
    {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName.toUpperCase();
        this.IBAN = IBAN;
        this.creditCardNumber = creditCardNumber;
        this.secretCode = secretCode;
        this.balance = balance;
        this.transactions = new ArrayList<Transaction>();
    }


    public long getAccountID() {
        return accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void deposit(int amount) throws NegativeAmountException
    {
        if(amount > 0)
            this.balance += amount;
        else
            throw new NegativeAmountException();
    }

    public void withdraw(int amount) throws NegativeAmountException, NotEnoughMoneyException
    {
        if(amount > 0)
        {
            if(amount <= this.balance)
                this.balance -= amount;
            else
                throw new NotEnoughMoneyException();
        }
        else
            throw new NegativeAmountException();
    }

    public void transfer(Account account, int amount) throws NegativeAmountException, NotEnoughMoneyException
    {
        if(amount > 0)
        {
            if(amount <= this.balance)
            {
                this.balance -= amount;
                account.balance += amount;
            }
            else
                throw new NotEnoughMoneyException();
        }
        else
            throw new NegativeAmountException();
    }


    public void printTransactions()
    {
        if(transactions.size() != 0)
        {
            System.out.printf("|  %-21s  |  %12s  |  %-13s  |  %30s  |  %40s  |\n", "Date", "Amount", "Type", "Name", "IBAN");
            System.out.println("|  _____________________  |  ____________  |  _____________  |  ______________________________  |  ________________________________________  |");

            for (Transaction t : transactions) {
                Timestamp transactionDate = t.getTransactionDate();
                int transactionAmount = t.getTransactionAmount();
                String type = t.getTransactionType().name();
                String sign = t.transactionType == WITHDRAWAL ? "-" : "+";
                String firstName = "";
                String lastName = "";
                String IBAN = "";
                if (t.transactionType == TRANSFER) {
                    if (t.getSourceAccountID() == this.getAccountID())
                    {
                        type = "TRANSFER TO";
                        sign = "-";
                        firstName = t.getDestinationFirstName();
                        lastName = t.getDestinationLastName();
                        IBAN = t.getDestinationIBAN();
                    }
                    else
                    {
                        type = "TRANSFER FROM";
                        firstName = t.getSourceFirstName();
                        lastName = t.getSourceLastName();
                        IBAN = t.getSourceIBAN();
                    }
                }
                System.out.printf("|  %21s  |  %s%10d€  |  %-13s  |  %30s  |  %40s  |\n", transactionDate, sign, transactionAmount, type, firstName + " " + lastName, IBAN);
            }
        }
        else
            System.out.println("No transactions...");
    }


    @Override
    public String toString()
    {
        return "Account owner    = " + this.firstName + " " + this.lastName + "\n" +
               "IBAN             = " + this.IBAN + "\n" +
               "creditCardNumber = " + this.creditCardNumber + "\n" +
               "balance          = " + this.balance + "€";
    }
}
