package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vedanshtechnologies.swipetouch.ModelClass.AttendancePercentageModel;
import com.vedanshtechnologies.swipetouch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YearlyPercentageActivity extends AppCompatActivity {

    String yearlyurl="http://stage.swiftcampus.com/universal_app_api2.php?Parameter=StudYearlyAttendance&StudentId=12345";
    ProgressDialog progressDialog;
    ArrayList<BarEntry>barEntryArrayList  = new ArrayList<>();
    ArrayList<String>month_namell = new ArrayList<>();
    private static final int MAX_X_VALUE = 12;
    private static final int MAX_Y_VALUE = 100;
    private static final int MIN_Y_VALUE = 75;
    private static final String[] DAYS = { "DEC", "JAN", "FEB", "MAR", "APR", "MAY", "JUN","JULY","AUG","SEP","OCT","NOV","DEC" };
    private static final String SET_LABEL = "Yearly Attendance Percentage";
    ArrayList<String>Percentage = new ArrayList<>();
    ArrayList<AttendancePercentageModel>percentageModels = new ArrayList<>();
    ArrayList<String>Month = new ArrayList<>();
    String mydata [] ;
    //BarChart chart;

    HorizontalBarChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_percentage);
         chart = (HorizontalBarChart) findViewById(R.id.chart);
      /*  progressDialog = new ProgressDialog(YearlyPercentageActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        StudentYearlyPercentage();
        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);*/

    }

    private void StudentYearlyPercentage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, yearlyurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("","yearly_reaponec"+response);

                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);
                    String status = dataob1.getString("status");
                    String message = dataob1.getString("message");
                    if (status.equals("200")) {
                        JSONArray monthsdataarray = dataob1.getJSONArray("month");
                        for (int i = 0;i<monthsdataarray.length();i++) {
                            JSONObject monthdataob = monthsdataarray.getJSONObject(i);
                            AttendancePercentageModel attendancePercentageModel = new AttendancePercentageModel();
                            String  month_no = monthdataob.getString("month_no");
                            String  month_name = monthdataob.getString("month_name");
                            String  attendance_percent = monthdataob.getString("attendance_percent");

                            attendancePercentageModel.setPercentage(attendance_percent);
                            percentageModels.add(attendancePercentageModel);
                            month_namell.add(month_name);
                            Percentage.add(attendance_percent);



                        }



                    }




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
        RequestQueue requestQueue = Volley.newRequestQueue(YearlyPercentageActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

  /*  private BarData createChartData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < MAX_X_VALUE; i++) {
            float x = i;
            float y = new Util().randomFloatBetween(Percentage);
            y= 75;
            Log.e("","minus_value"+y);
            values.add(new BarEntry(x, y));
        }

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        return data;
    }
    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);

        chart.animateXY(2000, 2000);
        chart.invalidate();
    }*/
}