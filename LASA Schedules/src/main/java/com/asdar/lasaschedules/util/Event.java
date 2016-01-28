package com.asdar.lasaschedules.util;

import org.joda.time.LocalTime;

public class Event {
    public LocalTime starttime;
    public LocalTime endtime;
    public String name;

    public Event(LocalTime starttime, LocalTime endtime, String name) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.name = name;
    }
}
