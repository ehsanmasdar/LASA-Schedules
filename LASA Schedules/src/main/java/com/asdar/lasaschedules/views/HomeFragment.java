package com.asdar.lasaschedules.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asdar.lasaschedules.service.NotificationService;
import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Event;
import com.asdar.lasaschedules.util.Resources;
import com.asdar.lasaschedules.util.StaticResources;
import com.asdar.lasaschedules.TodayActivity;
import com.asdar.lasaschedules.util.Schedule;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
            //ActionItemTarget target = new ActionItemTarget(getActivity(),R.id.action_fullcal);
            //ShowcaseView.insertShowcaseView(target, getActivity(), R.string.showcase_title, R.string.showcase_details);
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("firstlaunch", false);
            e.commit();
        }

    }

    public void setTextViews(final Event e) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null) {
                        final RobotoTextView current = (RobotoTextView) getView().findViewById(R.id.current);
                        final RobotoTextView startime = (RobotoTextView) getView().findViewById(R.id.startime);
                        final RobotoTextView remain = (RobotoTextView) getView().findViewById(R.id.remain);
                        final RobotoTextView endtime = (RobotoTextView) getView().findViewById(R.id.endtime);
                        final RobotoTextView next = (RobotoTextView) getView().findViewById(R.id.next);
                        final RobotoTextView minuteText = (RobotoTextView) getView().findViewById(R.id.minuteText);
                        //remain.setTypeface(null, StaticResources.ROBOTO_LIGHT);
                        DateTime now = new DateTime();
                        DateTimeFormatter out = DateTimeFormat.forPattern("hh:mm a");

                        //Plurality
                        if (s.getTimeTillNext() > 1) {
                            minuteText.setText("minute remaining");
                        } else {
                            minuteText.setText("minutes remaining");
                        }

                        current.setText(e.name);
                        startime.setText(out.print(e.starttime));
                        remain.setText(s.getTimeTillNext() + "");
                        endtime.setText(out.print(e.endtime));
                        next.setText(s.getNext().name);
                    }
                }
            });
        }
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
        ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
        t.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                s = Resources.getSchedule(getActivity());
                DateTime now = new DateTime();
                if (s != null && (now.dayOfWeek().get() < 5)) {
                    Event e = s.getCurrent();
                    if (e != null){
                        isClassOn(true);
                        setTextViews(e);
                    }
                } else{
                    isClassOn(false);
                }

            }
        }, 0,1, TimeUnit.SECONDS);
    }



    public void isClassOn(final boolean b) {
        if (getView() != null) {
            final RobotoTextView starttimeText = (RobotoTextView) getView().findViewById(R.id.starttimeText);
            final RobotoTextView inText = (RobotoTextView) getView().findViewById(R.id.inText);
            final RobotoTextView endtimeText = (RobotoTextView) getView().findViewById(R.id.endtimeText);
            final RobotoTextView nextText = (RobotoTextView) getView().findViewById(R.id.nextText);
            final RobotoTextView minuteText = (RobotoTextView) getView().findViewById(R.id.minuteText);
            final RobotoTextView current = (RobotoTextView) getView().findViewById(R.id.current);
            final RobotoTextView startime = (RobotoTextView) getView().findViewById(R.id.startime);
            final RobotoTextView remain = (RobotoTextView) getView().findViewById(R.id.remain);
            final RobotoTextView endtime = (RobotoTextView) getView().findViewById(R.id.endtime);
            final RobotoTextView next = (RobotoTextView) getView().findViewById(R.id.next);
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
