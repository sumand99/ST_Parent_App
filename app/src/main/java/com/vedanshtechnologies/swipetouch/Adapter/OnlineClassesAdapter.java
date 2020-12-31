package com.vedanshtechnologies.swipetouch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedanshtechnologies.swipetouch.ModelClass.LeaveStatusModel;
import com.vedanshtechnologies.swipetouch.ModelClass.OnlineClassesModel;
import com.vedanshtechnologies.swipetouch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OnlineClassesAdapter extends RecyclerView.Adapter<OnlineClassesAdapter.ClassViewHolder> {
    Context context;
    ArrayList<OnlineClassesModel> onlineClassesModelArrayList;
    String Current_date;
    String MyCurrentdate;
    OnlineClassesAdapter onlineClassesAdapter ;
    public OnlineClassesAdapter(Context context, ArrayList<OnlineClassesModel> onlineClassesModels) {
        this.context = context;
        this.onlineClassesModelArrayList = onlineClassesModels;
        Date cDate = new Date();
        Current_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        SimpleDateFormat your_format = new SimpleDateFormat("dd-MM-yyyy");
         MyCurrentdate = your_format.format(cDate);
        Log.e("","curentdate:-"+MyCurrentdate);
    }

    @NonNull
    @Override
    public OnlineClassesAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.onlineclasses,null);
        return new OnlineClassesAdapter.ClassViewHolder (view);

    }

    @Override
    public void onBindViewHolder(@NonNull OnlineClassesAdapter.ClassViewHolder holder, int position) {
        final OnlineClassesModel onlineClassesModel = onlineClassesModelArrayList.get(position);
        Log.e("","cutrrendate_"+MyCurrentdate);
        Log.e("","modeldate"+onlineClassesModel.class_Start_date);
        if(onlineClassesModel.class_Start_date.compareTo(MyCurrentdate)<0){
            Log.e("","is_inn_true");
            //holder.ll_view.setVisibility(View.GONE);
        }else{
                holder.iv_topicname.setText(onlineClassesModel.getTopic());
                holder.iv_classendtime.setText(onlineClassesModel.getClass_end_date_time());
                holder.iv_classstarttime.setText(onlineClassesModel.getClass_start_date_time());
                holder.tv_subjectname.setText(onlineClassesModel.getSubject());
                holder.tv_teachername.setText(onlineClassesModel.getTeacher());
                holder.tv_description.setText(onlineClassesModel.getDescription());
              if(onlineClassesModel.getIs_blocked().equals("true")){
                  holder.ll_tvlayout.setVisibility(View.VISIBLE);
                holder.buttonjoin.setVisibility(View.GONE);
               }else {
                  holder.ll_tvlayout.setVisibility(View.GONE);
                  Log.e("","is_in_false");
                  holder.buttonlay.setVisibility(View.VISIBLE);
                  holder.buttonjoin.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          String url = onlineClassesModel.getMeeting_URL();
                          Intent i = new Intent(Intent.ACTION_VIEW);
                          i.setData(Uri.parse(url));
                          context.startActivity(i);
                      }
                  });

              }


        }



    }



    @Override
    public int getItemCount() {
        return onlineClassesModelArrayList.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        Button buttonjoin;
        RelativeLayout ll_view;
        LinearLayout buttonlay,ll_tvlayout;
        TextView tv_subjectname,tv_teachername,iv_topicname,iv_classstarttime,iv_classendtime,tv_description,tv_blocked_status;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonjoin = itemView.findViewById(R.id.buttonjoin);
            tv_subjectname = itemView.findViewById(R.id.tv_subjectname);
            iv_topicname = itemView.findViewById(R.id.iv_topicname);
            tv_teachername = itemView.findViewById(R.id.tv_teachername);
            iv_classstarttime = itemView.findViewById(R.id.iv_classstarttime);
            iv_classendtime = itemView.findViewById(R.id.iv_classendtime);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_blocked_status = itemView.findViewById(R.id.tv_blocked_status);
            ll_view = itemView.findViewById(R.id.ll_view);
            buttonlay = itemView.findViewById(R.id.buttonlay);
            ll_tvlayout = itemView.findViewById(R.id.ll_tvlayout);

        }
    }

   /* public void removeAt(int position) {
        onlineClassesModelArrayList.remove(position);

      notifyItemRemoved(position);
      notifyItemRangeChanged(position, peopleListUser.size());
    }*/
}
