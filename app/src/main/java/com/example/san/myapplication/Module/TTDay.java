package com.example.san.myapplication.Module;

/**
 * Created by vaps on 19-Feb-18.
 */

public class TTDay
{
    public int PeriodName;
    public String staffName;
    public String SubjectName;

    public TTDay(int periodName, String staffName, String subjectName) {
        PeriodName = periodName;
        this.staffName = staffName;
        SubjectName = subjectName;
    }

    public int getPeriodName() {
        return PeriodName;
    }

    public void setPeriodName(int periodName) {
        PeriodName = periodName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

}
