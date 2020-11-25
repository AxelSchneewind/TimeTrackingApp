package com.schneewind.timetracking;

import android.app.Application;

import com.schneewind.timetracking.timetracking.TimeTrackingData;

public class TimeTrackingApp extends Application {
    public TimeTrackingData timeTrackingData = new TimeTrackingData();


    public Thread TimerTick = new Thread(){
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                onTimerTick();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        TimerTick.start();
    }

    void onTimerTick(){
        timeTrackingData.addTimeToAllActiveTrackers(1);
    }
}
