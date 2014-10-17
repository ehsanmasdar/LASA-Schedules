package com.asdar.lasaschedules;

import java.util.ArrayList;

/**
 * Created by Ehsan on 4/21/2014.
 */
public class StaticSchedules {
    public static Schedule normal() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(815);
        events.add("Period 1/5");
        times.add(950);
        events.add("Passing Period");
        times.add(955);
        events.add("Period 2/6");
        times.add(1125);
        events.add("Passing Period");
        times.add(1135);
        events.add("Period 3/7");
        times.add(1305);
        events.add("Lunch");
        times.add(1400);
        events.add("Passing Period");
        times.add(1405);
        events.add("Period 4/8");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;

    }
    public static ArrayList<ScheduleElement> normalDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,950, "Period 1/5"));
        s.add(new ScheduleElement(955,1125, "Period 2/6"));
        s.add(new ScheduleElement(1135,1305,"Period 3/7"));
        s.add(new ScheduleElement(1305,1400, "Lunch"));
        s.add(new ScheduleElement(1405,1540,"Period 4/8"));
        return s;
    }
    public static Schedule forum() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(815);
        events.add("Period 1/5");
        times.add(940);
        events.add("Passing Period");
        times.add(945);
        events.add("Advisory");
        times.add(1025);
        events.add("Passing Period");
        times.add(1030);
        events.add("Period 2/6");
        times.add(1150);
        events.add("Passing Period");
        times.add(1155);
        events.add("Period 3/7");
        times.add(1315);
        events.add("Lunch");
        times.add(1410);
        events.add("Passing Period");
        times.add(1415);
        events.add("Period 4/8");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement>  forumDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,940, "Period 1/5"));
        s.add(new ScheduleElement(945,1025,"Advisory"));
        s.add(new ScheduleElement(1030,1150,"Period 2/6"));
        s.add(new ScheduleElement(1155,1315, "Period 3/7"));
        s.add(new ScheduleElement(1315,1410, "Lunch"));
        s.add(new ScheduleElement(1415,1540, "Period 4/8"));
        return s;
    }
    public static Schedule latestart() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(1000);
        events.add("Period 5");
        times.add(1105);
        events.add("Passing Period");
        times.add(1110);
        events.add("Period 6");
        times.add(1220);
        events.add("Passing Period");
        times.add(1225);
        events.add("Period 7");
        times.add(1315);
        events.add("Lunch");
        times.add(1355);
        events.add("Passing Period");
        times.add(1400);
        events.add("Period 7");
        times.add(1420);
        events.add("Passing Period");
        times.add(1425);
        events.add("Period 8");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement>  latestartDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(1000,1105, "Period 5"));
        s.add(new ScheduleElement(1110,1220, "Period 6"));
        s.add(new ScheduleElement(1225,1315,"Period 7"));
        s.add(new ScheduleElement(1315,1355, "Lunch"));
        s.add(new ScheduleElement(1400,1420, "Period 7"));
        s.add(new ScheduleElement(1425,1540, "Period 8"));
        return s;
    }
    public static Schedule peprally() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(815);
        events.add("Period 1/5");
        times.add(940);
        events.add("Passing Period");
        times.add(945);
        events.add("Period 2/6");
        times.add(1110);
        events.add("Passing Period");
        times.add(1115);
        events.add("Period 3/7");
        times.add(1240);
        events.add("Lunch");
        times.add(1320);
        events.add("Passing Period");
        times.add(1325);
        events.add("Period 4/8");
        times.add(1455);
        events.add("Passing Period");
        times.add(1500);
        events.add("Pep Rally");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement> peprallyDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,940, "Period 1/5"));
        s.add(new ScheduleElement(945,1110, "Period 2/6"));
        s.add(new ScheduleElement(1115,1240,"Period 3/7"));
        s.add(new ScheduleElement(1240,1320, "Lunch"));
        s.add(new ScheduleElement(1325,1455, "Period 4/8"));
        s.add(new ScheduleElement(1500,1540, "Pep Rally"));
        return s;
    }
}