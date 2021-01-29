package com.swipetouch.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.swipetouch.ModelClass.AttendancePercentageModel;
import com.vedanshtechnologies.swipetouch.R;

import java.util.ArrayList;

public class YearlyPercentageAdapter extends RecyclerView.Adapter<YearlyPercentageAdapter.PercentageViehHolder> {


    Context context;
   ArrayList<AttendancePercentageModel>attendancePercentageModels;

    public YearlyPercentageAdapter(Context context, ArrayList<AttendancePercentageModel> attendancePercentageModels) {
        this.context = context;
        this.attendancePercentageModels = attendancePercentageModels;
    }
    @NonNull
    @Override
    public YearlyPercentageAdapter.PercentageViehHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.yearlypercentage,null);
        return new YearlyPercentageAdapter.PercentageViehHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull YearlyPercentageAdapter.PercentageViehHolder holder, int position) {
        AttendancePercentageModel attendancePercentageModel = attendancePercentageModels.get(position);
        int myNum = 0;
        try {
            myNum = Integer.parseInt(attendancePercentageModel.getPercentage());
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if (myNum > 40 && myNum < 70) {
            holder.progress_bar.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
            Log.e("", "is_true_beetween40-70");
        } else if (myNum > 70) {
            holder.progress_bar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
            Log.e("", "is_true_greather70");
        } else if (myNum < 40) {
            holder.progress_bar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            Log.e("", "is_true_lessa40");
        }
        holder.progress_bar.setProgress(myNum);
        holder.percents.setText(attendancePercentageModel.getPercentage()+"%");
    }

    @Override
    public int getItemCount() {
        return attendancePercentageModels.size();
    }

    public class PercentageViehHolder extends RecyclerView.ViewHolder {
        ProgressBar progress_bar;
        TextView percents;
        public PercentageViehHolder(@NonNull View itemView) {
            super(itemView);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            percents = itemView.findViewById(R.id.percents);

        }
    }
}
