package com.valko.data.implementations;

import com.valko.data.DatabaseConnection;
import com.valko.data.daos.TransactionDao;
import com.valko.models.Transaction;
import com.valko.models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao
{
    private static final String FIND_TRANSACTIONS_BY_ACCOUNTID = "SELECT t.transactionID, " +
                                                                        "t.transactionDate, " +
                                                                        "t.transactionType, " +
                                                                        "t.transactionAmount, " +
                                                                        "t.sourceAccountID, " +
                                                                        "a1.firstName AS sourceFirstName, " +
                                                                        "a1.lastName  AS sourceLastName, " +
                                                                        "a1.IBAN      AS sourceIBAN, " +
                                                                        "t.destinationAccountID, " +
                                                                        "a2.firstName AS destinationFirstName, " +
                                                                        "a2.lastName  AS destinationLastName, " +
                                                                        "a2.IBAN      AS destinationIBAN " +
                                                                 "FROM T_Transactions t " +
                                                                 "LEFT JOIN T_Accounts a1 ON t.sourceAccountID=a1.accountID " +
                                                                 "LEFT JOIN T_Accounts a2 ON t.destinationAccountID=a2.accountID " +
                                                                 "WHERE t.sourceAccountID=? OR t.destinationAccountID=? " +
                                                                 "ORDER BY transactionDate DESC";

    private static final String INSERT_TRANSACTION_TRANSFER = "INSERT INTO T_Transactions (transactionDate," +
                                                                                          "transactionType, " +
                                                                                          "transactionAmount, " +
                                                                                          "sourceAccountID, " +
                                                                                          "destinationAccountID) " +
                                                              "VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_TRANSACTION_OTHER = "INSERT INTO T_Transactions (transactionDate," +
                                                                                        "transactionType, " +
                                                                                        "transactionAmount, " +
                                                                                        "sourceAccountID) " +
                                                           "VALUES (?, ?, ?, ?)";

    @Override
    public List<Transaction> getTransactionsByAccountID(long accountID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();
        try
        {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(FIND_TRANSACTIONS_BY_ACCOUNTID);
            preparedStatement.setLong(1, accountID);
            preparedStatement.setLong(2, accountID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                transactions.add(new Transaction(resultSet.getLong("transactionID"),
                                                 Timestamp.valueOf(resultSet.getString("transactionDate")),
                                                 TransactionType.valueOf(resultSet.getString("transactionType")),
                                                 resultSet.getInt("transactionAmount"),
                                                 resultSet.getLong("sourceAccountID"),
                                                 resultSet.getString("sourceFirstName"),
                                                 resultSet.getString("sourceLastName"),
                                                 resultSet.getString("sourceIBAN"),
                                                 resultSet.getLong("destinationAccountID"),
                                                 resultSet.getString("destinationFirstName"),
                                                 resultSet.getString("destinationLastName"),
                                                 resultSet.getString("destinationIBAN")));
            preparedStatement.close();
            resultSet.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return transactions;
    }


    @Override
    public boolean insertTransaction(Transaction transaction)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = DatabaseConnection.getConnection();

            if(transaction.getDestinationAccountID() != -1)
            {
                preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_TRANSFER);
                preparedStatement.setLong(5, transaction.getDestinationAccountID());
            }
            else
                preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_OTHER);

            preparedStatement.setString(1, transaction.getTransactionDate().toString());
            preparedStatement.setString(2, transaction.getTransactionType().name());
            preparedStatement.setInt(3, transaction.getTransactionAmount());
            preparedStatement.setLong(4, transaction.getSourceAccountID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
