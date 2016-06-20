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
import android.widget.TextView;
import android.widget.Toast;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.TodayActivity;
import com.asdar.lasaschedules.service.NotificationService;
import com.asdar.lasaschedules.util.Event;
import com.asdar.lasaschedules.util.Resources;
import com.asdar.lasaschedules.util.Schedule;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {
    private static Schedule s;

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
    }

    public void setTextViews() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null) {
                        Event e = s.getCurrent();
                        final TextView current = (TextView) getView().findViewById(R.id.current);
                        final TextView startime = (TextView) getView().findViewById(R.id.startime);
                        final TextView remain = (TextView) getView().findViewById(R.id.remain);
                        final TextView endtime = (TextView) getView().findViewById(R.id.endtime);
                        final TextView next = (TextView) getView().findViewById(R.id.next);
                        final TextView minuteText = (TextView) getView().findViewById(R.id.minuteText);

                        DateTimeFormatter out = DateTimeFormat.forPattern("hh:mm a");

                        //Plurality
                        if (s.getTimeTillNext() > 1) {
                            minuteText.setText(R.string.minute_plural);
                        } else {
                            minuteText.setText(R.string.minute_singular);
                        }

                        current.setText(e.name);
                        startime.setText(out.print(e.starttime));
                        remain.setText(s.getTimeTillNext().toString());
                        endtime.setText(out.print(e.endtime));
                        if (s.getNext() == null) {
                            next.setText(R.string.endOfSchool);
                        } else {
                            next.setText(s.getNext().name);
                        }
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
                    if (s.getCurrent() != null) {
                        isClassOn(true);
                        setTextViews();
                    } else {
                        isClassOn(false);
                    }
                } else {
                    isClassOn(false);
                }

            }
        }, 0, 1, TimeUnit.SECONDS);
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
                if (s != null) {
                    Intent intent = new Intent(getActivity(), TodayActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "No Active Schedule", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
