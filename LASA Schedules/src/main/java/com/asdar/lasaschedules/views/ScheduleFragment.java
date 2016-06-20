package com.asdar.lasaschedules.views;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Resources;
import com.asdar.lasaschedules.util.Schedule;

public class ScheduleFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        int schedule = getArguments().getInt("request");
        Schedule s = Resources.getAllSchedules(getContext()).get(schedule);
        ScheduleAdapter a = new ScheduleAdapter(getActivity(), R.layout.schedule_element_row, s.getEvents());
        setListAdapter(a);
    }
}
