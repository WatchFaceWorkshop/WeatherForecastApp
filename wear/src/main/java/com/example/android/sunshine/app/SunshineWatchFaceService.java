package com.example.android.sunshine.app;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;

public class SunshineWatchFaceService extends CanvasWatchFaceService {
    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine implements TimerCallback, SunshineSyncCallback {
        private SunshineTimer sunshineTimer;
        private SunshineSyncService sunshineSyncService;
        private Double temperatureHigh = Double.NaN, temperatureLow = Double.NaN;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            sunshineTimer = new SunshineTimer(this);
            sunshineSyncService = new SunshineSyncService(getApplicationContext(), this);
            sunshineSyncService.requestWeatherForecast();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            toggleTimerState(visible);
            invalidate();
        }

        private void toggleTimerState(boolean visible) {
            if (visible) {
                sunshineTimer.start();
                sunshineSyncService.requestWeatherForecast();
            } else
                sunshineTimer.reset();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            SunshineWatchFaceCanvas sunshineWatchFaceCanvas = new SunshineWatchFaceCanvas(canvas, bounds, getResources(), isVisible(), isInAmbientMode());
            sunshineWatchFaceCanvas.draw(sunshineTimer.formattedTime(), temperatureHigh, temperatureLow);
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            toggleTimerState(isVisible() && !inAmbientMode);
            invalidate();
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onTimeUpdate() {
            invalidate();
        }

        @Override
        public void onTemperatureFetched(Double high, Double low) {
            this.temperatureHigh = high;
            this.temperatureLow = low;
            invalidate();
        }
    }
}
