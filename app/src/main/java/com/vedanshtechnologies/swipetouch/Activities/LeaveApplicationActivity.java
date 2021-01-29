package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swipetouch.Adapter.YearlyPercentageAdapter;
import com.swipetouch.ModelClass.AttendancePercentageModel;
import com.vedanshtechnologies.swipetouch.R;
import com.swipetouch.UtilityTools.Const;
import com.swipetouch.UtilityTools.KeyboardUtils;
import com.swipetouch.UtilityTools.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeaveApplicationActivity extends AppCompatActivity {
    EditText et_fromdate,et_todate;
    TextView tv_leavestatus;
    DateFormat dateFormatter;
    public static EditText _tv_fromdate;
    public static EditText _tv_Todate;
    String Fromdate;
    String Student_id;
    int year,month,day;
    EditText et_remark_id;
    Spinner sp_leavereason;
    String Current_date;
    String From_date,To_date,Remark;
    String SelectedReason;
    Button submit_button;
    ImageView backicon;
    ProgressDialog progressDialog;
    ImageView to_date_cal_picer,from_date_calpicker;
    ArrayList<String> StringsReasons = new ArrayList<>();
    ArrayList<String> IDReason = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);
        _tv_fromdate =findViewById(R.id._tv_fromdate);
        _tv_Todate =findViewById(R.id._tv_Todate);
        tv_leavestatus =findViewById(R.id.tv_leavestatus);
        et_remark_id =findViewById(R.id.et_remark_id);
        submit_button =findViewById(R.id.submit_button);
        sp_leavereason =findViewById(R.id.sp_leavereason);
        from_date_calpicker =findViewById(R.id.from_date_calpicker);
        to_date_cal_picer =findViewById(R.id.to_date_cal_picer);
        backicon =findViewById(R.id.backicon);
        _tv_fromdate.setInputType(InputType.TYPE_NULL);
        _tv_Todate.setInputType(InputType.TYPE_NULL);

        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
         Student_id=pref.getString("student_Id","");

        progressDialog = new ProgressDialog(LeaveApplicationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Date cDate = new Date();
        Current_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        Log.e("","curentdate:-"+Current_date);
        if(NetworkUtil.isNetworkAvailable(LeaveApplicationActivity.this)){
            Log.e("","is_true");
            get_LeaveReasons();
        }else{
            Toast.makeText(LeaveApplicationActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
            Log.e("","is_in_false");
        }

        SimpleDateFormat your_format = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = your_format.format(cDate);
        _tv_fromdate.setText(date2);
        _tv_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        tv_leavestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaveApplicationActivity.this,LeaveStatusActivity.class));
            }
        });
        _tv_Todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDatePickerDialog(view);
            }
        });



        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               From_date = _tv_fromdate.getText().toString();
                To_date = _tv_Todate.getText().toString();
                Remark = et_remark_id.getText().toString();
                isDateAfter(From_date,To_date);
                if(From_date.equals("")){
                    _tv_fromdate.setError("fill From date");
                    _tv_fromdate.setFocusable(true);
                }else if(To_date.equals("")){
                    _tv_fromdate.setFocusable(true);
                    _tv_Todate.setError("fill To date");
                }else if(SelectedReason.equals("Select Reason")){
                    Toast.makeText(LeaveApplicationActivity.this, "Please Select Leave Reason", Toast.LENGTH_SHORT).show();
                }else {
                    if(NetworkUtil.isNetworkAvailable(LeaveApplicationActivity.this)){
                        Log.e("","is_true");
                        SubmitLeaveApplication();
                    }else{
                        Toast.makeText(LeaveApplicationActivity.this, "Please Cheak Your Internet Connection", Toast.LENGTH_SHORT).show();
                        Log.e("","is_in_false");
                    }

                }

            }
        });

    }

    private void showToDatePickerDialog(View view) {
        DialogFragment newFragment = new ToDatePickerFragment ();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showTruitonDatePickerDialog(View view) {
            DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            Date cDate = new Date();
            String Current_date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
            Log.e("","curentdate_dec:-"+Current_date);
            SimpleDateFormat your_format = new SimpleDateFormat("dd/MM/yyyy");
            String date2 = your_format.format(cDate);
            String getfrom[] = date2.split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1])-1;
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year,month,day);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year,month,day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            _tv_fromdate.setText(day + "/" + (month+1)  + "/" + year);
            _tv_Todate.setText("");

        }

    }
    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            String getfromdate = _tv_fromdate.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1])-1;
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year,month,day);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year,month,day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            _tv_Todate.setText(day + "/" + (month+1)  + "/" + year);
        }
    }
    public static boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "yyyy-M-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }

    private void SubmitLeaveApplication() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.submitleave, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("","leavesubmitrespone"+response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if(status.equals("200")){
                        final Dialog dialog1 = new Dialog(LeaveApplicationActivity.this,R.style.dialogstyle); // Context, this, etc.
                        dialog1.setContentView(R.layout.leavesucess);
                        Button yesbutton = dialog1.findViewById(R.id.yesbutton);
                        Button nobutton = dialog1.findViewById(R.id.nobutton);
                        yesbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.hide();
                                startActivity(new Intent(LeaveApplicationActivity.this,LeaveApplicationActivity.class));
                            }
                        });
                        dialog1.show();

                    }else{
                        final Dialog dialog2 = new Dialog(LeaveApplicationActivity.this,R.style.dialogstyle); // Context, this, etc.
                        dialog2.setContentView(R.layout.leavefailds);
                        Button yesbutton = dialog2.findViewById(R.id.yesbutton);
                        yesbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.hide();
                                startActivity(new Intent(LeaveApplicationActivity.this,LeaveApplicationActivity.class));
                            }
                        });
                        dialog2.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "responce_ec" + error);

            }
        })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("from_date", From_date);
                params.put("to_date", To_date);
                params.put("leave_reason", SelectedReason);
                params.put("remarks", Remark);

                Log.e("", "leave_submit_param" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LeaveApplicationActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private void get_LeaveReasons() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL.LeaveReasons, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("", "all_states_responce" + response);
                response ="{\n" +
                        "    \"status\":\"200\",\n" +
                        "    \"message\":\"success\",\n" +
                        "    \"Leave_Reason\": [\n" +
                        "        {\n" +
                        "            \"leave_reason_id\": \"1\",\n" +
                        "            \"leave_reason\": \"Sick\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"leave_reason_id\": \"2\",\n" +
                        "            \"leave_reason\": \"Family Function\"\n" +
                        "        },\n" +
                        "       {\n" +
                        "            \"leave_reason_id\": \"3\",\n" +
                        "            \"leave_reason\": \"Bereavement\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"leave_reason_id\": \"4\",\n" +
                        "            \"leave_reason\": \"Other\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("200")) {
                        IDReason.clear();
                        StringsReasons.clear();
                        IDReason.add("");
                        StringsReasons.add("Select Reason");
                        JSONArray alldata = jsonObject.getJSONArray("Leave_Reason");
                        for (int i = 0; i < alldata.length(); i++) {
                            JSONObject dataobj = alldata.getJSONObject(i);

                          String leave_reason_id = dataobj.getString("leave_reason_id");
                          String leave_reason = dataobj.getString("leave_reason");

                            IDReason.add(leave_reason_id);
                            StringsReasons.add(leave_reason);


                        }

                        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(LeaveApplicationActivity.this,
                               R.layout.item_spinner_viewnew, StringsReasons);
                        spnAdapter.setDropDownViewResource(R.layout.item_spinner_view);
                        sp_leavereason.setAdapter(spnAdapter);
                        sp_leavereason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    Log.e("", "position" + position);
                                    SelectedReason = "Select Reason";
                                }else{
                                    SelectedReason = StringsReasons.get(position);
                                    Log.e("", "Reasonelsse" + SelectedReason);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        sp_leavereason.setSelection(StringsReasons.indexOf(StringsReasons));

                        Log.e("","product_sting"+StringsReasons.indexOf(StringsReasons));


                    } else {
                        Toast.makeText(LeaveApplicationActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("", "respoce_exception" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "responce_ec" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("StudentId",Student_id);
                Log.e("","Students_id"+params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LeaveApplicationActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}