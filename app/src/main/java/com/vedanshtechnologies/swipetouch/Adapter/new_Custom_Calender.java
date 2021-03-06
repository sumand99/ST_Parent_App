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

import com.swipetouch.ModelClass.Month;
import com.vedanshtechnologies.swipetouch.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class new_Custom_Calender extends BaseAdapter {
    static final int FIRST_DAY_OF_WEEK =0; // Sunday = 0, Monday = 1
    int monthno;
    int m=0;
    private String mVisibility;
    private Context mContext;
    String date;
    List<Date> disable = new ArrayList<>();
    private java.util.Calendar month;
    private Calendar selectedDate;
    //  private ArrayList items;
    ArrayList<Month> items;


    public  new_Custom_Calender(Context c, Calendar monthCalender, int i){
        month = monthCalender;
        selectedDate = (Calendar)monthCalender.clone();
        mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }

    public new_Custom_Calender(Context c, ArrayList<Month> monthlyAttendance, Calendar monthCalendar) {
        month = monthCalendar;
        selectedDate = (Calendar)monthCalendar.clone();
        mContext = c;
        this.items =monthlyAttendance;
        month.set(Calendar.DAY_OF_MONTH, 1);
        refreshDays();
    }
    @Override
    public int getCount() {
        return  days.length;
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


        }else {
            monthno =month.get(Calendar.MONTH);



            switch (monthno){
               /* case 0:
                    monthno =9;
                    break;
                case 1:
                    monthno = 10;
                    break;

                case 2:
                    monthno =11;
                    break;*/

                case 3:
                    monthno =0;
                    break;
                case 4:
                    monthno =1;
                    break;
                case 5:
                    monthno =2;
                    break;
                case 6:
                    monthno =3;
                    break;
                case 7:
                    monthno =4;
                    break;
                case 8:
                    monthno =5;
                    break;
                case 9:
                    monthno =6;
                    break;
                case 10:
                    monthno =7;
                    break;
                case 11:
                    monthno =8;
                    break;

            }

            date = days[i];


            dayView.setText(date);
            int  k = Integer.parseInt(date);
            k = k - 1;

            if(items == null){
                  Log.e("","is_true");
                    }else{
                        int total_month_days = Integer.parseInt(items.get(monthno).getDays_count());
                        String month_name = items.get(monthno).getMonth_name();
                        Log.e("","loop_month_number:-"+String.valueOf(monthno));
                        Log.e("","month_total_days_count"+total_month_days);
                       // Log.e("","monthlist_month"+month_name);

                        for(int j=0;j<total_month_days;j++){
                            Log.e("","attendance_list_size"+ items.get(monthno).getAttendanceList().size());
                            Log.e("","j"+j);
                            Log.e("","this_is_true_condition");

                            if(j< items.get(monthno).getAttendanceList().size()){
                                Log.e("","true");
                                String statusdate = items.get(monthno).getAttendanceList().get(j).getDay();
                                Log.e("","status_date"+statusdate);
                                String  datestatus = items.get(monthno).getAttendanceList().get(j).getPastatus();
                                Log.e("","date_status"+datestatus);

                                if(date.equals(statusdate)){
                                    Log.e("","is_match");
                                    if(datestatus.equals("PRESENT")){
                                        Log.e("","is_match_status");
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
                            }else{
                                Log.e("","false");
                            }








                        }
                    }





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

        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);
        Log.e("","first_date"+firstDay);



        int Month_Number = (int)month.get(Calendar.MONTH);


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
        for(int i=j-1;i<days.length;i++) {
            days[i] = ""+dayNumber;
            dayNumber++;
        }
    }

    public String[] days;

    public void setItems(int j, int dayNumber) {

    }

}
