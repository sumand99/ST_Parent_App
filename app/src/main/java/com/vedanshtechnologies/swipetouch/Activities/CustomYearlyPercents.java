package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hadiidbouk.charts.BarData;


import com.hadiidbouk.charts.ChartProgressBar;
import com.vedanshtechnologies.swipetouch.Adapter.YearlyPercentageAdapter;
import com.vedanshtechnologies.swipetouch.Adapter.new_Custom_Calender;
import com.vedanshtechnologies.swipetouch.ModelClass.AttendancePercentageModel;
import com.vedanshtechnologies.swipetouch.ModelClass.Month;
import com.vedanshtechnologies.swipetouch.R;
import com.vedanshtechnologies.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CustomYearlyPercents extends AppCompatActivity {
    String yearlyurl="http://stage.swiftcampus.com/universal_app_api2.php?Parameter=StudYearlyAttendance&StudentId=12345";
    ProgressDialog progressDialog;
    ArrayList<AttendancePercentageModel>attendancePercentageModels = new ArrayList<>();
    RecyclerView rv_YearlyPercentageBar;
    View vv_janView,vv_favview,vv_marchView,vv_aprlView,vv_mayView,vv_junView,vv_julyView,vv_augView,vv_octView,vv_noveView,vv_decView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_yearly_percents);
        vv_janView = findViewById(R.id.vv_janView);
        rv_YearlyPercentageBar = findViewById(R.id.rv_YearlyPercentageBar);
        vv_favview = findViewById(R.id.vv_favview);
        vv_marchView = findViewById(R.id.vv_marchView);
        vv_aprlView = findViewById(R.id.vv_aprlView);
        vv_mayView = findViewById(R.id.vv_mayView);
        vv_junView = findViewById(R.id.vv_junView);
        vv_julyView = findViewById(R.id.vv_julyView);
        vv_augView = findViewById(R.id.vv_augView);
        vv_octView = findViewById(R.id.vv_octView);
        vv_noveView = findViewById(R.id.vv_noveView);
        vv_decView = findViewById(R.id.vv_decView);
        RecyclerView.LayoutManager mLayoutManagerCategory = new LinearLayoutManager(CustomYearlyPercents.this, LinearLayoutManager.VERTICAL, false);
        rv_YearlyPercentageBar.setLayoutManager(mLayoutManagerCategory);
        progressDialog = new ProgressDialog(CustomYearlyPercents.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        if(NetworkUtil.isNetworkAvailable(CustomYearlyPercents.this)){
            Log.e("","is_true");
            StudentYearlyPercentage();
        }else{
            Toast.makeText(CustomYearlyPercents.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }






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
                            attendancePercentageModel.setMonth_name(month_name);
                            attendancePercentageModels.add(attendancePercentageModel);
                        }
                         saveMonthResponse(attendancePercentageModels,"Month_List");

                        YearlyPercentageAdapter yearlyPercentageAdapter = new YearlyPercentageAdapter(CustomYearlyPercents.this,attendancePercentageModels);
                        rv_YearlyPercentageBar.setAdapter(yearlyPercentageAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(CustomYearlyPercents.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
    public void saveMonthResponse(ArrayList<AttendancePercentageModel> list, String key){
        Log.e("","is_call");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CustomYearlyPercents.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<AttendancePercentageModel> getMonthResponse(String key){
        attendancePercentageModels.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CustomYearlyPercents.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<AttendancePercentageModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
}