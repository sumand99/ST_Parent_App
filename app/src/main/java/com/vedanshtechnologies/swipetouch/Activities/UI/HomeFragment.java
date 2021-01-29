package com.swipetouch.Activities.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.swipetouch.Activities.AcadmicfeedbackActivity;
import com.swipetouch.Activities.AssignmentActivity;
import com.swipetouch.Activities.AttendanceActivity;
import com.swipetouch.Activities.BirthdayActivity;
import com.swipetouch.Activities.CelenderActivity;
import com.swipetouch.Activities.ENoticeActivity;
import com.swipetouch.Activities.ExamReportActivity;
import com.swipetouch.Activities.FeesActivity;
import com.swipetouch.Activities.LibraryActivity;
import com.swipetouch.Activities.MainCalenderActivity;
import com.swipetouch.Activities.MyTeacherActivity;
import com.swipetouch.Activities.NewsEventActivity;
import com.swipetouch.Activities.OnlineClassesActivity;
import com.swipetouch.Activities.OnlineTestActivity;
import com.swipetouch.Activities.PersonalDetailActivity;
import com.swipetouch.Activities.SettingActivity;
import com.swipetouch.Activities.SmsActivity;
import com.swipetouch.Activities.SyllabusActivty;
import com.swipetouch.Activities.TimeTable;
import com.swipetouch.Activities.TransportActivity;
import com.swipetouch.Activities.WriteToSchoolActivity;
import com.vedanshtechnologies.swipetouch.R;


public class HomeFragment extends Fragment {

LinearLayout ll_attendance,ll_assignment,ll_Notice,ll_fees,ll_library,ll_examreport,ll_transport,ll_onlineclass,
        ll_onlinetest,ll_personaldetail,ll_myteacher,ll_acadmic_report,ll_feedback_acadmic;
LinearLayout ll_writeschool,ll_feedacadmic,ll_calender,ll_timetable,
        ll_syllabus,ll_smshistory,ll_newaevent,birthday,ll_search;
ImageView searchimg;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        ll_attendance = view.findViewById(R.id.ll_attendance);
        searchimg = view.findViewById(R.id.searchimg);
        ll_search = view.findViewById(R.id.ll_search);
        ll_assignment = view.findViewById(R.id.ll_assignment);
        ll_library = view.findViewById(R.id.ll_library);
        ll_Notice = view.findViewById(R.id.ll_Notice);
        ll_examreport = view.findViewById(R.id.ll_examreport);
        ll_syllabus = view.findViewById(R.id.ll_syllabus);
        ll_myteacher = view.findViewById(R.id.ll_myteacher);
        ll_smshistory = view.findViewById(R.id.ll_smshistory);
        ll_fees = view.findViewById(R.id.ll_fees);
        ll_calender = view.findViewById(R.id.ll_calender);
        ll_newaevent = view.findViewById(R.id.ll_newaevent);
        ll_personaldetail = view.findViewById(R.id.ll_personaldetail);
        ll_writeschool = view.findViewById(R.id.ll_writeschool);
        ll_transport = view.findViewById(R.id.ll_transport);
        birthday = view.findViewById(R.id.birthday);
        ll_onlineclass = view.findViewById(R.id.ll_onlineclass);
        ll_feedacadmic = view.findViewById(R.id.ll_feedacadmic);
        ll_timetable = view.findViewById(R.id.ll_timetable);
        ll_acadmic_report = view.findViewById(R.id.ll_acadmic_report);
        ll_onlinetest = view.findViewById(R.id.ll_onlinetest);
        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_search.setVisibility(View.VISIBLE);
                searchimg.setVisibility(View.GONE);
            }
        });
        ll_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AttendanceActivity.class));
            }
        }); ll_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AssignmentActivity.class));
            }
        }); ll_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ENoticeActivity.class));
            }
        });ll_fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FeesActivity.class));
            }
        });ll_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LibraryActivity.class));
            }
        });ll_examreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ExamReportActivity.class));
            }
        });ll_transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TransportActivity.class));
            }
        });ll_onlinetest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OnlineTestActivity.class));
            }
        });ll_acadmic_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });ll_personaldetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PersonalDetailActivity.class));
            }
        });ll_myteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyTeacherActivity.class));
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BirthdayActivity.class));
            }
        });
        ll_onlineclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OnlineClassesActivity.class));
            }
        });
        ll_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TimeTable.class));
            }
        });

        ll_newaevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewsEventActivity.class));
            }
        });
        ll_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainCalenderActivity.class));
            }
        });ll_smshistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SmsActivity.class));
            }
        });
        ll_syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SyllabusActivty.class));
            }
        });
        ll_writeschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WriteToSchoolActivity.class));
            }
        });


        ll_feedacadmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AcadmicfeedbackActivity.class));
            }
        });




   return  view;
    }
}
