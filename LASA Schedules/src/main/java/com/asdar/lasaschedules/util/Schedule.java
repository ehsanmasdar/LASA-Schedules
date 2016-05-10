package com.asdar.lasaschedules.util;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Holds a day's schdule represented by events
 */
public class Schedule {
    public ArrayList<Event> getEvents() {
        return events;
    }

    private ArrayList<Event> events;

    public Schedule(ArrayList<Event> events) {
        this.events = events;
    }

    public Event getCurrent() {
        DateTime now = new DateTime();
        for (Event e : events) {
            if (e.starttime.getMillisOfDay() <= now.getMillisOfDay() && e.endtime.getMillisOfDay() > now.getMillisOfDay()) {
                return e;
            }
        }
        return null;
    }

    public Integer getTimeTillNext() {
        DateTime now = new DateTime();
        Event current = getCurrent();
        if (current != null)
            return (getCurrent().endtime.getMillisOfDay() - now.getMillisOfDay()) / 60000 + 1;
        return null;
    }

    public Event getNext() {
        DateTime now = new DateTime();
        for (int i = 0; i < events.size() - 2; i++) {
            Event e = events.get(i);
            if (e.starttime.getMillisOfDay() <= now.getMillisOfDay() && e.endtime.getMillisOfDay() > now.getMillisOfDay()) {
                return events.get(i + 1);
            }
        }
        return null;
    }
}
