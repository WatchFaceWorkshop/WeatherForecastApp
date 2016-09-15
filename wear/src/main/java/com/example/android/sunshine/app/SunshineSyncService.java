package com.example.android.sunshine.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class SunshineSyncService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener {
    private static final String BLANK_KEY = "key";
    private static final int BLANK_VALUE = 1;
    public static final String PATH_REQUEST_SYNC = "/sync/weather/";
    private final GoogleApiClient googleApiClient;
    private SunshineSyncCallback sunshineSyncCallback;

    public SunshineSyncService(Context context, SunshineSyncCallback sunshineSyncCallback) {
        this.sunshineSyncCallback = sunshineSyncCallback;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
        googleApiClient.connect();
    }

    public void requestWeatherForecast() {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(PATH_REQUEST_SYNC + System.currentTimeMillis());
        putDataMapRequest.setUrgent();
        putDataMapRequest.getDataMap().putInt(BLANK_KEY, BLANK_VALUE);
        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, putDataRequest);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent dataEvent : dataEventBuffer) {
            DataItem dataItem = dataEvent.getDataItem();
            boolean isUpdateRequest = dataItem.getUri().toString().contains("/data/weather_forecast");
            if (isUpdateRequest) {
                DataMapItem weatherForecastDataItem = DataMapItem.fromDataItem(dataItem);
                DataMap weatherForecastDataMap = weatherForecastDataItem.getDataMap();
                double temperatureHigh = weatherForecastDataMap.getDouble("high");
                double temperatureLow = weatherForecastDataMap.getDouble("low");
                sunshineSyncCallback.onTemperatureFetched(temperatureHigh, temperatureLow);
            }
        }
    }
}
