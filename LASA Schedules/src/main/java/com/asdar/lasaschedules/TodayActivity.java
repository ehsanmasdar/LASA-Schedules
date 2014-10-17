package com.asdar.lasaschedules;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;


public class TodayActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_today);
        ListView l = (ListView) findViewById(R.id.todaylistview);
        Schedule s = Resources.getSchedule(getApplicationContext());
        if (s != null){
            ScheduleAdapter a = new ScheduleAdapter(this,R.layout.schedule_element_row, s.toStaticScheduleFormat());
            l.setAdapter(a);
        }
    }
}
