package com.example.san.myapplication.Module;

/**
 * Created by vaps on 20-Feb-18.
 */

public class Event
{
    public String Event_name;
    public String Start_date;
    public String End_date;

    public Event(String evnt_name, String start_date, String end_date) {
        Event_name = evnt_name;
        Start_date = start_date;
        End_date = end_date;
    }

    public String getEvnt_name() {
        return Event_name;
    }

    public void setEvnt_name(String evnt_name) {
        Event_name = evnt_name;
    }

    public String getStart_date() {
        return Start_date;
    }

    public void setStart_date(String start_date) {
        Start_date = start_date;
    }

    public String getEnd_date() {
        return End_date;
    }

    public void setEnd_date(String end_date) {
        End_date = end_date;
    }
}
