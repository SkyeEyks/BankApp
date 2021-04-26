package com.skye;

import java.sql.*;

public class DatabaseManager {
    private static final String conUrl = "jdbc:derby:DB;create=true";

    public static void createAccount(int id, String fName, String lName) throws SQLException {
        String sql = "INSERT INTO account(id, firstname, lastname, balance) VALUES(?, ?, ?, ?)";
        try(Connection con = DriverManager.getConnection(conUrl))
        {
            con.setSchema("SKYE");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, fName);
            ps.setString(3, lName);
            ps.setDouble(4, 0);
            ps.executeUpdate();
            con.commit();
        }
    }

    public static double getBalance(int accountId) throws SQLException {
        String sql = "SELECT balance FROM account WHERE id = ?";
        try(Connection con = DriverManager.getConnection(conUrl))
        {
            con.setSchema("SKYE");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getDouble(1);
            else
                return -1;
        }
    }

    public static boolean deposit(int id, double amount) throws SQLException {
        try(Connection con = DriverManager.getConnection(conUrl))
        {
            con.setSchema("SKYE");
            String sql = "UPDATE account SET balance = balance + ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, id);

            if (amount >= 0)
            {
                ps.executeUpdate();
                con.commit();
                return true;
            } else
            {
                String sqlCheck = "SELECT balance >= ? FROM account WHERE id = ?";
                PreparedStatement _ps = con.prepareStatement(sqlCheck);
                _ps.setDouble(1, amount);
                _ps.setInt(2, id);
                ResultSet rs = _ps.executeQuery();
                if(rs.next())
                {
                    if(rs.getBoolean(1))
                    {
                        ps.executeUpdate();
                        con.commit();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
