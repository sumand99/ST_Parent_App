package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.swipetouch.Adapter.SmsHistoryAdapter;
import com.swipetouch.Adapter.SyllabusAdapter;
import com.swipetouch.ModelClass.SmshistoryModelClass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class SmsActivity extends AppCompatActivity {
    ImageView backicon;
    RecyclerView rv_smshostory;
    ArrayList<SmshistoryModelClass>smshistoryModelClasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        backicon = findViewById(R.id.backicon);
        rv_smshostory = findViewById(R.id.rv_smshostory);

        rv_smshostory.setHasFixedSize(true);
        rv_smshostory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        SmsHistoryAdapter smsHistoryAdapter = new SmsHistoryAdapter(SmsActivity.this, smshistoryModelClasses);
        smsHistoryAdapter.notifyDataSetChanged();
        rv_smshostory.setAdapter(smsHistoryAdapter);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}