package com.imstudios.anntwit.api.tweet;

import com.imstudios.anntwit.db.tweet.TweeterUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TweeterService {
    private static TweeterService tweetService = null;
    private TweeterService(){

    }

    public static TweeterService getInstance() {
        if (tweetService == null) {
            tweetService = new TweeterService();
        }
        return tweetService;
    }

    public boolean createTweet(Long userID, String statement) {
        return TweeterUtil.getInstance().createTweet(userID, statement);
    }

    public boolean addTweetResponse(Long tweetID, Long userID, String statement) {
        return TweeterUtil.getInstance().addResponse(tweetID, userID, statement);
    }

    public List<HashMap> getTweets(int offSet, int limit) {
        List<HashMap> tweetList = new ArrayList<>();
        try {
            HashMap<String, Object> map;
            ResultSet resultSet = TweeterUtil.getInstance().getTweets(offSet, limit);
            if (resultSet != null) {
                while (resultSet.next()) {
                    map = new HashMap<>();
                    map.put("tweet", resultSet.getString("statement"));
                    map.put("expiry", resultSet.getString("expiry_time"));
                    map.put("likes", resultSet.getString("likes"));
                    map.put("user", resultSet.getString("user_name"));
                    tweetList.add(map);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tweetList;
    }

    public List<HashMap> getResponses(long tweetID, int offSet, int limit) {
        List<HashMap> tweetList = new ArrayList<>();
        try {
            HashMap<String, Object> map;
            ResultSet resultSet = TweeterUtil.getInstance().getAllResponses(tweetID, offSet, limit);
            if (resultSet != null) {
                while (resultSet.next()) {
                    map = new HashMap<>();
                    map.put("response", resultSet.getString(4));
                    map.put("user", resultSet.getString(6));
                    tweetList.add(map);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tweetList;
    }

}
