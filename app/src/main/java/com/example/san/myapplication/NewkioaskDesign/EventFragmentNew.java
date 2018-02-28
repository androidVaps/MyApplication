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
import com.example.san.myapplication.Adapters.EventsAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Module.Event;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.R;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragmentNew extends Fragment implements AdapterView.OnItemSelectedListener {

    private static String URL = "http://stagingmobileapp.azurewebsites.net/api/login/CalenderofEvents";
    public EventsAdapter mAdapter;
    String presentDate[];
    String abscenceDate[];
    Session session;
    Date minDate, maxDate;
    Calendar cal;
    int miid;
    int asmay;
    JSONObject JsonCalendarEventsResponse;
    ArrayList<Event> event_al;
    Spinner spinner_academy_name;
    Spinner spinner_month_name;
    RecyclerView recyclerView;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private ProgressDialog progressDialog;
    private String TAG = EventFragmentNew.class.getSimpleName();


    public EventFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_event_new, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recy_event);

        spinner_academy_name = (Spinner) v.findViewById(R.id.spinner_academic);
        spinner_month_name = (Spinner) v.findViewById(R.id.spinner_month);

        String[] spinner0Data = {"Select Academic Year", "2017-18"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_row, spinner0Data);
        aa.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinner_academy_name.setAdapter(aa);
        spinner_academy_name.setOnItemSelectedListener(this);

        String[] spinner1Data = {"Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter aa1 = new ArrayAdapter(getActivity(), R.layout.spinner_row, spinner1Data);
        aa1.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinner_month_name.setAdapter(aa1);
        spinner_month_name.setOnItemSelectedListener(this);

        ImageView img_back_button = (ImageView) v.findViewById(R.id.img_backIcon);
        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });

        volleyJsonObjectCalanderEventRequest(URL, 3, 5);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);

        switch (parent.getId()) {
            case R.id.spinner_academic:
                //       Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.spinner_month:

                if (position != 0) {
                    calendarEventJsonParsing(position);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void calendarEventJsonParsing(int selectedMonth) {
        event_al = new ArrayList<Event>();
        Boolean flag = false;

        try {
            JSONArray jsonArrayMonthlist = JsonCalendarEventsResponse.getJSONArray("monthlist");
          //  int jsonMonth_id ;
            for (int i = 0; i < jsonArrayMonthlist.length(); i++)
            {
                JSONObject month = jsonArrayMonthlist.getJSONObject(i);
                int jsonMonth_id = month.getInt("month_id");
                // getMonthEventInfo(jsonMonth_id);

                if (selectedMonth == jsonMonth_id)
                {
                    JSONArray eventList = month.getJSONArray("event_list");

                    for (int j = 0; j < eventList.length(); j++)
                    {
                        JSONObject eventNames = eventList.getJSONObject(j);
                        String eventStartDate = eventNames.getString("COEE_EStartDate");
                        String eventEndDate = eventNames.getString("COEE_EEndDate");
                        String eventName = eventNames.getString("COEME_EventName");

                        Event event = new Event(eventName, eventStartDate, eventEndDate);
                        event_al.add(event);
                    }
                    flag = true;
                    break;
                }

            }

            if (flag == false) {
                Toast.makeText(getActivity(), "No Events for selected month", Toast.LENGTH_SHORT).show();
            }

            mAdapter = new EventsAdapter(event_al);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    public void volleyJsonObjectCalanderEventRequest(String url, final int ASMAY_Id, final int MI_Id) {
        String REQUEST_TAG = "com.events.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JsonCalendarEventsResponse = response;
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

                params.put("ASMAY_Id", ASMAY_Id);
                params.put("MI_Id", MI_Id);

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
    /*******************************************************************************************************/


}
