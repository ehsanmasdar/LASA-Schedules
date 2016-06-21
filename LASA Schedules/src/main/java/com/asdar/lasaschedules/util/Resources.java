package com.asdar.lasaschedules.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
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
                JSONArray dateElements = schedules.getJSONObject(key).getJSONArray("dates");
                ArrayList<Event> events = new ArrayList<>();
                ArrayList<String> dates = new ArrayList<>();
                for (int i = 0; i < scheduleElements.length(); i++) {
                    JSONObject element = scheduleElements.getJSONObject(i);
                    events.add(new Event(LocalTime.parse(element.getString("start")), LocalTime.parse(element.getString("end")), element.getString("name")));
                }
                for(int i = 0; i < dateElements.length(); i++){
                    dates.add(dateElements.getString(i));
                }
                out.add(new Schedule(events, key,dates));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static Schedule getSchedule(Context c) {
        ArrayList<Schedule> schedules = getAllSchedules(c);
        for (Schedule s :  schedules){
            for (String date : s.getDates()) {
                if (ISODateTimeFormat.date().print(DateTime.now()).equals(date)) {
                    return s;
                }
            }
        }
        for (Schedule s :  schedules){
            for (String date : s.getDates()) {
                if (DateTime.now().dayOfWeek().toString().toLowerCase().equals(date)) {
                    return s;
                }
            }
        }
        for (Schedule s :  schedules){
            for (String date : s.getDates()) {
                if ((DateTime.now().getDayOfWeek() <= 5 && date.equals("weekday")) ||
                        (DateTime.now().getDayOfWeek() > 5 && date.equals("weekend")) ) {
                    return s;
                }
            }
        }
        return null;
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
