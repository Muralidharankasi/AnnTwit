package com.imstudios.anntwit.db.tweet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TweeterUtil extends TweeterCoreUtil {
    private static TweeterUtil tweeterUtil = null;

    private TweeterUtil() {

    }

    public static TweeterUtil getInstance() {
        if (tweeterUtil == null) {
            tweeterUtil = new TweeterUtil();
        }
        return tweeterUtil;
    }


    public ResultSet getAllResponses(long tweetId, int offSet, int limit) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("select * from responses left join ann_user on responses.user_id = ann_user.user_id where tweet_id=? limit "+offSet+","+limit);
            statement.setLong(1, tweetId);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean addResponse(Long tweetID, Long userID, String response) {
        super.updateExpiryTime(tweetID);
        try {
            String sqlSuff = "(tweet_id, response, user_id) values(?,?,?)";
            if (userID == null) {
                sqlSuff = "(tweet_id, response) values(?,?)";
            }
            PreparedStatement statement = dbConnection.prepareStatement("insert into responses" + sqlSuff);
            int index = 0;
            statement.setLong(++index, tweetID);
            statement.setString(++index, response);
            if (userID != null) {
                statement.setLong(++index, userID);
            }
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
