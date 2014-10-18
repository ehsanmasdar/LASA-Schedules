package com.asdar.lasaschedules;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by Ehsan on 4/21/2014.
 */
public class ScheduleFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void onResume() {
        super.onResume();
        int schedule = getArguments().getInt("request");
        ArrayList<ScheduleElement> s;
        switch (schedule){
            case 0:
                s = StaticSchedules.normalDisplay();
                break;
            case 1:
                s = StaticSchedules.forumDisplay();
                break;
            case 2:
                s = StaticSchedules.latestartDisplay();
                break;
            case 3:
                s = StaticSchedules.peprallyDisplay();
                break;
            default:
                s = StaticSchedules.normalDisplay();
                break;
        }
        ScheduleAdapter a = new ScheduleAdapter(getActivity(),R.layout.schedule_element_row,s);
        setListAdapter(a);
    }
}
