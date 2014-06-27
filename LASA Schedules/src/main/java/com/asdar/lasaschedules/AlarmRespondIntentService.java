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
public class AlarmRespondIntentService extends IntentService{

    public AlarmRespondIntentService() {
        super("AlarmRespondIntentService");
    }

    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
    protected void onHandleIntent(Intent intent) {
        Log.d("com.asdar.lasaschedules", "Alarm reciever called, pulling new schedule");
        String parsedString = null;
        try {
            URL url = new URL("http://raw.ehsandev.com/school.json");
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            parsedString = convertinputStreamToString(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (parsedString != null){
            SharedPreferences.Editor e = sp.edit();
            e.putString("jsonschedule", parsedString);
            e.commit();
            Log.d("com.asdar.lasaschedules", "Got Schedule: " + parsedString);
        }
        Intent service = new Intent(getApplicationContext(), NotificationService.class);
        if (sp.getBoolean("notification", true)) {
            getApplicationContext().stopService(service);
            getApplicationContext().startService(service);
        } else {
            getApplicationContext().stopService(service);
        }
        try {
            HomeFragment.setSchedule(getApplicationContext());
        }
        catch (Exception e){

        }
    }
}
