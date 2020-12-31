package com.vedanshtechnologies.swipetouch.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vedanshtechnologies.swipetouch.Activities.customcalender;
import com.vedanshtechnologies.swipetouch.ModelClass.Attendance;
import com.vedanshtechnologies.swipetouch.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;

    private java.util.Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    ArrayList<Attendance> absModelArrayList;
    private ArrayList items;
    private String mVisibility;
    public static List dayString;
    private View previousView;

    public CalendarAdapter(Context context, ArrayList<Attendance> items, String mVisibility) {
        this.mContext = context;
        this.items = items;
        this.mVisibility = mVisibility;
        CalenderAdapterView.dayString = new ArrayList();
    }


    public CalendarAdapter(Context c, GregorianCalendar monthCalendar) {
        CalendarAdapter.dayString = new ArrayList();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        System.out.println(":::::::::::::::::::::MONTH CALENDAR:::::::::::::::"
                + monthCalendar);
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        System.out
                .println(":::::::::::::::::CURRENT DATE STRING::::::::::::::;"
                        + curentDateString);
        refreshDays();
    }

    public CalendarAdapter(customcalender customcalender, ArrayList<Attendance> studentPreAbsModels, String s) {
        this.mContext = customcalender;
        this.items = items;
        this.absModelArrayList = studentPreAbsModels;
        this.mVisibility = s;
        CalenderAdapterView.dayString = new ArrayList();
    }

    public void setItems(ArrayList items) {
        for (int i = 0; i != items.size(); i++) {
      /* if (items.get(i).length() == 1) {
        items.set(i, "0" + items.get(i));
       }*/
        }
        this.items = items;
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        //StudentPreAbsModel studentPreAbsModel = absModelArrayList.get(position);
        View v = convertView;
        TextView dayView;
        RelativeLayout ll_item;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        ll_item = (RelativeLayout) v.findViewById(R.id.ll_items);
        // separates daystring into parts.
        String selectedGridDate = (String) CalendarAdapter.dayString
                .get(position);
        Log.e("","dates"+selectedGridDate);
        String separatedTime = (String) dayString.get(position);
        String[] slegrid = separatedTime.split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = slegrid[0].replaceFirst("^0*", "");

        Log.e("", "grid_value" + gridvalue);
        Log.e("", "model_data" + gridvalue);

        //Log.e("", "datamodel" + studentPreAbsModel.day);

        dayView.setText(gridvalue);
        // create date string for comparison
        String date = (String) dayString.get(position);




        if (date.length() == 1) {
            date = "0" + date;
        }
        System.out
                .println(":::::::::::::::::::::::ADPTER SPLITTED MONTH:::::::::::::::::::::"
                        + slegrid[1]);





        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            Log.e("","is_in_thsi");


            dayView.setText("");
            dayView.setTextColor(Color.parseColor("#CECECE"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {

            dayView.setTextColor(Color.parseColor("#CECECE"));
            dayView.setClickable(false);
            dayView.setText("");
            dayView.setFocusable(false);
        }

        if (dayString.get(position).equals(curentDateString)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);
        }




        return v;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        previousView = view;
      //  view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        Log.e("","month_Startday"+pmonth);
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pmonthmaxset.getTime());
            Log.e("","item_Values"+itemvalue);
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

}
