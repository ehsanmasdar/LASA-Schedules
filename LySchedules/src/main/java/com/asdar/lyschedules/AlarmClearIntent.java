package com.asdar.lyschedules;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ehsan on 4/20/2014.
 */
public class AlarmClearIntent extends IntentService{

    public AlarmClearIntent() {
        super("AlarmClearIntent");
    }

    protected void onHandleIntent(Intent intent) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       SharedPreferences.Editor e = sp.edit();
       e.putString("jsonschedule", "");
       e.commit();
    }
}
