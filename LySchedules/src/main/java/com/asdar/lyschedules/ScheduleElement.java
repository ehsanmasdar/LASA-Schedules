package com.asdar.lyschedules;

/**
 * Created by Ehsan on 4/21/2014.
 */
public class ScheduleElement {
    private String event;
    private int start;
    private int end;
    public ScheduleElement( int s, int end, String e){
        event = e;
        start = s;
        this.end = end;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int time) {
        this.start = time;
    }
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
