package com.example.san.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.myapplication.Caland.CaldroidSampleActivity;
import com.example.san.myapplication.Caland.CalendarEventsActivity;
import com.example.san.myapplication.Module.Attendance;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    Button btn_logout,btn_events;

    public static String PREFS_NAME = "remainderPref";

 //   @InjectView(R.id.btn_attend) Button btnAttendance;
    @InjectView(R.id.institute_name) TextView institute;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        ButterKnife.inject(this);
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        /*String institute_name = pref.getString("MI_NAME", null);

        if(institute_name != null) {
            institute.setText(institute_name);
        }*/

        /*btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SharedPreferences sharedPrefs = getSharedPreferences(LoginActivity.PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.clear();
                editor.commit();
              //  user="";

                //show login form
                finish();
                Toast.makeText(HomeActivity.this, "Loged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, LoginActivityNew.class);
                startActivity(intent);

             }

        });*/


        /*findViewById(R.id.imagebuttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(HomeActivity.this, "Loged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, LoginActivityNew.class);
                startActivity(intent);
            }
        });*/

        findViewById(R.id.btn_stud_det).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,StudentDeatilsActivity.class));
              //  finish();
            }
        });

        findViewById(R.id.btn_fees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,FeesActivityNew1.class));
             //   finish();
            }
        });

        findViewById(R.id.btn_attend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(HomeActivity.this,AttendanceActivity.class));
             //   finish();
//                startActivity(new Intent(HomeActivity.this,CaldroidSampleActivity.class));
                startActivity(new Intent(HomeActivity.this, CaldroidSampleActivity.class));
            }
        });

        findViewById(R.id.btn_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CalendarEventsActivity.class));
            }
        });

        findViewById(R.id.btn_exam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ExamActivityNew.class));
            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(HomeActivity.this, "Loged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, LoginActivityNew.class);
                startActivity(intent);
            }
        });

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                finish();
                Toast.makeText(HomeActivity.this, "Loged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, LoginActivityNew.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }*/

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }




}
