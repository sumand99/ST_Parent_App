package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.swipetouch.Adapter.CustomAdapter;
import com.vedanshtechnologies.swipetouch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CelenderActivity extends AppCompatActivity {
    Calendar calen = Calendar.getInstance();
    int month = Calendar.MONTH;
    String[] DateList ;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celender);
        DateList = init();

        GridView grid = (GridView) findViewById(R.id.gridview);
        adapter = new CustomAdapter(this,DateList);
        grid.setAdapter(adapter);
    }

    public void previous(View v){
        month--;
        DateList = changemonth(month);

        for(int i = 0; i < DateList.length;i++){
            Log.e("prev",""+ DateList[i]);
        }
        adapter.notifyDataSetChanged();
    }

    public void next(View v){
        month--;
        DateList = changemonth(month);

        adapter.notifyDataSetChanged();
    }

    private String[] init() {
            String[] DateList = new String[42];
            calen.set(Calendar.DAY_OF_MONTH,1); // first day of the month

            int date  = calen.get(Calendar.DATE);
            int month = calen.get(Calendar.MONTH);
            int year = calen.get(Calendar.YEAR);
            int day = calen.get(Calendar.DAY_OF_WEEK);


            DateList = BeforeMonth(DateList,day,month);

            int numdays = daysinamonth(month);
            int endmonth = numdays+day -1;
            int j = day-1;

            while(j < (numdays+day-1) ){
                DateList[j] = Integer.toString(calen.get(Calendar.DATE));
                calen.add(Calendar.DAY_OF_MONTH,1);
                j++;
            }

            int end = 1;
            while(endmonth <42){
                DateList[endmonth] = Integer.toString(end);
                endmonth++;
                end++;
            }

            return DateList;

        }
    private String[] changemonth(int month){
        calen.set(Calendar.YEAR,month,Calendar.DATE);
        return init();
    }
    //adds the days before the first day of the month to the string list
    private String[]  BeforeMonth(String[] d, int day, int month){
        int LastMo = daysinamonth( month-1); // know how many days in the last month 31 10-1
        //Log.d("bmon","the current month" + month + " last month days " + LastMo + " day " + day);
        switch (day){
            case 1: // sunday
                break;
            case 2: // monday
                d[0] = Integer.toString(LastMo);
                break;
            case 3: // tuesday
                d[0] = Integer.toString(LastMo-1);
                d[1] = Integer.toString(LastMo);
                break;
            case 4: // wednesday
                d[0] = Integer.toString(LastMo-2);
                d[1] = Integer.toString(LastMo-1);
                d[2] = Integer.toString(LastMo);
                break;
            case 5: // thursday
                d[0] = Integer.toString(LastMo-3);
                d[1] = Integer.toString(LastMo-2);
                d[2] = Integer.toString(LastMo-1);
                d[3] = Integer.toString(LastMo);
                break;
            case 6: //friday
                d[0] = Integer.toString(LastMo-4);
                d[1] = Integer.toString(LastMo-3);
                d[2] = Integer.toString(LastMo-2);
                d[3] = Integer.toString(LastMo-1);
                d[4] = Integer.toString(LastMo);
                break;
            case 7: //saturday
                d[0] = Integer.toString(LastMo-5);
                d[1] = Integer.toString(LastMo-4);
                d[2] = Integer.toString(LastMo-3);
                d[3] = Integer.toString(LastMo-2);
                d[4] = Integer.toString(LastMo-1);
                d[5] = Integer.toString(LastMo);
                break;
        }

        return d;
    }
    //check how many days are in each month
    private int daysinamonth(int month){
        switch (month){
            case 0: // January
                return 31;
            case 1: // February
                return 28;
            case 2: // March
                return 31;
            case 3: // April
                return 30;
            case 4: // May
                return 31;
            case 5: // June
                return 30;
            case 6: // July
                return 31;
            case 7: // August
                return 31;
            case 8: // Sept
                return 30;
            case 9: // Oct
                return 31;
            case 10: // Nov
                return 30;
            case 11: // Dec
                return 31;
            default:
                return 0;
        } // switch stmt

    }

}