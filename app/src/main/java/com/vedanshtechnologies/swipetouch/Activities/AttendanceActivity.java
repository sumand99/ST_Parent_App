package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vedanshtechnologies.swipetouch.R;

public class AttendanceActivity extends AppCompatActivity {
    ImageView backicon;
    LinearLayout ll_leaveApplication,ll_monthlyattendance,ll_yearlyPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        backicon = findViewById(R.id.backicon);
        ll_leaveApplication = findViewById(R.id.ll_leaveApplication);
        ll_yearlyPercentage = findViewById(R.id.ll_yearlyPercentage);
        ll_monthlyattendance = findViewById(R.id.ll_monthlyattendance);
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_leaveApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this,LeaveApplicationActivity.class));
            }
        });

        ll_yearlyPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this,CustomYearlyPercents.class));
            }
        });

        ll_monthlyattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this,customcalender.class));
            }
        });
    }
}