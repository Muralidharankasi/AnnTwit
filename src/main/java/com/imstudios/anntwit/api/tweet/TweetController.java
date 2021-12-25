package com.imstudios.anntwit.api.tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/app/feature")
public class TweetController {
    TweeterService tweetService = TweeterService.getInstance();

    @POST
    @Produces("tweet/json")
    @Path("tweet")
    public Response createTweet(HashMap<String, String> tweetData) {
        String statement = tweetData.get("statement");
        String userIdStr = tweetData.get("userid");
        if (statement == null || statement.isEmpty()) {
            return Response.status(409, "Bad request").build();// conflict
        }
        Long userid = Long.valueOf(userIdStr);
        boolean status = tweetService.createTweet(userid, statement);
        tweetData.put("status", status ? "Success" : "Failed");
        return Response.ok(tweetData, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces("tweet/json")
    @Path("tweet")
    public Response updateTweet(HashMap<String, String> tweetData) {
        String tweetIDStr = tweetData.get("tweetID");
        String userIdStr = tweetData.get("userid");
        String responseTweet = tweetData.get("responseTweet");
        if (tweetIDStr == null || tweetIDStr.isEmpty() || responseTweet == null || responseTweet.isEmpty()) {
            return Response.status(409, "Bad request").build();// conflict
        }
        boolean status = tweetService.addTweetResponse(Long.valueOf(tweetIDStr), Long.valueOf(userIdStr), responseTweet);
        tweetData.put("status", status ? "Success" : "Failed");
        return Response.ok(tweetData, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces("tweets/json")
    @Path("tweets")
    public Response getTweets(@QueryParam("offSet") Integer offSet, @QueryParam("limit") Integer limit) {
        if (offSet == null) offSet = 0;
        if (limit == null) limit = 10;
        List<HashMap> tweets = tweetService.getTweets(offSet, limit);
        return Response.ok(tweets, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces("responses/json")
    @Path("responses")
    public Response getTweets(@QueryParam("offSet") Integer offSet, @QueryParam("limit") Integer limit, @QueryParam("tweetID") Long tweetID) {
        if (offSet == null) offSet = 0;
        if (limit == null) limit = 10;
        if (tweetID == null) {
            return Response.status(409, "Bad request").build();// conflict
        }
        List<HashMap> tweets = tweetService.getResponses(tweetID, offSet, limit);
        return Response.ok(tweets, MediaType.APPLICATION_JSON).build();
    }
}
