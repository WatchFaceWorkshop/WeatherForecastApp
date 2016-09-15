package com.example.android.sunshine.app;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class SunshineWatchFaceCanvas {
    public static final int TEXT_SIZE_MEDIUM = 24;
    private final Canvas canvas;
    private final Rect bounds;
    private Resources resources;
    private final boolean visible;
    private final boolean inAmbientMode;

    public SunshineWatchFaceCanvas(Canvas canvas, Rect bounds, Resources resources, boolean visible, boolean inAmbientMode) {
        this.canvas = canvas;
        this.bounds = bounds;
        this.resources = resources;
        this.visible = visible;
        this.inAmbientMode = inAmbientMode;
    }

    public void draw(String formattedTime, Double temperatureHigh, Double temperatureLow) {
        clearCanvas();
        drawFormattedTime(formattedTime);
    }

    private void drawFormattedTime(String formattedTime) {
        canvas.drawText(formattedTime, xCoordinateToStartPrintingFrom(formattedTime), bounds.centerY(), defaultPaint());
    }

    private float xCoordinateToStartPrintingFrom(String formattedTime) {
        return bounds.centerX() - defaultPaint().measureText(formattedTime) / 2f;
    }

    private Paint defaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(resources.getColor(R.color.white));
        paint.setTextSize(TEXT_SIZE_MEDIUM);
        return paint;
    }

    private void clearCanvas() {
        int color;
        if (visible && inAmbientMode)
            color = resources.getColor(R.color.black);
        else
            color = resources.getColor(R.color.sunshine_blue);
        canvas.drawColor(color);
    }
}
