package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swipetouch.Adapter.NoticesAdapter;
import com.swipetouch.Adapter.OnlineClassesAdapter;
import com.swipetouch.ModelClass.NoticeModelClass;
import com.vedanshtechnologies.swipetouch.R;
import com.swipetouch.UtilityTools.Const;
import com.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ENoticeActivity extends AppCompatActivity {
    ImageView backicon;
    RecyclerView rv_notices;
    String Student_id;
    ProgressDialog progressDialog;
    ArrayList<NoticeModelClass>noticeModelClasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_notice);
        backicon = findViewById(R.id.backicon);
        rv_notices = findViewById(R.id.rv_notices);
        if(NetworkUtil.isNetworkAvailable(ENoticeActivity.this)){
            Log.e("","is_true");
            Enotices();
        }else{
            Toast.makeText(ENoticeActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }


        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
        Student_id=pref.getString("student_Id","");
        Log.e("","students_id::-"+Student_id);

        rv_notices.setHasFixedSize(true);
        rv_notices.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressDialog = new ProgressDialog(ENoticeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

         backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Enotices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.NoticeList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("", "student_noticeresponce" + response);
                try {
                    JSONObject dataobj = new JSONObject(response);
                    String status  = dataobj.getString("status");
                    String message  = dataobj.getString("message");
                    String name  = dataobj.getString("name");
                    String Student_class  = dataobj.getString("class");
                    String section  = dataobj.getString("section");
                    String roll_no  = dataobj.getString("roll_no");
                   if(status.equals("200")){
                       JSONArray Noticearray= dataobj.getJSONArray("Notices");
                       for(int i=0;i<Noticearray.length();i++){
                           JSONObject noticesobj = Noticearray.getJSONObject(i);
                           NoticeModelClass noticeModelClass = new NoticeModelClass();
                           String notice_id = noticesobj.getString("notice_id");
                           String title = noticesobj.getString("title");
                           String date_of_notice = noticesobj.getString("date_of_notice");

                           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD  HH:mm:SS");
                           Date date = dateFormat.parse(date_of_notice);
                           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                           String notice_date = dateFormat2.format(date);

                           Log.e("","chnagelast;;-"+notice_date);
                           noticeModelClass.notice_date = notice_date;
                           noticeModelClass.setNotice_id(notice_id);
                           noticeModelClass.setDate_of_notice(date_of_notice);
                           noticeModelClass.setTitle(title);
                           noticeModelClasses.add(noticeModelClass);
                       }

                       NoticesAdapter noticesAdapter = new NoticesAdapter(ENoticeActivity.this,noticeModelClasses);
                       rv_notices.setAdapter(noticesAdapter);
                   }



                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Studentid", Student_id);

                Log.e("", "Student_notices" + params);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ENoticeActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}