package com.vedanshtechnologies.swipetouch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedanshtechnologies.swipetouch.Activities.LeaveStatusActivity;
import com.vedanshtechnologies.swipetouch.ModelClass.LeaveStatusModel;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusAdapter.LeaveViewholder> {

    Context context;
   ArrayList<LeaveStatusModel>leaveStatusActivities;
    public LeaveStatusAdapter(Context context, ArrayList<LeaveStatusModel> leaveStatusActivities) {
        this.context = context;
        this.leaveStatusActivities = leaveStatusActivities;
    }

    @NonNull
    @Override
    public LeaveStatusAdapter.LeaveViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.leavestauss,null);
        return new LeaveStatusAdapter.LeaveViewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveStatusAdapter.LeaveViewholder holder, int position) {
        LeaveStatusModel leaveStatusModel = leaveStatusActivities.get(position);
        holder.iv_fromdate.setText(leaveStatusModel.getFrom_Date());
        holder.tv_todate.setText(leaveStatusModel.getTo_Date());
        holder.tv_leavereason.setText(leaveStatusModel.getLeave_Reason());
        holder.tv_remark.setText(leaveStatusModel.getStatus());
    }

    @Override
    public int getItemCount() {
        return leaveStatusActivities.size();
    }

    public class LeaveViewholder extends RecyclerView.ViewHolder {
        TextView tv_todate,iv_fromdate,tv_leavereason,tv_remark;
        public LeaveViewholder(@NonNull View itemView) {
            super(itemView);
            tv_todate= itemView.findViewById(R.id.tv_todate);
            iv_fromdate= itemView.findViewById(R.id.iv_fromdate);
            tv_leavereason= itemView.findViewById(R.id.tv_leavereason);
            tv_remark= itemView.findViewById(R.id.tv_remark);
        }
    }
}
