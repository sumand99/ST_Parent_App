package com.vedanshtechnologies.swipetouch.ModelClass;

import java.util.ArrayList;

public class Month {

    public String month_no;
    public String Month_name;
    public String days_count;
    public String present_count;
    public String absent_count;
    public String late_count;
    public String halfday_count;
    public String holiday_count;
    public String attendance_percent;

    public ArrayList<Attendance> attendanceList;

    public String getMonth_no() {
        return month_no;
    }

    public void setMonth_no(String month_no) {
        this.month_no = month_no;
    }

    public String getMonth_name() {
        return Month_name;
    }

    public void setMonth_name(String month_name) {
        Month_name = month_name;
    }

    public void setattendance_percent(String attendance_percent) {
      this.attendance_percent = attendance_percent;
    }

    public String getDays_count() {
        return days_count;
    }

    public String getattendance_percent() {
        return attendance_percent;
    }

    public void setDays_count(String days_count) {
        this.days_count = days_count;
    }

    public String getPresent_count() {
        return present_count;
    }

    public void setPresent_count(String present_count) {
        this.present_count = present_count;
    }

    public String getAbsent_count() {
        return absent_count;
    }

    public void setAbsent_count(String absent_count) {
        this.absent_count = absent_count;
    }

    public String getLate_count() {
        return late_count;
    }

    public void setLate_count(String late_count) {
        this.late_count = late_count;
    }

    public String getHalfday_count() {
        return halfday_count;
    }

    public void setHalfday_count(String halfday_count) {
        this.halfday_count = halfday_count;
    }

    public String getHoliday_count() {
        return holiday_count;
    }

    public void setHoliday_count(String holiday_count) {
        this.holiday_count = holiday_count;
    }

    public ArrayList<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(ArrayList<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }


}
