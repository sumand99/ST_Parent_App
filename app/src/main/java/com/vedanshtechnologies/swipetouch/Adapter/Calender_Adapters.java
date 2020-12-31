package com.vedanshtechnologies.swipetouch.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vedanshtechnologies.swipetouch.Activities.MainCalenderActivity;
import com.vedanshtechnologies.swipetouch.ModelClass.Attendance;
import com.vedanshtechnologies.swipetouch.ModelClass.Month;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthAttendanceModel;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthlyAttendance;
import com.vedanshtechnologies.swipetouch.ModelClass.StudentAttendance;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Calender_Adapters extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK =0; // Sunday = 0, Monday = 1
    private String mVisibility;
    private Context mContext;
    String date;
    private java.util.Calendar month;
    private Calendar selectedDate;
    ArrayList <Attendance> items;

           public  Calender_Adapters(Context c, Calendar monthCalender, int i){
                month = monthCalender;
                selectedDate = (Calendar)monthCalender.clone();
                mContext = c;
                month.set(Calendar.DAY_OF_MONTH, 1);
                refreshDays();
            }


    public Calender_Adapters(MainCalenderActivity mainCalenderActivity, ArrayList<Attendance> monthlyAttendance, Calendar monthCalendar) {
        month = monthCalendar;
        selectedDate = (Calendar)monthCalendar.clone();
        mContext = mainCalenderActivity;
        this.items =monthlyAttendance;
        month.set(Calendar.DAY_OF_MONTH, 1);

        refreshDays();
    }





    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

       // Month monthlyAttendance = items.get(i);
        TextView dayView;
        if (view == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }

        dayView = (TextView)
                v.findViewById(R.id.date);
        RelativeLayout mImageBaground = (RelativeLayout)
                v.findViewById(R.id.ll_items);


        // disable empty days from the beginning
        if(days[i].equals("")) {
            dayView.setClickable(false);
            dayView.setFocusable(false);
            //need to show previous months date

        }
        else {

             date = days[i];
             dayView.setText(date);

            /*//items.get(j).getDay().length();
            int  k = Integer.parseInt(date);
             k = k - 1;

            for(int j=0;j<items.get(j).getDay().length();j++){

               String statusdate = items.get(k).getDay();
               String datestatus = items.get(k).getPastatus();

                if(date.equals(statusdate)){

                    if(datestatus.equals("PRESENT")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundful);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }else if(datestatus.equals("HALFDAY")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundabhalf);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }else if(datestatus.equals("ABSENT")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundabsent);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }else if(datestatus.equals("LATE")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundlate);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }else if(datestatus.equals("SUNDAY")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundleave);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }else if(datestatus.equals("HOLIDAY")){
                        mImageBaground.setBackgroundResource(R.drawable.list_item_backgroundholiday);
                        dayView.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                }


            }
*/
            if(date.length()==1) {
                date = "0"+date;
            }

        }

        dayView.setText(days[i]);

        // create date string for comparison

        String monthStr = ""+(month.get(Calendar.MONTH)+1);

        if(monthStr.length()==1) {
            monthStr = "0"+monthStr;
        }


        return v;
    }


    public void refreshDays()
    {
        // clear items
       // items.clear();
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);

        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);

        // figure size of the array
        if(firstDay==1){
            days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
        }
        else {
            days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
        }

        int j=FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if(firstDay>1) {
            for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
                days[j] = "";
            }
        }
        else {
            for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
                days[j] = "";
            }
            j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;
        Log.e("","first_week_of_day"+j);
        for(int i=j-1;i<days.length;i++) {
            days[i] = ""+dayNumber;
            dayNumber++;

        }
    }

    // references to our items
    public String[] days;

    public void setItems(int number, int dayNumber) {

        int dayNumbers = dayNumber;
        Log.e("","first_week_of_day"+number);
        for(int i=number-1;i<days.length;i++) {
            days[i] = ""+dayNumber;
            dayNumbers++;

        }
    }


}

