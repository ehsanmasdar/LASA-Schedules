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
        events.add("Period 0A/0B");
        times.add(950);
        events.add("Passing Period");
        times.add(955);
        events.add("Period 1/5");
        times.add(1125);
        events.add("Passing Period");
        times.add(1135);
        events.add("Period 2/6");
        times.add(1305);
        events.add("Lunch");
        times.add(1405);
        events.add("Passing Period");
        times.add(1410);
        events.add("Period 3/7");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;

    }
    public static ArrayList<ScheduleElement> normalDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,950, "Period 0A/0B"));
        s.add(new ScheduleElement(955,1125, "Period 1/5"));
        s.add(new ScheduleElement(1135,1305,"Period 2/6"));
        s.add(new ScheduleElement(1305,1405, "Lunch"));
        s.add(new ScheduleElement(1410,1540,"Period 3/7"));
        return s;
    }
    public static Schedule forum() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(815);
        events.add("Period 0A");
        times.add(945);
        events.add("Passing Period");
        times.add(950);
        events.add("Period 1");
        times.add(1120);
        events.add("Passing Period");
        times.add(1125);
        events.add("Forum");
        times.add(1155);
        events.add("Passing Period");
        times.add(1200);
        events.add("Period 2");
        times.add(1330);
        events.add("Lunch");
        times.add(1405);
        events.add("Passing Period");
        times.add(1410);
        events.add("Period 3");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement>  forumDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,945, "Period 0A"));
        s.add(new ScheduleElement(950,1120,"Period 1"));
        s.add(new ScheduleElement(1125,1155,"Forum"));
        s.add(new ScheduleElement(1200,1330, "Period 2"));
        s.add(new ScheduleElement(1330,1405, "Lunch"));
        s.add(new ScheduleElement(1410,1540, "Period 3"));
        return s;
    }
    public static Schedule latestart() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(1000);
        events.add("Period 5");
        times.add(1115);
        events.add("Passing Period");
        times.add(1120);
        events.add("Period 0B");
        times.add(1230);
        events.add("Lunch");
        times.add(1305);
        events.add("Passing Period");
        times.add(1310);
        events.add("Period 6");
        times.add(1420);
        events.add("Passing Period");
        times.add(1425);
        events.add("Period 7");
        times.add(1540);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement>  latestartDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(1000,1115, "Period 5"));
        s.add(new ScheduleElement(1120,1230, "Period 0B"));
        s.add(new ScheduleElement(1230,1305, "Lunch"));
        s.add(new ScheduleElement(1310,1420, "Period 6"));
        s.add(new ScheduleElement(1425,1540, "Period 7"));
        return s;
    }
    public static Schedule peprally() {
        ArrayList<Integer> times = new ArrayList<Integer>();
        ArrayList<String> events = new ArrayList<String>();
        times.add(815);
        events.add("Period 0A/0B");
        times.add(945);
        events.add("Passing Period");
        times.add(950);
        events.add("Period 1/5");
        times.add(1125);
        events.add("Passing Period");
        times.add(1130);
        events.add("Period 2/6");
        times.add(1300);
        events.add("Lunch");
        times.add(1350);
        events.add("Passing Period");
        times.add(1355);
        events.add("Period 3/7");
        times.add(1520);
        events.add("Pep Rally");
        times.add(1600);
        events.add("End of School");
        Schedule s = new Schedule(times, events);
        return s;
    }
    public static ArrayList<ScheduleElement> peprallyDisplay() {
        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
        s.add(new ScheduleElement(815,945, "Period 0A/0B"));
        s.add(new ScheduleElement(950,1125, "Period 1/5"));
        s.add(new ScheduleElement(1130,1300,"Period 2/6"));
        s.add(new ScheduleElement(1300,1350, "Lunch"));
        s.add(new ScheduleElement(1355,1520, "Period 3/7"));
        s.add(new ScheduleElement(1520,1600, "Pep Rally"));
        return s;
    }
}