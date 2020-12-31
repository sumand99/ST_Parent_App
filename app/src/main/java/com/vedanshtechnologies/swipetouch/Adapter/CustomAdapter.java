package com.vedanshtechnologies.swipetouch.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.vedanshtechnologies.swipetouch.R;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context c;
    private String[] date ;
    private LayoutInflater inflater;

    public CustomAdapter(Context context,String[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.c = context;
        date = objects;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return date.length;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.calendar_item,parent,false);
            textView = new TextView(c);
            textView.setLayoutParams(new GridView.LayoutParams(120,120));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5,5,5,5);
        }else{
            textView = (TextView) convertView;
        }

        textView.setText(date[position]);


        return textView;

    }
}