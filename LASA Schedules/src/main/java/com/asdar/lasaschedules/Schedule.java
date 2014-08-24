package com.asdar.lasaschedules;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ehsan on 4/17/2014.
 */
public class Schedule {
    private ArrayList<Integer> times;
    private ArrayList<String> events;
    public Schedule (ArrayList<Integer> t, ArrayList<String> e ){
        times = t;
        events = e;
    }
    public ArrayList<String> getCurrent (){
        //Output in the form minutes current, and start time
        ArrayList<String> result = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        int currenttime = (c.get(Calendar.HOUR_OF_DAY))*100 +  c.get(Calendar.MINUTE);
        int i = 0;
        while(currenttime >= times.get(i)&& currenttime < times.get(times.size()-1)){
            i++;
        }
        if (i == 0){
            return result;
        }
        if (currenttime < times.get(times.size()-1)) {
            result.add(events.get(i - 1));
            result.add(times.get(i - 1).toString());
        }
        return result;
    }
    public ArrayList<String> getTimeTillNext (){
        //Output in the form minutes remaining, ends at, up next
        ArrayList<String> result = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        int currenttime = (c.get(Calendar.HOUR_OF_DAY))*100 +  c.get(Calendar.MINUTE);
        long currenttimeinmil = (c.get(Calendar.HOUR_OF_DAY)*60 + c.get(Calendar.MINUTE))*60*1000;
        int i = 0;
        while(currenttime >= times.get(i) && currenttime < times.get(times.size()-1)){
            i++;
        }
        if (currenttime < times.get(times.size()-1)){
            Long diff = parseTime(times.get(i))- currenttimeinmil;
            //Convert to seconds
            diff = diff/1000;
            //Convert to minutes
            diff = diff/60;
            result.add(diff.toString());
            if (events.get(i).equals("Passing Period") ){
                result.add(times.get(i).toString());
                result.add(events.get(i+1));
            }
            else{
                result.add(times.get(i).toString());
                result.add(events.get(i));
            }
        }
        else{
            //School already over
        }
        return result;
    }

    private long parseTime(Integer integer) {
        int hours = integer/100;
        int minutes = integer%100;
        minutes = minutes + hours*60;
        return minutes*60*1000;
    }

    public ArrayList<Integer> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Integer> times) {
        this.times = times;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }
    public ArrayList<ScheduleElement> toStaticScheduleFormat(){
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        for (int i = 0; i< events.size();i++){
            if(i != events.size()-1)
                s.add(new ScheduleElement(times.get(i),times.get(i+1), events.get(i)));
        }
        for (int j = 0; j < s.size(); j++){
            if (s.get(j).getEvent().equals("Passing Period")){
                s.remove(j);
            }
        }
        return s;
    }
}
