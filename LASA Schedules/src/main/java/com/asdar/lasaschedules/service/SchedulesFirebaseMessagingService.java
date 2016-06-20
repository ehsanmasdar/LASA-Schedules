package com.asdar.lasaschedules.service;

import com.asdar.lasaschedules.util.Resources;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class SchedulesFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Resources.updateSchedules(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
