package com.vedanshtechnologies.swipetouch.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vedanshtechnologies.swipetouch.Adapter.CalenderAdapterView;
import com.vedanshtechnologies.swipetouch.Adapter.Calender_Adapters;
import com.vedanshtechnologies.swipetouch.Adapter.CustomCalenderAdapter;
import com.vedanshtechnologies.swipetouch.Adapter.new_Custom_Calender;
import com.vedanshtechnologies.swipetouch.ModelClass.Month;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthAttendanceModel;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthlyAttendance;
import com.vedanshtechnologies.swipetouch.ModelClass.Attendance;
import com.vedanshtechnologies.swipetouch.R;
import com.vedanshtechnologies.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class customcalender extends AppCompatActivity {
    public GregorianCalendar month, itemmonth;// calendar instances.
        ProgressDialog progressDialog;
    public String[] days;
    static final int FIRST_DAY_OF_WEEK = 0;
    String days_count;
    int  Current_month ;
    //public CalenderAdapterView adapter;// adapter instance
    public new_Custom_Calender adapter;// adapter instance
     TextView  title;
    public Handler handler;// for grabbing some event values for showing the dot
     int monthno;
    GridView gridview;
    //ArrayList<String> = new ArrayList<>(items);
    Month monthmodel;

    ArrayList<MonthlyAttendance> monthlyAttendances = new ArrayList<>();
    RelativeLayout next,previous;
    TextView tv_attendancePercentage;
    ProgressBar pBarattendance;

    MonthlyAttendance monthlyAttendance;
    ArrayList<Month> monthList = new ArrayList<>();
    String url = "http://stage.swiftcampus.com/universal_app_api2.php?Parameter=StudMAttendance&StudentId=12345";
    TextView current_month, tv_stupresent, tv_stuabsent, stu_holidays, stu_leaves, tv_stu_halfday, stu_latecoms;
    public ArrayList items; // container to store calendar items which


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customcalender);

        Locale.setDefault(Locale.US);
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        String Month  = (String) android.text.format.DateFormat.format("MMMM yyyy", month);
        Current_month =month.get(Calendar.MONTH);

        Log.e("","month"+String.valueOf(Current_month));
        itemmonth = (GregorianCalendar) month.clone();
        Log.e("","month"+String.valueOf(itemmonth));


        items = new ArrayList();

        //StrudentsPresentAbsents();

        gridview = (GridView) findViewById(R.id.gridview);
        pBarattendance = (ProgressBar) findViewById(R.id.pBarattendance);
        tv_attendancePercentage = (TextView) findViewById(R.id.tv_attendancePercentage);
        //adapter = new CustomCalenderAdapter(customcalender.this,monthlyAttendance, month);

        adapter = new new_Custom_Calender(customcalender.this,monthList, month);
        progressDialog = new ProgressDialog(customcalender.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        if(NetworkUtil.isNetworkAvailable(customcalender.this)){
            Log.e("","is_true");
            StudentMonthlyDetail();
        }else{
            Toast.makeText(customcalender.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }


        handler = new Handler();
        handler.post(calendarUpdater);

        title = (TextView) findViewById(R.id.title);
         next = (RelativeLayout) findViewById(R.id.next);
         previous = (RelativeLayout) findViewById(R.id.previous);
        tv_stupresent = (TextView) findViewById(R.id.tv_stupresent);
        tv_stuabsent = (TextView) findViewById(R.id.tv_stuabsent);
        stu_holidays = (TextView) findViewById(R.id.stu_holidays);
        stu_leaves = (TextView) findViewById(R.id.stu_leaves);
        tv_stu_halfday = (TextView) findViewById(R.id.tv_stu_halfday);
        stu_latecoms = (TextView) findViewById(R.id.stu_latecoms);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
        next.setVisibility(View.GONE);


        previous.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
                next.setVisibility(View.VISIBLE);
                int  monthno =month.get(Calendar.MONTH);
                if(monthno == Current_month){
                    next.setVisibility(View.GONE);
                }else if(monthno==3){
                    previous.setVisibility(View.GONE);
                }

           /*     if(monthno==(0)){
                    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                    new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                    gridview.setAdapter(new_custom_calender);
                }else if(monthno==(1)){
                    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                    new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                    gridview.setAdapter(new_custom_calender);
                }else if(monthno==(2)){
                    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                    new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                    gridview.setAdapter(new_custom_calender);
                }else{*/
                    ArrayList<Month>monthArrayList =  getMonthResponse("Month_List");
                    Log.e("","month_no_Size"+ String.valueOf(monthArrayList.size()));
                    for(int i=0;i<monthArrayList.size();i++){
                        String present_count = monthArrayList.get(monthno-3).getPresent_count();
                        tv_stupresent.setText(present_count);
                        Log.e("","presnr_count"+present_count);
                        String Absents = monthArrayList.get(monthno-3).getAbsent_count();
                        tv_stuabsent.setText(Absents);
                        String halfday = monthArrayList.get(monthno-3).getHalfday_count();
                        tv_stu_halfday.setText(halfday);
                        String holiday = monthArrayList.get(monthno-3).getHoliday_count();
                        stu_holidays.setText(holiday);
                        String Late = monthArrayList.get(monthno-3).getLate_count();
                        stu_latecoms.setText(Late);
                        String attendance_percents = monthArrayList.get(monthno-3).getattendance_percent();
                        tv_attendancePercentage.setText(attendance_percents+"%");

                        int myNum = 0;

                        try {
                            myNum = Integer.parseInt(attendance_percents);
                        } catch(NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        if(myNum>40&&myNum<70){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                            Log.e("","is_true_beetween40-70");
                        }else if(myNum>70){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                            Log.e("","is_true_greather70");
                        }else if(myNum<40){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.RED));
                            Log.e("","is_true_lessa40");
                        }
                        pBarattendance.setProgress(myNum);


                    }



                    new_Custom_Calender mCalenderAd = new new_Custom_Calender(customcalender.this, monthArrayList, month);
                    gridview.setAdapter(mCalenderAd);



            }
        });


        next.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                setNextMonth();

                getMonthResponse("Month_List");
                Log.e("","month_list_size"+getMonthResponse("Month_List").size());
                int  monthno =month.get(Calendar.MONTH);
                previous.setVisibility(View.VISIBLE);
                if(monthno == Current_month){
                    next.setVisibility(View.GONE);
                }else if(monthno==3){
                    previous.setVisibility(View.GONE);
                }

               /* if(monthno==(0)){
                   title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                 new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                 gridview.setAdapter(new_custom_calender);
                }else if(monthno==(1)){
                    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                    new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                    gridview.setAdapter(new_custom_calender);
                }else if(monthno==(2)){
                    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
                    new_Custom_Calender new_custom_calender = new new_Custom_Calender(customcalender.this,month,0);
                    gridview.setAdapter(new_custom_calender);
                }
                else{*/
                    Log.e("","month_no"+String.valueOf(monthno));
                    ArrayList<Month>monthArrayList =  getMonthResponse("Month_List");
                    Log.e("","month_no_Size"+ String.valueOf(monthArrayList.size()));
                    for(int i=0;i<monthArrayList.size();i++){
                        String present_count = monthArrayList.get(monthno-3).getPresent_count();
                        tv_stupresent.setText(present_count);
                        String Absents = monthArrayList.get(monthno-3).getAbsent_count();
                        tv_stuabsent.setText(Absents);
                        String halfday = monthArrayList.get(monthno-3).getHalfday_count();
                        tv_stu_halfday.setText(halfday);
                        String holiday = monthArrayList.get(monthno-3).getHoliday_count();
                        stu_holidays.setText(holiday);
                        String Late = monthArrayList.get(monthno-3).getLate_count();
                        stu_latecoms.setText(Late);
                        String attendance_percent = monthArrayList.get(monthno-3).getattendance_percent();
                        Log.e("","attendance_percent"+attendance_percent);
                        tv_attendancePercentage.setText(attendance_percent);

                        int myNum = 0;

                        try {
                            myNum = Integer.parseInt(attendance_percent);
                        } catch(NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        if(myNum>40&&myNum<70){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                            Log.e("","is_true_beetween40-70");
                        }else if(myNum>70){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                            Log.e("","is_true_greather70");
                        }else if(myNum<40){
                            pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.RED));
                            Log.e("","is_true_lessa40");
                        }
                        pBarattendance.setProgress(myNum);



                    }

                    new_Custom_Calender mCalenderAd = new new_Custom_Calender(customcalender.this, getMonthResponse("Month_List"), month);
                    gridview.setAdapter(mCalenderAd);
                    refreshCalendar();



            }
        });


    }




    private void StudentMonthlyDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);

                    String status = dataob1.getString("status");
                    String message = dataob1.getString("message");
                    if (status.equals("200")) {
                        JSONArray monthsdataarray = dataob1.getJSONArray("months"); ///2 le
                        for (int i = 0;i<monthsdataarray.length();i++){
                            JSONObject monthdataob = monthsdataarray.getJSONObject(i);
                            Log.e("","month_array_len"+monthsdataarray.length());
                            monthmodel = new Month();
                            String month_no = monthdataob.getString("month_no");
                            monthmodel.setMonth_no(month_no);
                            String Month_name = monthdataob.getString("month_name");
                            monthmodel.setMonth_name(Month_name);
                            days_count = monthdataob.getString("days_count");
                            monthmodel.setDays_count(days_count);
                            String present_count = monthdataob.getString("present_count");
                             monthmodel.setPresent_count(present_count);
                             tv_stupresent.setText(present_count);
                             String absent_count = monthdataob.getString("absent_count");
                             monthmodel.setAbsent_count(absent_count);
                             tv_stuabsent.setText(absent_count);
                             String late_count = monthdataob.getString("late_count");
                             monthmodel.setLate_count(late_count);
                             stu_latecoms.setText(late_count);
                             String halfday_count = monthdataob.getString("halfday_count");
                             monthmodel.setHalfday_count(halfday_count);
                             tv_stu_halfday.setText(halfday_count);
                             String holiday_count = monthdataob.getString("holiday_count");
                             String attendance_percent = monthdataob.getString("attendance_percent");
                            monthmodel.setattendance_percent(attendance_percent);
                             tv_attendancePercentage.setText(attendance_percent+"%");
                            int myNum = 0;

                            try {
                                myNum = Integer.parseInt(attendance_percent);
                            } catch(NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }
                            if(myNum>40&&myNum<70){
                                pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                                Log.e("","is_true_beetween40-70");
                            }else if(myNum>70){
                                pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                                Log.e("","is_true_greather70");
                            }else if(myNum<=40){
                                pBarattendance.setProgressTintList(ColorStateList.valueOf(Color.RED));
                                Log.e("","is_true_lessa40");
                            }

                            pBarattendance.setProgress(myNum);
                             monthmodel.setHoliday_count(holiday_count);
                             stu_holidays.setText(holiday_count);
                             JSONArray attendanceArray = monthdataob.getJSONArray("attendance");
                             ArrayList<Attendance>attendanceArrayList = new ArrayList<>();
                             for (int j = 0; j < attendanceArray.length(); j++) {
                                 Log.e("","attendance_array_len"+attendanceArray.length());
                                JSONObject attendenceob = attendanceArray.getJSONObject(j);
                                Attendance attendance = new Attendance();
                                String day = attendenceob.getString("day");
                                String pastatus = attendenceob.getString("status");
                                attendance.setDay(day);
                                attendance.setPastatus(pastatus);
                                 attendanceArrayList.add(attendance);

                            }
                            monthmodel.setAttendanceList(attendanceArrayList);
                            monthList.add(monthmodel);
                            saveMonthResponse(monthList,"Month_List");
                        }

                    }

                    new_Custom_Calender mCalenderAd = new new_Custom_Calender(customcalender.this, monthList, month);
                    gridview.setAdapter(mCalenderAd);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "responce_ec" + error);

            }
        })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("request-type", "StudMAttendance");
                params.put("StudentId", "12345");

                Log.e("", "Student_monthly_detail" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(customcalender.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }



    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void saveMonthResponse(ArrayList<Month> list, String key){
        Log.e("","is_call");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(customcalender.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<Month> getMonthResponse(String key){
        items.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(customcalender.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Month>>() {}.getType();
        return gson.fromJson(json, type);
    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public void refreshCalendar() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        int i = 0;
        int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.e("","last_date"+lastDay);
        int firstDay = (int) month.get(Calendar.DAY_OF_WEEK);
        Log.e("","first_day"+firstDay);
        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
        }

        int j = FIRST_DAY_OF_WEEK;

        // populate empty days before first real day
        if (firstDay > 1) {
            for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
                days[j] = "";
            }
        } else {
            for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
                days[j] = "";
            }
            j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;
        Log.e("", "first_week_of_day" + j);
        for ( i = j - 1; i < days.length; i++) {
            days[i] = "" + dayNumber;

            dayNumber++;
        }
        // Log.e("","days"+days[i]);
        adapter.setItems(j,dayNumber);
        adapter.notifyDataSetChanged();

        // handler.post(calendarUpdater); // generate some calendar items
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add("2020-09-12");
                items.add("2020-10-07");
                items.add("2020-10-15");
                items.add("2020-10-20");
                items.add("2020-11-30");

            }

            adapter.refreshDays();
            adapter.notifyDataSetChanged();
        }
    };


}
