package com.valko.data;

import java.sql.*;
import java.util.ResourceBundle;

public class DatabaseConnection
{
    private static Connection connection = null;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    private static String url = resourceBundle.getString("url");
    private static String username = resourceBundle.getString("username");
    private static String password = resourceBundle.getString("password");


    public static Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
                connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
