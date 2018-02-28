package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.AttendanceAdapter;
import com.example.san.myapplication.Module.Attendance;
import com.example.san.myapplication.Module.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AttendanceActivity extends AppCompatActivity {

    private static String TAG = AttendanceActivity.class.getSimpleName();
    private static String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentAttendance";
    ProgressDialog progressDialog;
    AttendanceAdapter attendanceAdapter;
    List<Attendance> attendanceList = new ArrayList<>();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView1;
    @InjectView(R.id.calendarView) CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);
        ButterKnife.inject(this);

        toolbar.setTitle("Attendance Details");

        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();

        volleyJsonObjectFeesRequest(URL, session.getMI_Id(), session.getAMST_Id(), session.getASMAY_Id());

        attendanceAdapter = new AttendanceAdapter(attendanceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(attendanceAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
           //     startActivity(new Intent(AttendanceActivity.this, HomeActivity.class));
            }
        });

        initializeCalendar();
    }

    private void initializeCalendar()
    {
        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.colorBlueLight));
        calendarView.setUnfocusedMonthDateColor(getResources().getColor(R.color.colorOrangeLight));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        String date = "29/3/2014";
        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();

        calendarView.setDate(milliTime);

        String selectedDate = "23/3/2014";
        try {
            calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime(), true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectFeesRequest(String url, final int mi_id, final int amst_id, final int asmay_id) {
        String REQUEST_TAG = "com.fees.details";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            int ID = response.getInt("amsT_Id");
                            if (ID == 0) {
                                Toast.makeText(AttendanceActivity.this, "Failed to access Attendance data", Toast.LENGTH_SHORT).show();
                            } else {
                                //    Toast.makeText(AttendanceActivity.this, "got data", Toast.LENGTH_SHORT).show();
                                attendaceResponseParse(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            public byte[] getBody() {

                Map<String, Integer> params = new HashMap<String, Integer>();

                params.put("ASMAY_Id", asmay_id);
                params.put("AMST_Id", amst_id);
                params.put("MI_Id", mi_id);

                /*params.put("ASMAY_Id",3);
                params.put("AMST_Id",944);
                params.put("MI_Id",5);*/

                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/

    private void attendaceResponseParse(JSONObject response) {
        String month = null;
        int class_held, class_attended, percentage;

        try {
            int amsT_Id = response.getInt("amsT_Id");
            JSONArray jsonArray = response.getJSONArray("studentAttendanceList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                month = object.getString("ivrM_Month_Name");
                class_held = object.getInt("asA_ClassHeld");
                class_attended = object.getInt("asA_Class_Attended");
                percentage = object.getInt("percentage");

                Attendance attendance = new Attendance(month, class_held, class_attended, percentage);
                attendanceList.add(attendance);

                attendanceAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }
}
