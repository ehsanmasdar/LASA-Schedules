package com.asdar.lasaschedules.service;

import android.util.Log;

import com.asdar.lasaschedules.util.Resources;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class SchedulesFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.d("firebase","Got firebase message");
            Resources.updateSchedules(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
