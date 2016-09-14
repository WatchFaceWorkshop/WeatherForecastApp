package com.example.android.sunshine.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class SunshineTimer {
    private static final long UPDATE_INTERVAL_PERIOD = 500l;
    private static final long DELAY_TIMER_START = 0l;
    public static final String PATTERN_TIME = "HH : mm : ss";
    private Timer timer;
    private TimerCallback timerCallback;

    public SunshineTimer(TimerCallback timerCallback) {
        this.timer = new Timer();
        this.timerCallback = timerCallback;
    }

    public void start() {
        timer.scheduleAtFixedRate(new SunshineTimerTask(timerCallback), DELAY_TIMER_START, UPDATE_INTERVAL_PERIOD);
    }

    public void reset() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
    }

    public String formattedTime() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        SimpleDateFormat formattedTimeFormat = new SimpleDateFormat(PATTERN_TIME);
        String formattedTime = formattedTimeFormat.format(date);
        return formattedTime;
    }
}
