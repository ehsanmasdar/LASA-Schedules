package com.asdar.lasaschedules.util;

import org.joda.time.LocalTime;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ehsan on 4/21/2014.
 */
public class StaticSchedules {

    public static Schedule normal() {
        ArrayList<Event> events = new ArrayList<>();

        events.add(new Event(new LocalTime(8,15),new LocalTime(9,50),"Period 0A/0B"));
        events.add(new Event(new LocalTime(9,55), new LocalTime(11,25),"Period 1/5" ));
        events.add(new Event(new LocalTime(11,35), new LocalTime(13,05),"Period 2/6" ));
        events.add(new Event(new LocalTime(13,05), new LocalTime(14,05),"Lunch" ));
        events.add(new Event(new LocalTime(14,10), new LocalTime(15,40),"Period 3/7" ));

        return new Schedule(events);
    }

//    public static Schedule forum() {
//        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
//        s.add(new ScheduleElement(815,945, "Period 0A"));
//        s.add(new ScheduleElement(950,1120,"Period 1"));
//        s.add(new ScheduleElement(1125,1155,"Forum"));
//        s.add(new ScheduleElement(1200,1330, "Period 2"));
//        s.add(new ScheduleElement(1330,1405, "Lunch"));
//        s.add(new ScheduleElement(1410,1540, "Period 3"));
//        return s;
//    }
//
//    public static Schedule latestart() {
//        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
//        s.add(new ScheduleElement(1000,1115, "Period 5"));
//        s.add(new ScheduleElement(1120,1230, "Period 0B"));
//        s.add(new ScheduleElement(1230,1305, "Lunch"));
//        s.add(new ScheduleElement(1310,1420, "Period 6"));
//        s.add(new ScheduleElement(1425,1540, "Period 7"));
//        return s;
//    }
//
//    public static Schedule peprally() {
//        ArrayList<ScheduleElement> s = new ArrayList<ScheduleElement>();
//        s.add(new ScheduleElement(815,945, "Period 0A/0B"));
//        s.add(new ScheduleElement(950,1125, "Period 1/5"));
//        s.add(new ScheduleElement(1130,1300,"Period 2/6"));
//        s.add(new ScheduleElement(1300,1350, "Lunch"));
//        s.add(new ScheduleElement(1355,1520, "Period 3/7"));
//        s.add(new ScheduleElement(1520,1600, "Pep Rally"));
//        return s;
//    }
}