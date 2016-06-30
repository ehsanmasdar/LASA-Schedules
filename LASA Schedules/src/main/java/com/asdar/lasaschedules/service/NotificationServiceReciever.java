package com.asdar.lasaschedules.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NotificationServiceReciever extends BroadcastReceiver {
    public void onReceive(Context c, Intent intent) {
        Intent service = new Intent(c, NotificationService.class);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(c);
        if (s.getBoolean("notification", true))
            c.startService(service);
    }
}
