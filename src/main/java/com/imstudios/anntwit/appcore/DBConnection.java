package com.imstudios.anntwit.appcore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ann_twit?autoReconnect=true&useSSL=false", "root", "");
        }
        return connection;
    }
}
