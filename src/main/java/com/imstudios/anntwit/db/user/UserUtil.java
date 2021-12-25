package com.imstudios.anntwit.db.user;

import com.imstudios.anntwit.appcore.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUtil {

    public static Connection dbConnection;
    private static UserUtil userUtil = null;

    static {
        try {
            dbConnection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserUtil getInstance() {
        if (userUtil == null) {
            userUtil = new UserUtil();
        }
        return userUtil;
    }

    public String validateUserLogin(String userName) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select user_password from ann_user where user_name=?");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("user_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserExists(String userName) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select user_name from ann_user where user_name=?");
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public boolean createUser(String userName, String password, String emailID) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("insert into ann_user(user_name,user_email,user_password) values(?,?,?)");
            statement.setString(1, userName);
            statement.setString(2, emailID);
            statement.setString(3, password);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String userName, String password) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("delete from ann_user where user_name=?");
            statement.setString(1, userName);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
