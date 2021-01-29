package com.swipetouch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.Activities.NoticeDetailActivity;
import com.swipetouch.ModelClass.NoticeModelClass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class NoticesAdapter  extends RecyclerView.Adapter<NoticesAdapter.NoticeViewHolder> {
    Context context;
   ArrayList<NoticeModelClass>noticeModelClasses;
    public NoticesAdapter(Context context, ArrayList<NoticeModelClass> noticeModelClasses) {
        this.context = context;
        this.noticeModelClasses = noticeModelClasses;
    }
    @NonNull
    @Override
    public NoticesAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.noticelist,null);
        return new NoticesAdapter.NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticesAdapter.NoticeViewHolder holder, int position) {
        final NoticeModelClass noticeModelClass = noticeModelClasses.get(position);
        holder.iv_noticetitle.setText(noticeModelClass.getTitle());
        holder.tv_date.setText(noticeModelClass.getNotice_date());
        holder.tv_titledetail.setText(noticeModelClass.getTitle());
        holder.ll_noticeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoticeDetailActivity.class);
                Log.e("","notice_id"+noticeModelClass.getNotice_id());
                intent.putExtra("notice_id",noticeModelClass.getNotice_id());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeModelClasses.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView iv_noticetitle,tv_date,tv_titledetail;
        RelativeLayout ll_noticeid;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_noticetitle= itemView.findViewById(R.id.iv_noticetitle);
            tv_date= itemView.findViewById(R.id.tv_date);
            tv_titledetail= itemView.findViewById(R.id.tv_titledetail);
            ll_noticeid= itemView.findViewById(R.id.ll_noticeid);
        }
    }
}
