package com.swipetouch.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.vedanshtechnologies.swipetouch.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    Spinner sp_mainspinner;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    TextView log_out_id,sectionid,tv_studentname;
    CircleImageView drawer_profile_image,home_profile_image;
    ArrayList<String> stringsallStates = new ArrayList<>();
    ArrayList<String> idallStates = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //sp_mainspinner = findViewById(R.id.sp_mainspinner);
        drawer_profile_image = findViewById(R.id.drawer_profile_image);
        sectionid = findViewById(R.id.sectionid);
        tv_studentname = findViewById(R.id.tv_studentname);
        home_profile_image = findViewById(R.id.home_profile_image);
        log_out_id = findViewById(R.id.log_out_id);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences("Student_Login_Data", MODE_PRIVATE);///get_data from Shared_Prefrence
        String Student_profileimage=pref.getString("image","");
        String student_name=pref.getString("student_name","");
        String section=pref.getString("section","");
        String student_class=pref.getString("student_class","");
        tv_studentname.setText(student_name);
        sectionid.setText("Class-"+student_class+","+"Sec-"+section+"");

        Picasso.with(HomeActivity.this)
                .load(Student_profileimage)
                .into(home_profile_image);




        log_out_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(HomeActivity.this,R.style.dialogstyle); // Context, this, etc.
                dialog1.setContentView(R.layout.logoutpopup);
                Button yesbutton =dialog1.findViewById(R.id.yesbutton);
                Button Nobutton =dialog1.findViewById(R.id.nobutton);
                yesbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences preferences =getSharedPreferences("Student_Login_Data", MODE_PRIVATE);//Clear Session data
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                });
                Nobutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                   dialog1.hide();
                    }
                });
                dialog1.show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        navigationView.setItemIconTintList(null);
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_logindevice)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     /*   finish();
        System.exit(1);*/

    }
}