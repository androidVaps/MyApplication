package com.example.san.myapplication.NewkioaskDesign;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.AttendanceAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.AttendanceActivity;
import com.example.san.myapplication.Caland.CaldroidSampleActivity;
import com.example.san.myapplication.Module.Attendance;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment
{
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private ProgressDialog progressDialog;
    private String TAG = CaldroidSampleActivity.class.getSimpleName();
    private static String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentAttendance";
    String presentDate[];
    String abscenceDate[];
    Session session;
    TextView abscenceDays;
    TextView presenseDays;

   /* RecyclerView recyclerView1;
    CalendarView calendarView;*/

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance1, container, false);

        /*recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);*/

        ImageView img_back_button = (ImageView) view.findViewById(R.id.img_backIcon);
        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });

        presenseDays = (TextView)view.findViewById(R.id.presence_no);
        abscenceDays = (TextView)view.findViewById(R.id.absence_no);

        initialize(savedInstanceState);
         return view;
    }


    private void setCustomResourceForDates()
    {
             if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
 //           Drawable blue = getResources().getDrawable(R.drawable.blue);
            ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.caldroid_light_red));
//            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            //    caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
//            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            //    caldroidFragment.setTextColorForDate(R.color.white, greenDate);

            /*caldroidFragment.setBackgroundDrawableForDate(blue, minDate);
            caldroidFragment.setBackgroundDrawableForDate(green, maxDate);
            caldroidFragment.setTextColorForDate(R.color.white, minDate);
            caldroidFragment.setTextColorForDate(R.color.white, maxDate);*/

            //    caldroidFragment.setSelectedDates(minDate, maxDate);

            for (String pDate : presentDate)
            {
                String parts[] = pDate.split("/");

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date blueDate = calendar.getTime();
                caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
                caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            }

            for (String pDate : abscenceDate)
            {
                String parts[] = pDate.split("/");

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date greenDate = calendar.getTime();
                caldroidFragment.setBackgroundDrawableForDate(red, greenDate);
                caldroidFragment.setTextColorForDate(R.color.white, greenDate);
            }
            caldroidFragment.refreshView();
        }
    }

    public void initialize(Bundle savedInstanceState)
    {


        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        session = AppSingleton.getInstance(getActivity()).getInstance();

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            /*int current_month = cal.get(Calendar.MONTH) +1;

            volleyJsonObjectFeesRequest(URL, session.getMI_Id(), session.getAMST_Id(), session.getASMAY_Id(),current_month);
            Log.d(TAG,"Month "+current_month);*/

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);


        }

        // Attach to the activity
       FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getActivity(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
              /*  Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();*/

//                volleyJsonObjectAttendanceRequest(URL, session.getMI_Id(), session.getAMST_Id(), session.getASMAY_Id(),month);

                volleyJsonObjectAttendanceRequest(URL, 5, 944, 3,month);

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getActivity(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    /*Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }


    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectAttendanceRequest(String url, final int mi_id, final int amst_id, final int asmay_id,final int month) {
        String REQUEST_TAG = "com.fees.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            int ID = response.getInt("ClassHeld");
                            if (ID == 0) {
                                Toast.makeText(getActivity(), "No data for selected month", Toast.LENGTH_SHORT).show();
                                presenseDays.setText("0");
                                abscenceDays.setText("0");
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
                params.put("monthid",month);

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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/

    private void attendaceResponseParse(JSONObject response) {
        String month = null;
        int class_held, class_attended, percentage;

        try
        {
            class_held = response.getInt("ClassHeld");
            class_attended = response.getInt("Class_Attended");
            percentage = response.getInt("Percentage");

            Log.d(TAG,class_held+"--"+class_attended+"--"+percentage);

            JSONArray jsonArrayPresense = response.getJSONArray("Presentdays");
            JSONArray jsonArrayAbscence = response.getJSONArray("Absentdays");

            presentDate = new String[jsonArrayPresense.length()];
            abscenceDate = new String[jsonArrayAbscence.length()];

            presenseDays.setText("Present Days "+class_attended+"");
            abscenceDays.setText("Absent Days "+jsonArrayAbscence.length()+"");

            for(int i = 0 ; i < jsonArrayPresense.length() ; i++)
            {
                presentDate[i] = jsonArrayPresense.getString(i);
            }

            for(int i = 0 ; i < jsonArrayAbscence.length() ; i++)
            {
                abscenceDate[i] = jsonArrayAbscence.getString(i);
            }

            setCustomResourceForDates();
        } catch(JSONException e) {
            e.printStackTrace();
        }


    }

}
