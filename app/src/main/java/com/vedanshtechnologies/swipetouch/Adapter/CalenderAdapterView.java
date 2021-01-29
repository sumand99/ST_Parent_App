package com.swipetouch.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swipetouch.Activities.MainCalenderActivity;
import com.swipetouch.Activities.customcalender;
import com.swipetouch.ModelClass.Attendance;
import com.swipetouch.ModelClass.MonthlyAttendance;
import com.vedanshtechnologies.swipetouch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalenderAdapterView extends BaseAdapter {
    private Context mContext;
    Calendar mCalender = Calendar.getInstance();
    private Context context; //context
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;

    int maxP;
    public static List dayString;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    MonthlyAttendance monthlyAttendance; //data source of the list adapter

    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    private String mVisibility;
    private View previousView;

    //public constructor
    public CalenderAdapterView(Context context, MonthlyAttendance monthlyAttendance, String mVisibility) {
        this.context = context;
        this.monthlyAttendance = monthlyAttendance;
        this.mVisibility = mVisibility;
       // CalenderAdapterView.dayString = monthlyAttendance;
    }

    public CalenderAdapterView(customcalender customcalender, GregorianCalendar monthcal) {
        CalendarAdapter.dayString = new ArrayList();
        Locale.setDefault(Locale.US);
        month = monthcal;
        System.out.println(":::::::::::::::::::::MONTH CALENDAR:::::::::::::::"
                + monthcal);
        selectedDate = (GregorianCalendar) monthcal.clone();
        mContext = customcalender;
        month.set(GregorianCalendar.DAY_OF_WEEK, 1);

        String sunday = String.valueOf(GregorianCalendar.SUNDAY);
        Log.e("","my_first_day"+sunday);

       // this.items = new ArrayList();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        System.out
                .println(":::::::::::::::::CURRENT DATE STRING::::::::::::::;"
                        + curentDateString);
        //refreshDays();
    }


    @Override
    public int getCount() {
        //returns total of items in the list
        return monthlyAttendance.getMonths().size();
    }

    @Override
    public Object getItem(int position) {
        //returns list item at the specified position
        return itemvalue.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        previousView = view;
        //view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.calendar_item, parent, false);
        }

        // get current item to be displayed



        // get the TextView for item name and item description
        Log.e("","month_size"+monthlyAttendance.getMonths().size());
        Log.e("","attendance_size"+monthlyAttendance.getMonths().get(position).getAttendanceList().size());

        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.date);
        RelativeLayout mImageBaground = (RelativeLayout)
                convertView.findViewById(R.id.ll_items);


        textViewItemName.setText(monthlyAttendance.getMonths().get(position).getAttendanceList().get(position).getDay());



        /*for(int i=position;i<monthlyAttendance.getMonths().get(position).getAttendanceList().size();i++){

            textViewItemName.setText(monthlyAttendance.getMonths().get(0).getAttendanceList().get(i).getDay());

           *//* if (mVisibility.equals("0")) {
                if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("PRESENT")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundful);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                } else if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("LATE")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundlate);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                } else if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("HOLIDAY")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundholiday);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                } else if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("ABSENT")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundabsent);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                } else if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("HALFDAY")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundabhalf);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                } else if ((monthlyAttendance.months.get(position).getAttendanceList().get(position).getPastatus()).equals("LEAVE")) {
                    mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundleave);
                    textViewItemName.setTextColor(Color.parseColor("#FFFFFF"));
                }

            }*//*

        }*/



        // returns the view for the current row
        return convertView;
    }

    public void setItems(ArrayList items) {
        for (int i = 0; i != items.size(); i++) {
         /*  if (items.get(i) == 1) {
            items.set(i, "0" + items.get(i));
           }*/
        }
      //  this.items = items;
    }


    // references to our items
    public String[] days;

    public void refreshDays() {
        // clear items
       // items.clear();
       // dayString.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        Log.e("","month_Startday"+pmonth);
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        Log.e("","first_day"+firstDay);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH,maxP);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pmonthmaxset.getTime());
            Log.e("","item_Values"+itemvalue);
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }


}