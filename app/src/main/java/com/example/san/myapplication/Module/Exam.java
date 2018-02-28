package com.example.san.myapplication.Module;

/**
 * Created by vaps on 9/8/2017.
 */

public class Exam
{
    private String subName = null;
    private int totMarks = 0;
    private int minMarks = 0;
    private int obtainMarks = 0;

    public Exam() {

    }

    public Exam(String subName, int totMarks, int minMarks, int obtainMarks) {
        this.subName = subName;
        this.totMarks = totMarks;
        this.minMarks = minMarks;
        this.obtainMarks = obtainMarks;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getTotMarks() {
        return totMarks;
    }

    public void setTotMarks(int totMarks) {
        this.totMarks = totMarks;
    }

    public int getMinMarks() {
        return minMarks;
    }

    public void setMinMarks(int minMarks) {
        this.minMarks = minMarks;
    }

    public int getObtainMarks() {
        return obtainMarks;
    }

    public void setObtainMarks(int obtainMarks) {
        this.obtainMarks = obtainMarks;
    }
}
