package com.asdar.lasaschedules;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
