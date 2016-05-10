package com.asdar.lasaschedules.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationServiceReciever extends BroadcastReceiver {
    public void onReceive(Context c, Intent intent) {
        Intent service = new Intent(c, NotificationService.class);
        c.startService(service);
    }

}
