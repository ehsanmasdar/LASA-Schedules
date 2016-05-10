package com.asdar.lasaschedules.util;

import org.joda.time.LocalTime;

import java.util.ArrayList;

public class StaticSchedules {

    public static Schedule normal() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(new LocalTime(8, 15), new LocalTime(9, 50), "Period 0A/0B"));
        events.add(new Event(new LocalTime(9, 50), new LocalTime(9, 55), "Passing Period"));
        events.add(new Event(new LocalTime(9, 55), new LocalTime(11, 25), "Period 1/5"));
        events.add(new Event(new LocalTime(11, 25), new LocalTime(11, 35), "Passing Period"));
        events.add(new Event(new LocalTime(11, 35), new LocalTime(13, 5), "Period 2/6"));
        events.add(new Event(new LocalTime(13, 5), new LocalTime(14, 5), "Lunch"));
        events.add(new Event(new LocalTime(14, 5), new LocalTime(14, 10), "Passing Period"));
        events.add(new Event(new LocalTime(14, 10), new LocalTime(15, 40), "Period 3/7"));

        return new Schedule(events);
    }

    public static Schedule forum() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(new LocalTime(8, 15), new LocalTime(9, 45), "Period 0A/0B"));
        events.add(new Event(new LocalTime(9, 50), new LocalTime(11, 20), "Period 1/5"));
        events.add(new Event(new LocalTime(11, 25), new LocalTime(11, 55), "Forum"));
        events.add(new Event(new LocalTime(12, 0), new LocalTime(13, 30), "Period 2/6"));
        events.add(new Event(new LocalTime(13, 30), new LocalTime(14, 5), "Lunch"));
        events.add(new Event(new LocalTime(14, 10), new LocalTime(15, 40), "Period 3/7"));
        return new Schedule(events);
    }

    public static Schedule latestart() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(new LocalTime(10, 0), new LocalTime(11, 15), "Period 5"));
        events.add(new Event(new LocalTime(11, 20), new LocalTime(12, 30), "Period 0B"));
        events.add(new Event(new LocalTime(13, 5), new LocalTime(13, 30), "Lunch"));
        events.add(new Event(new LocalTime(13, 10), new LocalTime(14, 20), "Period 6"));
        events.add(new Event(new LocalTime(14, 25), new LocalTime(15, 40), "Period 3/7"));

        return new Schedule(events);
    }

    public static Schedule peprally() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(new LocalTime(8, 15), new LocalTime(9, 45), "Period 0A/0B"));
        events.add(new Event(new LocalTime(9, 50), new LocalTime(11, 25), "Period 1/5"));
        events.add(new Event(new LocalTime(11, 30), new LocalTime(13, 0), "Period 2/6"));
        events.add(new Event(new LocalTime(13, 0), new LocalTime(13, 50), "Lunch"));
        events.add(new Event(new LocalTime(13, 55), new LocalTime(15, 20), "Period 3/7"));
        events.add(new Event(new LocalTime(15, 20), new LocalTime(16, 0), "Pep Rally"));
        return new Schedule(events);
    }
}