package com.asdar.lasaschedules;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

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
        Gson gson = new Gson();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Schedule json = gson.fromJson(parsedString, Schedule.class);

        switch (schedule){
            case 0:
                s = removeEmpty(gson.fromJson(sp.getString("mon", ""), Schedule.class).toStaticScheduleFormat());
                break;
            case 1:
                s = removeEmpty(gson.fromJson(sp.getString("tue", ""), Schedule.class).toStaticScheduleFormat());
                break;
            case 2:
                s = removeEmpty(gson.fromJson(sp.getString("wed", ""), Schedule.class).toStaticScheduleFormat());
                break;
            case 3:
                s = removeEmpty(gson.fromJson(sp.getString("thu", ""), Schedule.class).toStaticScheduleFormat());
                break;
            case 4:
                s = removeEmpty(gson.fromJson(sp.getString("fri", ""), Schedule.class).toStaticScheduleFormat());
                break;
            default:
                s = StaticSchedules.normalDisplay();
                break;
        }
        ScheduleAdapter a = new ScheduleAdapter(getActivity(),R.layout.schedule_element_row,s);
        setListAdapter(a);
    }

    public ArrayList<ScheduleElement> removeEmpty(ArrayList<ScheduleElement> arr) {
        ArrayList<ScheduleElement> output = new ArrayList<ScheduleElement>();
        for (int i = 0; i < arr.size(); i++){
            if (!(arr.get(i).getStart() >= arr.get(i).getEnd()) ){
                output.add(arr.get(i));
            }
        }
        return output;
    }

}
