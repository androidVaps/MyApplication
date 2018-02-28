package com.example.san.myapplication.NewkioaskDesign;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.AttendanceAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Module.Attendance;
import com.example.san.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragmentNew extends Fragment implements AdapterView.OnItemSelectedListener {

    private static String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentYearlyAttendance";
    private static String TAG = AttendanceFragmentNew.class.getSimpleName();
    ProgressDialog progressDialog;
    AttendanceAdapter attendanceAdapter;
    List<Attendance> attendanceList = new ArrayList<>();
    RecyclerView recyclerView;
    JSONObject jsonAttendanceResponse;
    Spinner spinner_academy_name;

    public AttendanceFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_attendance_new, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recy_attendance);

        spinner_academy_name = (Spinner) v.findViewById(R.id.spinner_academic);

        ImageView img_back_button = (ImageView) v.findViewById(R.id.img_backIcon);

        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });

        String[] spinner0Data = {"Select Academic Year", "2017-18"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_row, spinner0Data);
        aa.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinner_academy_name.setAdapter(aa);
        spinner_academy_name.setOnItemSelectedListener(this);

         volleyJsonObjectAttendanceRequest(URL, 3, 5, 944);



        return v;
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    public void volleyJsonObjectAttendanceRequest(String url, final int ASMAY_Id, final int MI_Id, final int AMST_Id) {
        String REQUEST_TAG = "com.attendance.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("YearlyArray");
                            if (jsonArray.length() == 0) {
                                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                            } else {
                                jsonAttendanceResponse = response;
                            }
                        } catch (Exception e) {
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

                /*params.put("ASMAY_Id", ASMAY_Id);
                params.put("MI_Id", MI_Id);
                params.put("AMST_Id", AMST_Id);*/

                params.put("ASMAY_Id", 3);
                params.put("MI_Id", 5);
                params.put("AMST_Id", 944);

                 /* params.put("ASMAY_Id", 3);
                params.put("MI_Id", 5);*/

                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);

        switch (parent.getId()) {
            case R.id.spinner_academic:

                if (position != 0)
                {
                    if(jsonAttendanceResponse != null) {
                        parseJsonAttendanceData(jsonAttendanceResponse);
                    }else{
                        Toast.makeText(getActivity(), "Please try again !!", Toast.LENGTH_SHORT).show();
                    }
                }
            break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*******************************************************************************************************/

    void parseJsonAttendanceData(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("YearlyArray");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject monthObject = jsonArray.getJSONObject(i);

                String month_id = monthObject.getString("monthid");
                int class_held = Integer.parseInt(monthObject.getString("ClassHeld"));
                int class_attended = Integer.parseInt(monthObject.getString("Class_Attended"));
                int percentage = Integer.parseInt(monthObject.getString("Percentage"));

                Attendance attendance = new Attendance(month_id, class_held, class_attended, percentage);
                attendanceList.add(attendance);
            }

            attendanceAdapter = new AttendanceAdapter(attendanceList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(attendanceAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
