import com.imstudios.anntwit.api.tweet.TweeterService;
import com.imstudios.anntwit.api.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public class Main {
    TweeterService tweetService = TweeterService.getInstance();
    UserService userService = UserService.getInstance();

    public static void main(String[] args) throws Exception {
        new Main().testUserAPIs();
        new Main().testTwitterAPIs();
    }

    private void testTwitterAPIs() throws Exception {
        Callable loadFewTweets = (() -> {
            tweetService.createTweet(2l, "Hai this is my first first tweet");
            tweetService.createTweet(2l, "Hai this is my first second tweet");
            tweetService.createTweet(3l, "Hai this is my second first tweet");
            tweetService.createTweet(null, "This is anonymous tweet");
            return null;
        });
        loadFewTweets.call();
        Callable loadFewResponses = (() -> {
            tweetService.addTweetResponse(3l, null, "Hay I agree with you man");
            tweetService.addTweetResponse(3l, 3l, "Hay I dis agree with you man");
            return null;
        });
        loadFewResponses.call();

        Callable readTweets = (() -> {
            List<HashMap> list = tweetService.getTweets(0, 10);
            for (HashMap map : list) {
                System.out.println(map.toString());
            }
            return null;
        });
        readTweets.call();
        Callable readResponses = (() -> {
            List<HashMap> list = tweetService.getResponses(3, 10, 100);
            for (HashMap map : list) {
                System.out.println(map.toString());
            }
            return null;
        });
        readResponses.call();
    }
    public void testUserAPIs() {
        Callable loadSingUps = (() -> {
            int userCount = 0;
            System.out.println(++userCount + " user created " + userService.createUser("user1", "admin", "user@email.com"));
            System.out.println(++userCount + " user created " + userService.createUser("user2", "admin", "user@email.com"));
            System.out.println(++userCount + " user created " + userService.createUser("user3", "admin", "user@email.com"));
            System.out.println(++userCount + " user created " + userService.createUser("user3", "admin", "user@email.com"));
            return null;
        });
        Callable loadSignIns = (() -> {
            int userCount = 0;
            System.out.println(++userCount + " user1 " + (userService.isUserExists("user1") ? "Exists" : "Not exists") + "user login validated " + userService.validateUserLogin("user1", "admin"));
            System.out.println(++userCount + " user2 " + (userService.isUserExists("user2") ? "Exists" : "Not exists") + "user login validated " + userService.validateUserLogin("user2", "admin"));
            System.out.println(++userCount + " user3 " + (userService.isUserExists("user3") ? "Exists" : "Not exists") + "user login validated " + userService.validateUserLogin("user3", "admin"));
            System.out.println(++userCount + " user3 " + (userService.isUserExists("user4") ? "Exists" : "Not exists") + "user login validated " + userService.validateUserLogin("user4", "admin"));
            return null;
        });
        Callable deleteUser1 = (() -> {
            System.out.println("user1 " + (userService.deleteUser("user1", "admin") ? "deleted" : "not exists"));
            System.out.println("user1 " + (userService.isUserExists("user1") ? "Exists" : "Not exists") + "user login validated " + userService.validateUserLogin("user1", "admin"));
            return null;
        });
    }
}
