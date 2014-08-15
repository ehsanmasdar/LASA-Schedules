package com.asdar.lasaschedules;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ehsan on 4/2/14.
 */
public class HomeFragment extends Fragment {
   public static Schedule s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void setTextViews(final ArrayList<String> temp1, final ArrayList<String> temp2) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null) {
                        final TextView current = (TextView) getView().findViewById(R.id.current);
                        final TextView startime = (TextView) getView().findViewById(R.id.startime);
                        final TextView remain = (TextView) getView().findViewById(R.id.remain);
                        final TextView endtime = (TextView) getView().findViewById(R.id.endtime);
                        final TextView next = (TextView) getView().findViewById(R.id.next);
                        final TextView minuteText = (TextView) getView().findViewById(R.id.minuteText);
                        //Plurality
                        if (temp2.get(0).equals("1")) {
                            minuteText.setText("minute remaining");
                        } else {
                            minuteText.setText("minutes remaining");
                        }
                        current.setText(temp1.get(0));
                        startime.setText(timeConvertion(temp1.get(1)));
                        remain.setText(temp2.get(0));
                        endtime.setText(timeConvertion(temp2.get(1)));
                        next.setText(temp2.get(2));
                    }
                }
            });
        }
    }

    private String timeConvertion(String s) {
        String output;
        int in = Integer.parseInt(s);
        int hour = (in / 100) % 12;
        if (hour == 0){
            hour = 12;
        }
        int min = in % 100;
        String minoutput;
        if (min < 10) {
            minoutput = "0" + min;
        } else {
            minoutput = "" + min;
        }
        boolean pm = false;
        if (in / 100 > 11) {
            pm = true;
        }
        if (pm) {
            output = hour + ":" + minoutput + " " + "pm";
        } else {
            output = hour + ":" + minoutput + " " + "am";
        }
        return output;
    }
    public static void setSchedule (Context context){
        Schedule out;
        final Calendar c = Calendar.getInstance();
        String parsedString = "";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        parsedString = sp.getString("jsonschedule", null);
        Gson gson = new Gson();
        Schedule json = null;
        Boolean noschool =  null;
        String specialDay = null;
        if (parsedString != null){
            try{
                json = gson.fromJson(parsedString, Schedule.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try{
                noschool = gson.fromJson(parsedString,Boolean.class);
            }
            catch (Exception e){
                e.printStackTrace();

            }
            try{
                specialDay = gson.fromJson(parsedString,String.class);
            }
            catch (Exception e){
                e.printStackTrace();

            }
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            //Forum
            out = StaticSchedules.forum();
        } else {
            //Default Schedule
            out = StaticSchedules.normal();
        }
        if (json != null && json.getEvents() != null && json.getTimes() != null && json.getEvents().size() > 0 && json.getTimes().size() > 0){
            out = json;
        }
        if (specialDay != null){
            if (specialDay.equals("latestart")){
                out = StaticSchedules.latestart();
            }
            if (specialDay.equals("peprally")){
                out = StaticSchedules.peprally();
            }
            if (specialDay.equals("normal")){
                out = StaticSchedules.normal();
            }
        }
        if (noschool != null && noschool){
            out = null;
        }
        s = out;
    }
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Intent service = new Intent(getActivity(), NotificationService.class);
            if (s.getBoolean("notification", true)) {
                getActivity().stopService(service);
                getActivity().startService(service);
            } else {
                getActivity().stopService(service);
            }
        }
        setSchedule(getActivity());
        ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
        t.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                    /* temp1.get(0) = Current Class name
                       temp1.get(1) = Current Class start time
                       temp2.get(0) = Time left in current class
                       temp2.get(1) = End time of current class
                       temp2.get(2) = Next Class start time
                     */
                final Calendar c = Calendar.getInstance();
                if (s == null){
                    isClassOn(false);
                }
                else if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    isClassOn(false);
                } else {
                    ArrayList<String> temp1 = s.getCurrent();
                    ArrayList<String> temp2 = s.getTimeTillNext();
                    if (temp1.size() == 0 || temp2.size() == 0) {
                        isClassOn(false);
                    } else {
                        isClassOn(true);
                        setTextViews(temp1, temp2);
                    }
                }

            }
        }, 0,1, TimeUnit.SECONDS);
    }



    public void isClassOn(final boolean b) {
        if (getView() != null) {
            final TextView starttimeText = (TextView) getView().findViewById(R.id.starttimeText);
            final TextView inText = (TextView) getView().findViewById(R.id.inText);
            final TextView endtimeText = (TextView) getView().findViewById(R.id.endtimeText);
            final TextView nextText = (TextView) getView().findViewById(R.id.nextText);
            final TextView minuteText = (TextView) getView().findViewById(R.id.minuteText);
            final TextView current = (TextView) getView().findViewById(R.id.current);
            final TextView startime = (TextView) getView().findViewById(R.id.startime);
            final TextView remain = (TextView) getView().findViewById(R.id.remain);
            final TextView endtime = (TextView) getView().findViewById(R.id.endtime);
            final TextView next = (TextView) getView().findViewById(R.id.next);
            if (getActivity() != null) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String daysleft = getOutOfSchoolText();
                        if (b) {
                            starttimeText.setVisibility(View.VISIBLE);
                            inText.setVisibility(View.VISIBLE);
                            endtimeText.setVisibility(View.VISIBLE);
                            nextText.setVisibility(View.VISIBLE);
                        } else {
                            starttimeText.setVisibility(View.GONE);
                            inText.setVisibility(View.GONE);
                            endtimeText.setVisibility(View.GONE);
                            nextText.setVisibility(View.GONE);
                            minuteText.setText(daysleft);
                            current.setText("");
                            startime.setText("");
                            remain.setText("");
                            endtime.setText("");
                            next.setText("");
                        }
                    }
                });
            }
        }
    }

    private String getOutOfSchoolText() {
        Calendar c = Calendar.getInstance();
        int daysleft = (c.get(Calendar.DAY_OF_YEAR)-156);
        if (daysleft < 0){
            if (daysleft == -1){
                return Math.abs(daysleft) + " day left till school is out!";
            }
            else{
                return Math.abs(daysleft) + " days left till school is out!";
            }
        }
        else {
            return "School is out!";
        }
    }
}
