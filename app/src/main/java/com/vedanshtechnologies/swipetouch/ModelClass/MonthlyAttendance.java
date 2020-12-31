package com.vedanshtechnologies.swipetouch.ModelClass;

import java.util.ArrayList;

public class MonthlyAttendance {

    public String status;
    public String message;
    public ArrayList<Month> months;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Month> getMonths() {
        return months;
    }

    public void setMonths(ArrayList<Month> months) {
        this.months = months;
    }


}
