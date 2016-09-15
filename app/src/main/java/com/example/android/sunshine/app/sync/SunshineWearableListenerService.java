package com.example.android.sunshine.app.sync;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

public class SunshineWearableListenerService extends WearableListenerService {
    private static final CharSequence PATH_REQUEST_SYNC = "/sync/weather/";

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);
        for (DataEvent dataEvent : dataEventBuffer) {
            boolean isWearableUpdateRequest = dataEvent.getDataItem().getUri().toString().contains(PATH_REQUEST_SYNC);
            if (isWearableUpdateRequest)
                SunshineSyncAdapter.syncImmediately(getApplicationContext());
        }
    }
}
