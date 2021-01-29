package com.swipetouch.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.ModelClass.SyllabusModelclass;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;
import java.util.Random;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.syallabusviehHolder> {


    Context context;
    ArrayList<SyllabusModelclass>syllabusModelclasses ;

    public SyllabusAdapter(Context context, ArrayList<SyllabusModelclass> syllabusModelclasses) {
        this.context = context;
        this.syllabusModelclasses = syllabusModelclasses;
    }

    @NonNull
    @Override
    public SyllabusAdapter.syallabusviehHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.syllabuslist,null);
        return new SyllabusAdapter.syallabusviehHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusAdapter.syallabusviehHolder holder, int position) {

     holder.rl_cardid.setCardBackgroundColor(getRandomColorCode());
     holder.ll_linearlayout.setBackgroundColor(getRandomColorCode());
     holder.rl_cardid.setRadius(20);


    }

    public int getRandomColorCode(){

        Random random = new Random();


        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class syallabusviehHolder extends RecyclerView.ViewHolder {
        ImageView iv_syllabus;
        TextView tv_syllabusname;
        CardView rl_cardid;
        LinearLayout ll_linearlayout;
        public syallabusviehHolder(@NonNull View itemView) {
            super(itemView);
            iv_syllabus = itemView.findViewById(R.id.iv_syllabus);
            tv_syllabusname = itemView.findViewById(R.id.tv_syllabusname);
            rl_cardid = itemView.findViewById(R.id.rl_cardid);
            ll_linearlayout = itemView.findViewById(R.id.ll_linearlayout);
        }
    }
}
