package com.example.android.sunshine.app;

import android.support.wearable.watchface.CanvasWatchFaceService;

public class SunshineWatchFaceService extends CanvasWatchFaceService {
    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {

    }
}
