package com.swipetouch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.swipetouch.Adapter.LeaveStatusAdapter;
import com.swipetouch.Adapter.OnlineClassesAdapter;

import com.swipetouch.ModelClass.OnlineClassesModel;
import com.vedanshtechnologies.swipetouch.R;
import com.swipetouch.UtilityTools.Const;
import com.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OnlineClassesActivity extends AppCompatActivity {
    ImageView backicon;
    RecyclerView rv_onlineclasses;
    ProgressDialog progressDialog;
    String Student_id= "";
    String Current_date;



    OnlineClassesAdapters onlineClassesAdapter;
    ArrayList<OnlineClassesModel>onlineClassesModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_classes);
        backicon =findViewById(R.id.backicon);
        rv_onlineclasses =findViewById(R.id.rv_onlineclasses);

        if(NetworkUtil.isNetworkAvailable(OnlineClassesActivity.this)){
           Log.e("","is_true");
            OnlineClassesDetail();
        }else{
            Toast.makeText(this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }



        Date cDate = new Date();
        Current_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        SimpleDateFormat your_format = new SimpleDateFormat("dd-MM-yyyy");
        String date2 = your_format.format(cDate);
        Log.e("","curentdate:-"+date2);



        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
        Student_id=pref.getString("student_Id","");
        Log.e("","students_id::-"+Student_id);

        rv_onlineclasses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressDialog = new ProgressDialog(OnlineClassesActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                
            }
        });
    }

    private void OnlineClassesDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.OnlineMeetingList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("","leavelist"+response);
                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);
                    String status = dataob1.getString("status");

                    if (status.equals("200")) {
                        String message = dataob1.getString("message");
                        String name = dataob1.getString("name");
                        String Student_class = dataob1.getString("class");
                        String section = dataob1.getString("section");
                        String roll_no = dataob1.getString("roll_no");
                        JSONArray MeetingsArray = dataob1.getJSONArray("Meetings");
                        onlineClassesModelArrayList.clear();
                        for (int i = 0;i<MeetingsArray.length();i++) {
                            JSONObject meatingobj = MeetingsArray.getJSONObject(i);
                            OnlineClassesModel onlineClassesModel = new OnlineClassesModel();
                            String  topic = meatingobj.getString("topic");
                            String  description = meatingobj.getString("description");
                            String  subject = meatingobj.getString("subject");
                            String  teacher = meatingobj.getString("teacher");
                            String  is_blocked = meatingobj.getString("is_blocked");
                            String  class_start_date_time = meatingobj.getString("class_start_date_time");
                            String  Meeting_URL = meatingobj.getString("Meeting_URL");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm a");
                            Date date = dateFormat.parse(class_start_date_time);
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                            String class_Start_date = dateFormat2.format(date);
                            Log.e("","chnagelast;;-"+class_Start_date);
                            onlineClassesModel.class_Start_date = class_Start_date;
                            String  class_end_date_time = meatingobj.getString("class_end_date_time");


                            onlineClassesModel.setTopic(topic);
                            onlineClassesModel.setIs_blocked(is_blocked);
                            onlineClassesModel.setDescription(description);
                            onlineClassesModel.setSubject(subject);
                            onlineClassesModel.setTeacher(teacher);
                            onlineClassesModel.setMeeting_URL(Meeting_URL);
                            onlineClassesModel.setClass_start_date_time(class_start_date_time);
                            onlineClassesModel.setClass_end_date_time(class_end_date_time);
                            onlineClassesModelArrayList.add(onlineClassesModel);
                        }

                        onlineClassesAdapter = new OnlineClassesAdapters(OnlineClassesActivity.this,onlineClassesModelArrayList);
                        rv_onlineclasses.setAdapter(onlineClassesAdapter);
                    }





                } catch (JSONException | ParseException e) {
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

                params.put("StudentId",Student_id);

                Log.e("", "leave_submit_param" + params);
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
        RequestQueue requestQueue = Volley.newRequestQueue(OnlineClassesActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        OnlineClassesDetail();
    }

    public class OnlineClassesAdapters extends RecyclerView.Adapter<OnlineClassesAdapters.ClassViewHolder> {
        Context context;
        ArrayList<OnlineClassesModel> onlineClassesModelArrayList;
        String Current_date;
        String MyCurrentdate;


        public OnlineClassesAdapters(Context context, ArrayList<OnlineClassesModel> onlineClassesModels) {
            this.context = context;
            this.onlineClassesModelArrayList = onlineClassesModels;
            Date cDate = new Date();
            Current_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
            SimpleDateFormat your_format = new SimpleDateFormat("dd-MM-yyyy");
            MyCurrentdate = your_format.format(cDate);
            Log.e("", "curentdate:-" + MyCurrentdate);
        }

        @NonNull
        @Override
        public OnlineClassesAdapters.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.onlineclasses, null);
            return new OnlineClassesAdapters.ClassViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull OnlineClassesAdapters.ClassViewHolder holder, int position) {
            final OnlineClassesModel onlineClassesModel = onlineClassesModelArrayList.get(position);
            Log.e("", "cutrrendate_" + MyCurrentdate);
            Log.e("", "modeldate" + onlineClassesModel.class_Start_date);
            if (onlineClassesModel.class_Start_date.compareTo(MyCurrentdate) < 0) {
                holder.ll_view.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            } else {
                holder.iv_topicname.setText(onlineClassesModel.getTopic());
                holder.iv_classendtime.setText(onlineClassesModel.getClass_end_date_time());
                holder.iv_classstarttime.setText(onlineClassesModel.getClass_start_date_time());
                holder.tv_subjectname.setText(onlineClassesModel.getSubject());
                holder.tv_teachername.setText(onlineClassesModel.getTeacher());
                holder.tv_description.setText(onlineClassesModel.getDescription());
                Log.e("","bloaked_status"+onlineClassesModel.getIs_blocked());
                if (onlineClassesModel.getIs_blocked().equals("false")) {
                    holder.buttonlay.setVisibility(View.VISIBLE);
                    holder.ll_tvlayout.setVisibility(View.GONE);
                    holder.buttonjoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = onlineClassesModel.getMeeting_URL();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                        }
                    });
                } else {
                    holder.buttonlay.setVisibility(View.GONE);
                    holder.ll_tvlayout.setVisibility(View.VISIBLE);

                }


            }


        }


        @Override
        public int getItemCount() {
            return onlineClassesModelArrayList.size();
        }

        public class ClassViewHolder extends RecyclerView.ViewHolder {
            Button buttonjoin;
            RelativeLayout ll_view;
            LinearLayout buttonlay, ll_tvlayout;
            TextView tv_subjectname, tv_teachername, iv_topicname, iv_classstarttime, iv_classendtime, tv_description, tv_blocked_status;

            public ClassViewHolder(@NonNull View itemView) {
                super(itemView);
                buttonjoin = itemView.findViewById(R.id.buttonjoin);
                tv_subjectname = itemView.findViewById(R.id.tv_subjectname);
                iv_topicname = itemView.findViewById(R.id.iv_topicname);
                tv_teachername = itemView.findViewById(R.id.tv_teachername);
                iv_classstarttime = itemView.findViewById(R.id.iv_classstarttime);
                iv_classendtime = itemView.findViewById(R.id.iv_classendtime);
                tv_description = itemView.findViewById(R.id.tv_description);
                tv_blocked_status = itemView.findViewById(R.id.tv_blocked_status);
                ll_view = itemView.findViewById(R.id.ll_view);
                buttonlay = itemView.findViewById(R.id.buttonlay);
                ll_tvlayout = itemView.findViewById(R.id.ll_tvlayout);

            }
        }
    }
    }