package com.example.san.myapplication.Module;

/**
 * Created by vaps on 8/18/2017.
 */

public class Attendance
{
    private String month;
    private int classHeld;
    private int attended;
    private int percentage;

    public Attendance() {
    }

    public Attendance(String month, int classHeld, int attended, int percentage) {
        this.month = month;
        this.classHeld = classHeld;
        this.attended = attended;
        this.percentage = percentage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getClassHeld() {
        return classHeld;
    }

    public void setClassHeld(int classHeld) {
        this.classHeld = classHeld;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
