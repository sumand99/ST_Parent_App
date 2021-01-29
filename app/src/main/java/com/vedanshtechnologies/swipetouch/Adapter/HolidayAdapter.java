package com.swipetouch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.ModelClass.HolidayModelClass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.holidayViewHolder> {


    Context context;
    ArrayList<HolidayModelClass>holidayModelClasses;

    public HolidayAdapter(Context context, ArrayList<HolidayModelClass> holidayModelClasses) {
        this.context = context;
        this.holidayModelClasses = holidayModelClasses;
    }

    @NonNull
    @Override
    public HolidayAdapter.holidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.holidaylist,null);
        return new HolidayAdapter.holidayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayAdapter.holidayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class holidayViewHolder extends RecyclerView.ViewHolder {
        TextView tv_month,tv_date,tv_holiday;
        public holidayViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date= itemView.findViewById(R.id.tv_date);
            tv_month= itemView.findViewById(R.id.tv_month);
            tv_holiday= itemView.findViewById(R.id.tv_holiday);

        }
    }
}
