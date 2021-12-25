package com.imstudios.anntwit.db.tweet;

import com.imstudios.anntwit.appcore.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TweeterCoreUtil {
    public static Connection dbConnection;
    private static TweeterCoreUtil tweeterCoreUtil = null;

    static {
        try {
            dbConnection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static TweeterCoreUtil getInstance() {
        if (tweeterCoreUtil == null) {
            tweeterCoreUtil = new TweeterCoreUtil();
        }
        return tweeterCoreUtil;
    }

    public void cleanOldTweets() {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("delete from tweets where expiry_time<=?");
            statement.setString(1, System.currentTimeMillis() + "");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getTweets(int offSet, int limit) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from tweets left join ann_user on tweets.user_id = ann_user.user_id where  expiry_time>=?");
            statement.setLong(1, System.currentTimeMillis());
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateExpiryTime(long tweetId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from tweets where id>=?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, tweetId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long expTime = resultSet.getLong(2);
                long upTime = resultSet.getLong(3);
                if (upTime < 3) return;
                resultSet.updateLong(2, expTime + (3600000L * upTime));
                resultSet.updateLong(3, upTime / 2);
                resultSet.updateRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createTweet(Long userID, String tweetStatement) {
        try {
            String sql = "(user_id, statement, expiry_time) values(?,?,?)";
            if (userID == null) {
                sql = "(statement, expiry_time) values(?,?)";
            }
            PreparedStatement statement = dbConnection.prepareStatement("insert into tweets" + sql);
            int index = 1;
            if (userID != null) statement.setLong(index++, userID);
            statement.setString(index++, tweetStatement);
            statement.setLong(index, System.currentTimeMillis() + (24 * 3600000L));
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
