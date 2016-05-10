package com.asdar.lasaschedules.views;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Event;

import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Event> {

    private LayoutInflater vi;
    private int viewResourceID;
    private ArrayList<Event> items;
    public ScheduleAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
        super(context, textViewResourceId, items);
        vi = LayoutInflater.from(context);
        viewResourceID = textViewResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Event g = items.get(position);
        if (v == null){

            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            v = inflater.inflate(viewResourceID, parent, false);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView range = (TextView) v.findViewById(R.id.range);
            TextView duration = (TextView) v.findViewById(R.id.duration);
            title.setText(g.name);
            range.setText(generateRange(g));
            duration.setText(generateDuration(g));
        }
        return v;
    }

    private String generateDuration(Event e) {
        return Minutes.minutesBetween(e.starttime,e.endtime).getMinutes() + " minutes";
    }

    private String generateRange(Event e) {
        DateTimeFormatter outputFormat = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
        return e.starttime.toString(outputFormat) + " - " + e.endtime.toString(outputFormat);
    }
}