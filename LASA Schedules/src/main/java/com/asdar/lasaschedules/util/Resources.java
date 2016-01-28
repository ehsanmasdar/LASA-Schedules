package com.asdar.lasaschedules.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import com.asdar.lasaschedules.util.Schedule;
import com.asdar.lasaschedules.util.StaticSchedules;

import java.util.Calendar;

/**
 * Created by Ehsan on 8/23/2014.
 */
public class Resources {
//    public static Schedule getSchedule(Context context){
//        Schedule out;
//        final Calendar c = Calendar.getInstance();
//        String parsedString = "";
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        parsedString = sp.getString("jsonschedule", null);
//        Gson gson = new Gson();
//        Schedule json = null;
//        Boolean noschool =  null;
//        String specialDay = null;
//        if (parsedString != null){
//            try{
//                json = gson.fromJson(parsedString, Schedule.class);
//            }
//            catch (Exception e){
//            }
//            try{
//                noschool = gson.fromJson(parsedString,Boolean.class);
//            }
//            catch (Exception e){
//            }
//            try{
//                specialDay = gson.fromJson(parsedString,String.class);
//            }
//            catch (Exception e){
//            }
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//            //Forum
//            out = StaticSchedules.forum();
//        } else {
//            //Default Schedule
//            out = StaticSchedules.normal();
//        }
//        if (json != null){
//            out = json;
//        }
//        if (specialDay != null){
//            if (specialDay.equals("latestart")){
//                out = StaticSchedules.latestart();
//            }
//            if (specialDay.equals("peprally")){
//                out = StaticSchedules.peprally();
//            }
//            if (specialDay.equals("normal")){
//                out = StaticSchedules.normal();
//            }
//        }
//        if (noschool != null && noschool){
//            out = null;
//        }
//        return out;
//    }
    public static Schedule getSchedule(Context context){
        return StaticSchedules.normal();
    }
}
