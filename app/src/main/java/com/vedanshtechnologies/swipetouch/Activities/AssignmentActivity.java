package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vedanshtechnologies.swipetouch.R;

public class AssignmentActivity extends AppCompatActivity {

   LinearLayout ll_attendance,ll_activity,ll_project;
   ImageView backicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        ll_attendance =findViewById(R.id.ll_attendance);
        ll_activity =findViewById(R.id.ll_activity);
        ll_project =findViewById(R.id.ll_project);
        backicon =findViewById(R.id.backicon);

        ll_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssignmentActivity.this,HomeWorkActivity.class));
            }
        }); backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

        ll_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssignmentActivity.this,ProjectsActivity.class));
            }
        });ll_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssignmentActivity.this,ActivitiesActivity.class));
            }
        });
    }
}