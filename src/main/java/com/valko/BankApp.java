package com.valko;

import com.valko.data.daos.AccountDao;
import com.valko.data.daos.TransactionDao;
import com.valko.data.implementations.AccountDaoImpl;
import com.valko.data.implementations.TransactionDaoImpl;
import com.valko.models.Account;
import com.valko.models.Operation;
import com.valko.models.Transaction;
import com.valko.models.TransactionType;
import com.valko.models.exceptions.NegativeAmountException;
import com.valko.models.exceptions.NotEnoughMoneyException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankApp
{
    private static AccountDao accountDao = new AccountDaoImpl();
    private static TransactionDao transactionDao = new TransactionDaoImpl();
    private static Scanner scanner = new Scanner(System.in);


    private static void showOperations()
    {
        for(Operation op: Operation.values())
            System.out.println(op.ordinal() + " - " + op.getDescription());
        System.out.println("");
    }

    private static void printBanner(Account account)
    {
    System.out.println("\n==============================");
    System.out.println("Welcome   " + account.getFirstName() + " " + account.getLastName());
    System.out.println("Balance : " + account.getBalance() + "€");
    System.out.println("==============================\n");
    }

    private static void viewInformation(Account account)
    {
        System.out.println(account);
    }

    private static void viewLastTransactions(Account account)
    {
        account.setTransactions(transactionDao.getTransactionsByAccountID(account.getAccountID()));
        account.printTransactions();
    }

    private static void exit()
    {
        System.out.println("Good bye !");
    }

    private static void deposit(Account account)
    {
        try
        {
            System.out.print("How much money do you want to deposit ? ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            account.deposit(amount);
            accountDao.updateAccount(account);
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, account, null);
            transactionDao.insertTransaction(transaction);
            System.out.println("Deposit completed... You now have " + account.getBalance() + "€");
        }
        catch(InputMismatchException e)
        {
            System.out.println("[!] You entered an invalid amount");
            scanner.nextLine();
        }
        catch(NegativeAmountException e)
        {
            System.out.println("[!] You entered a negative amount");
        }
    }

    private static void withdraw(Account account)
    {
        try
        {
            System.out.print("How much money do you want to withdraw ? ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            account.withdraw(amount);
            accountDao.updateAccount(account);
            Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, account, null);
            transactionDao.insertTransaction(transaction);
            System.out.println("Withdraw completed... You now have " + account.getBalance() + "€");
        }
        catch(InputMismatchException e)
        {
            System.out.println("[!] You entered an invalid amount");
            scanner.nextLine();
        }
        catch(NegativeAmountException e)
        {
            System.out.println("[!] You entered a negative amount");
        }
        catch(NotEnoughMoneyException e)
        {
            System.out.println("[!] You can't withdraw more money than you have");
        }
    }

    private static void transfer(Account account)
    {
        try
        {
            System.out.print("Enter IBAN of the receiver : ");
            String IBAN = scanner.nextLine();
            Account account2 = accountDao.findAccountByIBAN(IBAN);
            if(account2 == null)
            {
                System.out.println("[!] No bank account found with the given IBAN");
                return;
            }
            System.out.print("How much money do you want to withdraw ? ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            account.transfer(account2, amount);
            accountDao.updateAccount(account);
            accountDao.updateAccount(account2);
            Transaction transaction = new Transaction(TransactionType.TRANSFER, amount, account, account2);
            transactionDao.insertTransaction(transaction);
            System.out.println("Transfer completed");
        }
        catch(InputMismatchException e)
        {
            System.out.println("[!] You entered an invalid amount");
            scanner.nextLine();
        }
        catch(NegativeAmountException e)
        {
            System.out.println("[!] You entered a negative amount");
        }
        catch(NotEnoughMoneyException e)
        {
            System.out.println("[!] You can't transfer more money than you have");
        }
    }


    public static void main(String[] args)
    {
        System.out.print("Enter your credit card number : ");
        String creditCardNumber = scanner.nextLine();
        System.out.print("Enter your secret code : ");
        String secretCode = scanner.nextLine();

        Account account = accountDao.findAccountByCreditCardNumber(creditCardNumber);
        if(account != null && BCrypt.checkpw(secretCode, account.getSecretCode()))
        {
            printBanner(account);
            showOperations();
            int choice;
            Operation operation;

            MainLoop: while(true)
            {
                System.out.print("\nChoose an action : ");
                try
                {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // reads LineFeed character (not consumed by nextInt)
                    operation = Operation.values()[choice];
                }
                catch(InputMismatchException e)
                {
                    System.out.println("[!] You must enter the number corresponding to the action you want to perform");
                    continue;
                }
                catch(Exception e)
                {
                    System.out.println("[!] There is no such action");
                    operation = Operation.SHOW_OPERATIONS;
                }
                switch(operation)
                {
                    case SHOW_OPERATIONS:
                        showOperations();
                        break;

                    case VIEW_ACCOUNT_INFORMATION:
                        viewInformation(account);
                        break;

                    case VIEW_LAST_TRANSACTIONS:
                        viewLastTransactions(account);
                        break;

                    case DEPOSIT:
                        deposit(account);
                        break;

                    case WITHDRAWAL:
                        withdraw(account);
                        break;

                    case TRANSFER:
                        transfer(account);
                        break;

                    case EXIT:
                        exit();
                        break MainLoop;
                }
            }
        }
        else
            System.out.println("Wrong username or password !");
        scanner.close();
    }
}