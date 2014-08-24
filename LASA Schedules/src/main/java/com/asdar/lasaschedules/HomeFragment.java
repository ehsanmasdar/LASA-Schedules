package com.asdar.lasaschedules;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.espian.showcaseview.ShowcaseView;
import com.espian.showcaseview.targets.ActionItemTarget;
import com.espian.showcaseview.targets.ActionViewTarget;
import com.espian.showcaseview.targets.ViewTarget;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (sp.getBoolean("firstlaunch", true)){
            ActionItemTarget target = new ActionItemTarget(getActivity(),R.id.action_fullcal);
            ShowcaseView.insertShowcaseView(target, getActivity(), R.string.showcase_title, R.string.showcase_details);
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("firstlaunch", false);
            e.commit();
        }

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
        s = Resources.getSchedule(getActivity());
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
    public static void refresh(Context c){
        s = Resources.getSchedule(c);
    }
    private String getOutOfSchoolText() {
            return "No School";
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.today, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_fullcal:
                Schedule s = Resources.getSchedule(getActivity());
                if (s != null){
                    Intent intent = new Intent(getActivity(), TodayActivity.class);
                    startActivity(intent);
                }
                else {
                     Toast.makeText(getActivity(), "No Active Schedule", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
