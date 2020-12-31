package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
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
import com.vedanshtechnologies.swipetouch.Adapter.CalendarAdapter;
import com.vedanshtechnologies.swipetouch.Adapter.CalenderAdapterView;
import com.vedanshtechnologies.swipetouch.Adapter.Calender_Adapters;
import com.vedanshtechnologies.swipetouch.Adapter.CustomCalenderAdapter;
import com.vedanshtechnologies.swipetouch.ModelClass.Month;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthAttendanceModel;
import com.vedanshtechnologies.swipetouch.ModelClass.MonthlyAttendance;
import com.vedanshtechnologies.swipetouch.ModelClass.Attendance;
import com.vedanshtechnologies.swipetouch.ModelClass.StudentAttendance;
import com.vedanshtechnologies.swipetouch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainCalenderActivity extends AppCompatActivity {
    static final int FIRST_DAY_OF_WEEK = 0;
    public GregorianCalendar month, itemmonth;// calendar instances.
    ProgressDialog progressDialog;
    String days_count;
    public Calender_Adapters adapter;// adapter instance
    public Handler handler; // for grabbing some event values for showing the dot
    // marker.

    public String[] days;
    ArrayList<Month> monthList = new ArrayList<>();
    Month monthmodel;
    ArrayList<Attendance> attendanceList = new ArrayList<>();
    Attendance studentPreAbsModel;

    ArrayList<MonthlyAttendance> monthlyAttendances = new ArrayList<>();
    MonthlyAttendance monthlyAttendance;
    GridView gridview;
    ArrayList<MonthAttendanceModel> monthAttendanceModels = new ArrayList<>();

    String change_url = "http://stage.swiftcampus.com/universal_app_api2.php?Parameter=StudMAttendance&StudentId=12345";
    String url = "http://stage.swiftcampus.com/universal_app_api2.php?Parameter=StudMAttendance&StudentId=12345";
    TextView current_month, tv_stupresent, tv_stuabsent, stu_holidays, stu_leaves, tv_stu_halfday, stu_latecoms;
    public ArrayList items; // container to store calendar items which


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calender);

        Locale.setDefault(Locale.US);


        month = (GregorianCalendar) GregorianCalendar.getInstance();
        Log.e("", "month_name" + android.text.format.DateFormat.format("MMMM yyyy", month));

        itemmonth = (GregorianCalendar) month.clone();

        items = new ArrayList();


        gridview = (GridView) findViewById(R.id.gridview);



        adapter = new Calender_Adapters(MainCalenderActivity.this, attendanceList, month);

        progressDialog = new ProgressDialog(MainCalenderActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
       /* //  StudentAttendance();
        handler = new Handler();
        handler.post(calendarUpdater);*/
        StudentMonthlyDetail();


        TextView title = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
               // StudentAttendancenovemer();
                Calender_Adapters calender_adapters = new Calender_Adapters(MainCalenderActivity.this,month,0);
                gridview.setAdapter(calender_adapters);
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                Calender_Adapters calender_adapters = new Calender_Adapters(MainCalenderActivity.this,month,0);
                gridview.setAdapter(calender_adapters);
                refreshCalendar();

            }
        });

    }



    private void StudentAttendancenovemer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
          response = "{\n" +
                  "            \"status\": \"200\",\n" +
                  "            \"message\": \"success\",\n" +
                  "            \"months\": [\n" +
                  "    {\n" +
                  "        \"month_no\": \"11\",\n" +
                  "            \"month_name\": \"November\",\n" +
                  "            \"days_count\": \"30\",\n" +
                  "            \"present_count\": \"23\",\n" +
                  "            \"absent_count\": \"1\",\n" +
                  "            \"late_count\": \"1\",\n" +
                  "            \"halfday_count\": \"1\",\n" +
                  "            \"holiday_count\": \"4\",\n" +
                  "            \"attendance\": [\n" +
                  "        {\n" +
                  "            \"day\": \"1\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"2\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"3\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"4\",\n" +
                  "                \"status\": \"LATE\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"5\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"6\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"7\",\n" +
                  "                \"status\": \"HOLIDAY\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"8\",\n" +
                  "                \"status\": \"ABSENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"9\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"10\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"11\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"12\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"13\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"14\",\n" +
                  "                \"status\": \"HOLIDAY\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"15\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"16\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"17\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"18\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"19\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"10\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"21\",\n" +
                  "                \"status\": \"HOLIDAY\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"22\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"23\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"24\",\n" +
                  "                \"status\": \"HALFDAY\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"25\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"26\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"27\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"28\",\n" +
                  "                \"status\": \"HOLIDAY\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"29\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        },\n" +
                  "        {\n" +
                  "            \"day\": \"30\",\n" +
                  "                \"status\": \"PRESENT\"\n" +
                  "        }\n" +
                  "            ]\n" +
                  "    }\n" +
                  "    ]\n" +
                  "}";

                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);

                    String status = dataob1.getString("status");

                    String message = dataob1.getString("message");

                    if (status.equals("200")) {
                        JSONArray monthsdataarray = dataob1.getJSONArray("months"); ///2 le
                        ArrayList<Month> monthList = new ArrayList<>();
                        for (int i = 0; i < monthsdataarray.length(); i++) {
                            JSONObject monthdataob = monthsdataarray.getJSONObject(i);
                            monthmodel = new Month();
                            String month_no = monthdataob.getString("month_no");
                            monthmodel.setMonth_no(month_no);
                            String Month_name = monthdataob.getString("month_name");
                            monthmodel.setMonth_name(Month_name);
                            days_count = monthdataob.getString("days_count");
                            monthmodel.setHalfday_count(days_count);
                            String present_count = monthdataob.getString("present_count");
                            monthmodel.setPresent_count(present_count);
                            // tv_stupresent.setText(present_count);
                            String absent_count = monthdataob.getString("absent_count");
                            monthmodel.setAbsent_count(absent_count);
                            //tv_stuabsent.setText(absent_count);
                            String late_count = monthdataob.getString("late_count");
                            monthmodel.setLate_count(late_count);
                            // stu_latecoms.setText(late_count);
                            String halfday_count = monthdataob.getString("halfday_count");
                            monthmodel.setHalfday_count(halfday_count);
                            // tv_stu_halfday.setText(halfday_count);
                            String holiday_count = monthdataob.getString("holiday_count");
                            monthmodel.setHoliday_count(holiday_count);
                            // stu_holidays.setText(holiday_count);

                            JSONArray attendanceArray = monthdataob.getJSONArray("attendance");

                            ArrayList<Attendance> attendanceList = new ArrayList<>();  //30 l
                            for (int j = 0; j < attendanceArray.length(); j++) {
                                JSONObject attendenceob = attendanceArray.getJSONObject(j);
                                Attendance attendance = new Attendance();
                                String day = attendenceob.getString("day");
                                String pastatus = attendenceob.getString("status");
                                attendance.setDay(day);
                                attendance.setPastatus(pastatus);
                                attendanceList.add(attendance);

                            }
                            monthmodel.setAttendanceList(attendanceList);
                            monthList.add(monthmodel);

                        }


                    }
                    Calender_Adapters mCalenderAd = new Calender_Adapters(MainCalenderActivity.this, attendanceList, month);
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
        }) {
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainCalenderActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void StudentMonthlyDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);

                    String status = dataob1.getString("status");

                    String message = dataob1.getString("message");


                    if (status.equals("200")) {
                        JSONArray monthsdataarray = dataob1.getJSONArray("months"); ///2 le
                        ArrayList<Month> monthList = new ArrayList<>();
                        for (int i = 0; i < monthsdataarray.length(); i++) {
                            JSONObject monthdataob = monthsdataarray.getJSONObject(i);
                            monthmodel = new Month();
                            String month_no = monthdataob.getString("month_no");
                            monthmodel.setMonth_no(month_no);
                            String Month_name = monthdataob.getString("month_name");
                            monthmodel.setMonth_name(Month_name);
                            days_count = monthdataob.getString("days_count");
                            monthmodel.setHalfday_count(days_count);
                            String present_count = monthdataob.getString("present_count");
                            monthmodel.setPresent_count(present_count);
                            // tv_stupresent.setText(present_count);
                            String absent_count = monthdataob.getString("absent_count");
                            monthmodel.setAbsent_count(absent_count);
                            //tv_stuabsent.setText(absent_count);
                            String late_count = monthdataob.getString("late_count");
                            monthmodel.setLate_count(late_count);
                            // stu_latecoms.setText(late_count);
                            String halfday_count = monthdataob.getString("halfday_count");
                            monthmodel.setHalfday_count(halfday_count);
                            // tv_stu_halfday.setText(halfday_count);
                            String holiday_count = monthdataob.getString("holiday_count");
                            monthmodel.setHoliday_count(holiday_count);
                            // stu_holidays.setText(holiday_count);

                            JSONArray attendanceArray = monthdataob.getJSONArray("attendance");

                            ArrayList<Attendance> attendanceList = new ArrayList<>();  //30 l
                            for (int j = 0; j < attendanceArray.length(); j++) {
                                JSONObject attendenceob = attendanceArray.getJSONObject(j);
                                Attendance attendance = new Attendance();
                                String day = attendenceob.getString("day");
                                String pastatus = attendenceob.getString("status");
                                attendance.setDay(day);
                                attendance.setPastatus(pastatus);
                                attendanceList.add(attendance);

                            }
                            monthmodel.setAttendanceList(attendanceList);
                            monthList.add(monthmodel);

                        }


                    }
                    Calender_Adapters mCalenderAd = new Calender_Adapters(MainCalenderActivity.this, attendanceList, month);
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
        }) {
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainCalenderActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }


    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month.getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1), month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
        }

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
    }

   /* public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {

        }


    };*/
}



