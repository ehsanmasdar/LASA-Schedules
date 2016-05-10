package com.asdar.lasaschedules.views;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Schedule;
import com.asdar.lasaschedules.util.StaticSchedules;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class ScheduleFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        int schedule = getArguments().getInt("request");
        Schedule s;
        switch (schedule){
            case 0:
                s = StaticSchedules.normal();
                break;
            case 1:
                s = StaticSchedules.forum();
                break;
            case 2:
                s = StaticSchedules.latestart();
                break;
            case 3:
                s = StaticSchedules.peprally();
                break;
            default:
                s = StaticSchedules.normal();
                break;
        }
        ScheduleAdapter a = new ScheduleAdapter(getActivity(), R.layout.schedule_element_row,s.getEvents());
        setListAdapter(a);
    }
}
