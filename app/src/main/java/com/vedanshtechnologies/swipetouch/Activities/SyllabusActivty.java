package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swipetouch.Adapter.HomeWorkAdapter;
import com.swipetouch.Adapter.SyllabusAdapter;
import com.swipetouch.ModelClass.SyllabusModelclass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class SyllabusActivty extends AppCompatActivity {

    RecyclerView rv_syllabus;
    ImageView backicon;
    ArrayList<SyllabusModelclass>syllabusModelclasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_activty);
        rv_syllabus=  findViewById(R.id.rv_syllabus);
        backicon = findViewById(R.id.backicon);

        rv_syllabus.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SyllabusActivty.this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        rv_syllabus.setLayoutManager(gridLayoutManager);

        SyllabusAdapter syllabusAdapter = new SyllabusAdapter(SyllabusActivty.this, syllabusModelclasses);
        rv_syllabus.setAdapter(syllabusAdapter);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}