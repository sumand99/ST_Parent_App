package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.swipetouch.Adapter.HomeWorkAdapter;
import com.swipetouch.ModelClass.HomeWorkModel;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class HomeWorkActivity extends AppCompatActivity {
RecyclerView  rv_HomeWorks;
ImageView backicon;
ArrayList<HomeWorkModel>homeWorkModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        rv_HomeWorks = findViewById(R.id.rv_HomeWorks);
        backicon = findViewById(R.id.backicon);


        rv_HomeWorks.setHasFixedSize(true);
        rv_HomeWorks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(HomeWorkActivity.this, homeWorkModels);
        homeWorkAdapter.notifyDataSetChanged();
        rv_HomeWorks.setAdapter(homeWorkAdapter);

       /* rv_HomeWorks.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeWorkActivity.this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        rv_HomeWorks.setLayoutManager(gridLayoutManager);

        HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(HomeWorkActivity.this, homeWorkModels);
        rv_HomeWorks.setAdapter(homeWorkAdapter);*/

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}