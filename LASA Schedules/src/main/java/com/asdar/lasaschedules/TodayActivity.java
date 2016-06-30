package com.asdar.lasaschedules;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.asdar.lasaschedules.util.Resources;
import com.asdar.lasaschedules.util.Schedule;
import com.asdar.lasaschedules.views.ScheduleAdapter;


public class TodayActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        //Define Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Configure Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        ListView l = (ListView) findViewById(R.id.todaylistview);
        Schedule s = Resources.getSchedule(getApplicationContext());
        if (s != null) {
            ScheduleAdapter a = new ScheduleAdapter(this, R.layout.schedule_element_row, s.getEvents());
            l.setAdapter(a);
        }
    }
}
