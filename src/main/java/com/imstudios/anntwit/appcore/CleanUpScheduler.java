package com.imstudios.anntwit.appcore;

import com.imstudios.anntwit.db.tweet.TweeterCoreUtil;

import java.util.Timer;
import java.util.TimerTask;

public class CleanUpScheduler extends TimerTask {
    @Override
    public void run() {
        TweeterCoreUtil.getInstance().cleanOldTweets();
    }

    public void setCleanUpActivity(){
        CleanUpScheduler cleanUpScheduler = new CleanUpScheduler();
        Timer time = new Timer();
        time.schedule(cleanUpScheduler, 0, 7200000);// schedules for 2 hrs.
    }
}
