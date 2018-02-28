package com.example.san.myapplication.NewkioaskDesign;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Module.TTDay;
import com.example.san.myapplication.R;
import com.example.san.myapplication.StudentDeatilsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment
{

    private static final String URL = "https://stagingmobileapp.azurewebsites.net/api/login/Timetable";
    public static String TAG = StudentDeatilsActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    public TimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_table, container, false);
        volleyJsonObjectRequest(URL);
        return v;
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectRequest(String url)
    {
        String REQUEST_TAG = "com.timetable.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            /*int ID = Integer.parseInt(response.getString("AMST_Id"));
                            if (ID == 0) {
                                Toast.makeText(getActivity(), "Failed to access student data", Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(StudentDeatilsActivity.this, "got data", Toast.LENGTH_SHORT).show();

                            }*/

                            timeTableDataParse(response);

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

                /*params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");*/


                params.put("ASMCL_Id", 34);
                params.put("ASMS_Id", 20);
                params.put("ASMAY_Id", 1);
                params.put("MI_Id", 6);


                /*params.put("AMST_Id",4221);
                params.put("MI_Id",5);*/

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

    /*******************************************************************************************************/
    /*******************************************************************************************************/

    public void timeTableDataParse(JSONObject jsonObject)
    {
        try {
            JSONArray objects = jsonObject.getJSONArray("TimeTable");


            ArrayList<TTDay> al_weeks = new ArrayList<TTDay>();

            for (int i = 0 ; i < objects.length() ; i++)
            {

                JSONObject weekObject = objects.getJSONObject(i);
                JSONArray dayObjects = weekObject.getJSONArray("TTData");

                for (int j = 0 ; j < dayObjects.length() ; j++)
                {

                    int PeriodName = (int) dayObjects.getJSONObject(i).get("PeriodName");
                    String staffName = (String) dayObjects.getJSONObject(i).get("staffName");
                    String SubjectName = (String) dayObjects.getJSONObject(i).get("SubjectName");

                    TTDay ttDay = new TTDay(PeriodName,staffName,SubjectName);
                    al_weeks.add(ttDay);
                }



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
