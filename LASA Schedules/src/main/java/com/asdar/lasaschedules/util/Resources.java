package com.asdar.lasaschedules.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class Resources {
    public static String SYNC_URL = "https://ehsandev.com/school2.json";

    public static void updateSchedules(Context c) throws IOException {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        URL url = new URL(Resources.SYNC_URL);
        URLConnection conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.connect();

        InputStream is = httpConn.getInputStream();
        String res = convertinputStreamToString(is);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("schedules", res);
        editor.apply();
    }

    public static ArrayList<Schedule> getAllSchedules(Context context) {
        ArrayList<Schedule> out = new ArrayList<>();

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            JSONObject schedules = new JSONObject(s.getString("schedules", null));
            Iterator<?> keys = schedules.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONArray scheduleElements = schedules.getJSONObject(key).getJSONArray("schedule");
                ArrayList<Event> events = new ArrayList<>();
                for (int i = 0; i < scheduleElements.length(); i++) {
                    JSONObject element = scheduleElements.getJSONObject(i);
                    events.add(new Event(LocalTime.parse(element.getString("start")), LocalTime.parse(element.getString("end")), element.getString("name")));
                }
                out.add(new Schedule(events, key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static Schedule getSchedule(Context c) {
        return getAllSchedules(c).get(0);
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
}
