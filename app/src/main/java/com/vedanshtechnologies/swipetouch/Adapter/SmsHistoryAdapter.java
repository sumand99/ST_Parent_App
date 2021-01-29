package com.swipetouch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.ModelClass.SmshistoryModelClass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class SmsHistoryAdapter extends RecyclerView.Adapter<SmsHistoryAdapter.smsViehHolder> {


    Context context;
    ArrayList<SmshistoryModelClass>smshistoryModelClasses ;

    public SmsHistoryAdapter(Context context, ArrayList<SmshistoryModelClass> smshistoryModelClasses) {
        this.context = context;
        this.smshistoryModelClasses = smshistoryModelClasses;
    }

    @NonNull
    @Override
    public SmsHistoryAdapter.smsViehHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.smshistorylist,null);
        return new SmsHistoryAdapter.smsViehHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsHistoryAdapter.smsViehHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class smsViehHolder extends RecyclerView.ViewHolder {
        public smsViehHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
