package com.valko.data.implementations;

import com.valko.data.DatabaseConnection;
import com.valko.data.daos.AccountDao;
import com.valko.models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao
{
    private static final String FIND_ACCOUNT_BY_CCN  = "SELECT * FROM T_Accounts WHERE creditCardNumber = ?";

    private static final String FIND_ACCOUNT_BY_IBAN = "SELECT * FROM T_Accounts WHERE IBAN = ?";

    private static final String UPDATE_ACCOUNT       = "UPDATE T_Accounts " +
                                                       "SET firstName = ?, " +
                                                           "lastName = ?, " +
                                                           "IBAN = ?, " +
                                                           "creditCardNumber = ?, " +
                                                           "secretCode = ?, " +
                                                           "balance = ? " +
                                                       "WHERE accountID = ?";


    @Override
    public Account findAccountByCreditCardNumber(String creditCardNumber)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        try
        {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ACCOUNT_BY_CCN);
            preparedStatement.setString(1, creditCardNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                account = new Account(
                        resultSet.getLong("accountID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("creditCardNumber"),
                        resultSet.getString("secretCode"),
                        resultSet.getInt("balance")
                );
            preparedStatement.close();
            resultSet.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return account;
    }


    @Override
    public Account findAccountByIBAN(String IBAN)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        try
        {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ACCOUNT_BY_IBAN);
            preparedStatement.setString(1, IBAN);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                account = new Account(
                        resultSet.getLong("accountID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("creditCardNumber"),
                        resultSet.getString("secretCode"),
                        resultSet.getInt("balance")
                );
            preparedStatement.close();
            resultSet.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return account;
    }


    @Override
    public boolean updateAccount(Account account)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = DatabaseConnection.getConnection();

            preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT);
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getIBAN());
            preparedStatement.setString(4, account.getCreditCardNumber());
            preparedStatement.setString(5, account.getSecretCode());
            preparedStatement.setInt(6, account.getBalance());
            preparedStatement.setLong(7, account.getAccountID());

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
