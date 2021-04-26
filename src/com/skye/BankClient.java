package com.skye;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankClient {
    private static final String username = "Skye", password = "password";

    public static void main(String[] args) throws SQLException {
        createTable();
        new BankGUI();
    }


    private static void createTable() throws SQLException {
        String sqlCreate = "" +
                "CREATE TABLE account(" +
                "   ID int," +
                "   firstName varchar(255)," +
                "   lastName varchar(255)," +
                "   balance double," +
                "   primary key(ID)" +
                ")";
        try(Connection connection = DriverManager.getConnection("jdbc:derby:DB;create=true", username, password))
        {
            try {
                connection.prepareStatement(sqlCreate).executeUpdate();
            } catch (SQLException e) {}
            connection.commit();
        }
    }
}
