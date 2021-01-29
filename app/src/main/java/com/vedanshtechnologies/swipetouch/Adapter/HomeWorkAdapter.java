package com.swipetouch.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.ModelClass.HomeWorkModel;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.HomeworkViewHolder> {


    Context context;
    ArrayList<HomeWorkModel>homeWorkModels;

    public HomeWorkAdapter(Context context, ArrayList<HomeWorkModel> homeWorkModels) {
        this.context = context;
        this.homeWorkModels = homeWorkModels;
    }
    @NonNull
    @Override
    public HomeWorkAdapter.HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.maincategorylist,null);
        return new HomeWorkAdapter.HomeworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWorkAdapter.HomeworkViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class HomeworkViewHolder extends RecyclerView.ViewHolder {

        TextView tv_homwworkdetail,tv_date,iv_language;
        public HomeworkViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_language = itemView.findViewById(R.id.iv_language);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_homwworkdetail = itemView.findViewById(R.id.tv_homwworkdetail);
        }
    }
}
