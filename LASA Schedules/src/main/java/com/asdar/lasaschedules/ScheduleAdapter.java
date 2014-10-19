package com.asdar.lasaschedules;

/**
 * Created by Ehsan on 8/23/13.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asdar.lasaschedules.ScheduleElement;

import java.awt.font.TextAttribute;
import java.util.ArrayList;



/**
 * Created by Ehsan on 8/14/13.
 */
public class ScheduleAdapter extends ArrayAdapter<ScheduleElement> {

    private ArrayList<ScheduleElement> items;
    private LayoutInflater vi;
    private int viewResourceID;
    public ScheduleAdapter(Context context, int textViewResourceId, ArrayList<ScheduleElement> items) {
        super(context, textViewResourceId, items);
        vi = LayoutInflater.from(context);
        viewResourceID = textViewResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ScheduleElement g = items.get(position);
        if (v == null){

            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            v = inflater.inflate(viewResourceID, parent, false);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView range = (TextView) v.findViewById(R.id.range);
            TextView duration = (TextView) v.findViewById(R.id.duration);
            title.setText(g.getEvent());
            range.setText(generateRange(position));
            duration.setText(generateDuration(position));
        }
       // v = g.addView(getContext(), position);
        return v;
    }

    private String generateDuration(int position) {
        String out = "";
        out = (Math.abs(parseTime(items.get(position).getEnd()) - parseTime(items.get(position).getStart())))/60000 + "";
        out += " minutes";
        return out;
    }

    private String generateRange(int position) {
        String out = "";
        out = timeConvertion(items.get(position).getStart() + "") + " - " + timeConvertion(items.get(position).getEnd() + "");
        return out;
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
        output = hour + ":" + minoutput;
        return output;
    }
    private long parseTime(Integer integer) {
        int hours = integer/100;
        int minutes = integer%100;
        minutes = minutes + hours*60;
        return minutes*60*1000;
    }
}
