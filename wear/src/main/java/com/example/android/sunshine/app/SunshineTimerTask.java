package com.example.android.sunshine.app;

import java.util.TimerTask;

public class SunshineTimerTask extends TimerTask {
    private TimerCallback timerCallback;

    public SunshineTimerTask(TimerCallback timerCallback) {
        this.timerCallback = timerCallback;
    }

    @Override
    public void run() {
        timerCallback.onTimeUpdate();
    }
}
