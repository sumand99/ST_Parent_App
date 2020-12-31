package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.vedanshtechnologies.swipetouch.R;
import com.vedanshtechnologies.swipetouch.UtilityTools.Const;
import com.vedanshtechnologies.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    LinearLayout btnlaypot;
    Button loginbtn;
    ProgressDialog progressDialog;
    EditText et_user_id,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlaypot = findViewById(R.id.btnlaypot);
        et_user_id = findViewById(R.id.et_user_id);
        et_password = findViewById(R.id.et_password);
        loginbtn = findViewById(R.id.loginbtn);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);
        String student_Id=pref.getString("student_Id","");
        Log.e("","student_Id"+student_Id);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        if(!student_Id.isEmpty()){
            startActivity(intent);
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkUtil.isNetworkAvailable(LoginActivity.this)){
                    Log.e("","is_true");
                    UserLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
                    Log.e("","is_in_false");
                }

            }
        });
    }

    private void UserLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.UserLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("", "Login_Responce" + response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("200")) {
                        String message = jsonObject.getString("message");
                        String student_Id = jsonObject.getString("student_Id");
                        String student_name = jsonObject.getString("student_name");
                        String student_class = jsonObject.getString("class");
                        String section = jsonObject.getString("section");
                        String roll_no = jsonObject.getString("roll_no");
                        String image = jsonObject.getString("image");

                        SharedPreferences sharedPreferences=getSharedPreferences("Student_Login_Data",MODE_PRIVATE) ;////Store Student Login Data in Shared Prefrence
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("student_Id",student_Id);
                        editor.putString("student_class",student_class);
                        editor.putString("section",section);
                        editor.putString("student_name",student_name);
                        editor.putString("student_class",student_class);
                        editor.putString("roll_no",roll_no);
                        editor.putString("image",image);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);///intent to home
                        startActivity(intent);



                    } else {
                        Toast.makeText(LoginActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
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
                HashMap<String, String> params = new HashMap<String, String>();
            /*    params.put("otp", otp);
                params.put("user_id",id);*/

                Log.e("", "otp_varification_params" + params);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}