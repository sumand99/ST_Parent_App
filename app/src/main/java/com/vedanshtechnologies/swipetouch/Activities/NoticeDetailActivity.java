package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.swipetouch.Adapter.NoticesAdapter;
import com.swipetouch.ModelClass.NoticeModelClass;
import com.vedanshtechnologies.swipetouch.R;
import com.swipetouch.UtilityTools.Const;
import com.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoticeDetailActivity extends AppCompatActivity {
    String notice_id;
    String Student_id;
    ImageView Imageid;
    TextView notice_Tittle,notice_Discription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        Imageid  = findViewById(R.id.Imageid);
        notice_Tittle  = findViewById(R.id.notice_Tittle);
        notice_Discription  = findViewById(R.id.notice_Discription);
        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
        Student_id=pref.getString("student_Id","");
        Log.e("","students_id::-"+Student_id);
        Intent intent = getIntent();
         notice_id = intent.getStringExtra("notice_id");
        Log.e("","notice_id"+notice_id);
        if(NetworkUtil.isNetworkAvailable(NoticeDetailActivity.this)){
            Log.e("","is_true");
            getNoticeDetail();
        }else{
            Toast.makeText(NoticeDetailActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }


    }

    private void getNoticeDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.NoticeDescription, new Response.Listener<String>() {
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
                            String notice_id = noticesobj.getString("notice_id");
                            String title = noticesobj.getString("title");
                            notice_Tittle.setText(title);
                            String description = noticesobj.getString("description");
                            notice_Discription.setText(description);
                            String attachment_type = noticesobj.getString("attachment_type");
                            String date_of_notice = noticesobj.getString("date_of_notice");
                            String attachment = noticesobj.getString("attachment");


                            Picasso.with(NoticeDetailActivity.this)
                                    .load(attachment)
                                    .into(Imageid);



                        }


                    }



                } catch (JSONException e) {
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
                params.put("Notice_Id", notice_id);

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
        RequestQueue requestQueue = Volley.newRequestQueue(NoticeDetailActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}