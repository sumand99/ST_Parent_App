
package com.vedanshtechnologies.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vedanshtechnologies.swipetouch.Adapter.HolidayAdapter;
import com.vedanshtechnologies.swipetouch.Adapter.SmsHistoryAdapter;
import com.vedanshtechnologies.swipetouch.ModelClass.HolidayModelClass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class HolidayActivity extends AppCompatActivity {
     RecyclerView rv_holiday;
     ImageView backicon;
     ArrayList<HolidayModelClass>holidayModelClasses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        rv_holiday = findViewById(R.id.rv_holiday);

        backicon = findViewById(R.id.backicon);
        rv_holiday.setHasFixedSize(true);
        rv_holiday.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        HolidayAdapter smsHistoryAdapter = new HolidayAdapter(HolidayActivity.this, holidayModelClasses);
        smsHistoryAdapter.notifyDataSetChanged();
        rv_holiday.setAdapter(smsHistoryAdapter);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}