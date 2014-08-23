package com.asdar.lyschedules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
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
        return "No School";
    }
}
