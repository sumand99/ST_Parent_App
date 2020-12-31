package com.vedanshtechnologies.swipetouch.Activities;

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
import com.vedanshtechnologies.swipetouch.Adapter.LeaveStatusAdapter;
import com.vedanshtechnologies.swipetouch.ModelClass.LeaveStatusModel;
import com.vedanshtechnologies.swipetouch.R;
import com.vedanshtechnologies.swipetouch.UtilityTools.Const;
import com.vedanshtechnologies.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaveStatusActivity extends AppCompatActivity {
    RecyclerView rv_LeaveStatus;
    ProgressDialog progressDialog;
    ImageView backicon;
    String Student_id;
    ArrayList<LeaveStatusModel>leaveStatusModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status);
        rv_LeaveStatus = findViewById(R.id.rv_LeaveStatus);
        backicon = findViewById(R.id.backicon);
        rv_LeaveStatus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressDialog = new ProgressDialog(LeaveStatusActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        if(NetworkUtil.isNetworkAvailable(LeaveStatusActivity.this)){
            Log.e("","is_true");
            getListOfLeaves();
        }else{
            Toast.makeText(LeaveStatusActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }

        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
        Student_id=pref.getString("student_Id","");
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getListOfLeaves() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.ListOfLeaves, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("","leavelist"+response);
                progressDialog.dismiss();
                try {
                    JSONObject dataob1 = new JSONObject(response);
                    String status = dataob1.getString("status");
                    String message = dataob1.getString("message");
                    if (status.equals("200")) {
                        JSONArray leaveArray = dataob1.getJSONArray("Leave");
                        for (int i = 0;i<leaveArray.length();i++) {
                            JSONObject leavedata = leaveArray.getJSONObject(i);
                            LeaveStatusModel leaveStatusModel = new LeaveStatusModel();
                            String  Leave_Reason = leavedata.getString("Leave_Reason");
                            String  From_Date = leavedata.getString("From_Date");
                            String  To_Date = leavedata.getString("To_Date");
                            String  Status = leavedata.getString("Status");
                            leaveStatusModel.setLeave_Reason(Leave_Reason);
                            leaveStatusModel.setFrom_Date(From_Date);
                            leaveStatusModel.setTo_Date(To_Date);
                            leaveStatusModel.setStatus(Status);
                            leaveStatusModels.add(leaveStatusModel);
                        }


                        LeaveStatusAdapter yearlyPercentageAdapter = new LeaveStatusAdapter(LeaveStatusActivity.this,leaveStatusModels);
                        rv_LeaveStatus.setAdapter(yearlyPercentageAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LeaveStatusActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }
}