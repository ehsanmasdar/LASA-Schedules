package com.asdar.lyschedules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Calendar;

/**
 * Created by Ehsan on 8/28/2014.
 */
public class Resources {
    public static Schedule setSchedule (Context context){
        Schedule out;
        final Calendar c = Calendar.getInstance();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        try{
            Schedule monjson = gson.fromJson(sp.getString("mon",null), Schedule.class);
            Schedule tuejson = gson.fromJson(sp.getString("tue",null), Schedule.class);
            Schedule wedjson = gson.fromJson(sp.getString("wed",null), Schedule.class);
            Schedule thujson = gson.fromJson(sp.getString("thu",null), Schedule.class);
            Schedule frijson = gson.fromJson(sp.getString("fri",null), Schedule.class);
            if (monjson != null && monjson.getEvents() != null && monjson.getTimes() != null && monjson.getEvents().size() > 0 && monjson.getTimes().size() > 0 && c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
                out = monjson;
            }
            else if (tuejson != null && tuejson.getEvents() != null && tuejson.getTimes() != null && tuejson.getEvents().size() > 0 && tuejson.getTimes().size() > 0 && c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
                out = tuejson;
            }
            else if (wedjson != null && wedjson.getEvents() != null && wedjson.getTimes() != null && wedjson.getEvents().size() > 0 && wedjson.getTimes().size() > 0 && c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
                out = wedjson;
            }
            else  if (thujson != null && thujson.getEvents() != null && thujson.getTimes() != null && thujson.getEvents().size() > 0 && thujson.getTimes().size() > 0 && c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                out = thujson;
            }
            else if (frijson != null && frijson.getEvents() != null && frijson.getTimes() != null && frijson.getEvents().size() > 0 && frijson.getTimes().size() > 0 && c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                out = frijson;
            }
            else {
                out = null;
            }
            return out;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
