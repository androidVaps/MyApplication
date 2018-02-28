package com.example.san.myapplication.NewkioaskDesign;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.san.myapplication.Adapters.ExamAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.ExamActivity;
import com.example.san.myapplication.ExamActivityNew;
import com.example.san.myapplication.Module.Exam;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment implements  AdapterView.OnItemSelectedListener
{

    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/ExamMarks";
    private static final String URL1 = "http://stagingmobileapp.azurewebsites.net/api/login/Examid";
    public static String TAG = ExamActivity.class.getSimpleName();
    List<Exam> examMarksList;

    double finalMarks;
    ArrayList<Integer> examIds = new ArrayList<>();
    ArrayList<String> examNames = new ArrayList<>();

   /* @Inject(R.id.toolbar)
    Toolbar toolbar;*/



    TextView txtmarksObtain,txtmaxMarks,txtminMarks,result;
    Spinner spinnerAcademic;
    Spinner spinnerExamType;
    RecyclerView rView;

    private GridLayoutManager lLayout;


    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam, container, false);

        spinnerAcademic = (Spinner) view.findViewById(R.id.spinAcademic);
        spinnerExamType = (Spinner) view.findViewById(R.id.spinExamtype);
        txtmarksObtain = (TextView) view.findViewById(R.id.marksObtain);
        txtmaxMarks = (TextView) view.findViewById(R.id.maxMarks);
        txtminMarks = (TextView) view.findViewById(R.id.minMarks);
        result = (TextView) view.findViewById(R.id.result);


        List<Exam> examRows = getAllMarksList();

        lLayout = new GridLayoutManager(getActivity(), 2);

        rView = (RecyclerView) view.findViewById(R.id.recyclerViewMarks);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        ImageView img_back_button = (ImageView) view.findViewById(R.id.img_backIcon);
        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });

      /*  ExamAdapter rcAdapter = new ExamAdapter(ExamActivityNew.this, examRows);
        rView.setAdapter(rcAdapter);*/



        /*TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Exam Details");*/
        //   toolbar.setTitle("Exam Details");

     /*   setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowTitleEnabled(false);*/

        Session session = AppSingleton.getInstance(getActivity()).getInstance();
        int miid = session.getMI_Id();
        int amst_id = session.getAMST_Id();
        int asmay = session.getASMAY_Id();


        volleyJsonObjectExamIDRequest(URL1, miid, amst_id, asmay);
        String[] spinner0Data = {"Select Academic Year", "2017-18"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_row, spinner0Data);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aa.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinnerAcademic.setAdapter(aa);
        spinnerAcademic.setOnItemSelectedListener(this);

      /*  toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //     startActivity(new Intent(FeesActivity.this,HomeActivity.class));
            }
        });*/

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);

        switch (parent.getId()) {
            case R.id.spinExamtype:
                //       Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                if(position != 0)
                {
                    volleyJsonObjectExamMarksRequest(URL);  // , miid, amst_id, asmay
                }
                break;
            case R.id.spinAcademic:
                //     Toast.makeText(getApplicationContext(),examIds.get(position)+1 ,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectExamIDRequest(String url, final int mi_id, final int amst_id, final int asmay_id) {
        final ProgressDialog progressDialog;
        String REQUEST_TAG = "com.exam.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            //   parseMarksResponse(response);
                            int valid = response.getJSONArray("examlist").length();
                            if (valid == 0) {
                                Toast.makeText(getActivity(), "Not valid data", Toast.LENGTH_SHORT).show();
                            } else {
                                jsonParseMarksResponse(response);
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

                params.put("ASMAY_Id", 1);
                params.put("Mi_Id", 6);
                params.put("AMST_Id", 4515);

               /* params.put("ASMAY_Id", 1);
                params.put("Mi_Id", 5);
                params.put("AMST_Id", 944);*/


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


    public void jsonParseMarksResponse(JSONObject response) {

        examNames.add("Select Exam Type");
        try {
            JSONArray examsArray = response.getJSONArray("examlist");
            for (int i = 0; i < examsArray.length(); i++) {
                JSONObject examDetails = examsArray.getJSONObject(i);
                examIds.add(examDetails.getInt("EME_Id"));
                examNames.add(examDetails.getString("EME_ExamName"));
            }
            spinnerSelection();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void spinnerSelection()
    {
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_row, examNames);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aa.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinnerExamType.setAdapter(aa);
        spinnerExamType.setOnItemSelectedListener(this);
    }


    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectExamMarksRequest(String url)  // , final int mi_id, final int amst_id, final int asmay_id
    {
        final ProgressDialog progressDialog;
        String REQUEST_TAG = "com.exam.details";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            //   parseMarksResponse(response);
                            int valid = response.getJSONArray("Marklist").length();
                            if (valid == 0) {
                                Toast.makeText(getActivity(), "Not valid data", Toast.LENGTH_SHORT).show();
                            } else {
                                parseMarksResponse(response);
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

                /*params.put("ASMAY_Id", 1);
                params.put("MI_Id", 6);
                params.put("AMST_Id", 4515);
                params.put("EME_Id",6);*/

                params.put("ASMAY_Id", 3);
                params.put("MI_Id", 5);
                params.put("AMST_Id", 944);
                params.put("EME_Id",14);

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


    public void parseMarksResponse(JSONObject jsonObject)
    {
        try {
            String marks = jsonObject.getString("subMorGFlag");
            JSONArray jsonArray = jsonObject.getJSONArray("Marklist");
            int TotalMinMarks = 0;
            int TotalMaxMarks = 0;
            int TotalObtainedMarks = 0;

            Exam examObject = new Exam();
            examMarksList = new ArrayList<Exam>();

            //    System.out.println("Count of Subject+++++++"+jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subDetails = jsonArray.getJSONObject(i);
                examObject = new Exam(subDetails.getString("SubjectName"), subDetails.getInt("TotalMarks"),
                        subDetails.getInt("MinMarks"), subDetails.getInt("obtainmarks"));

                TotalMinMarks = TotalMinMarks + subDetails.getInt("MinMarks");
                TotalMaxMarks = TotalMaxMarks + subDetails.getInt("TotalMarks");
                TotalObtainedMarks = TotalObtainedMarks + subDetails.getInt("obtainmarks");

                Log.d("TotalMaxMarks", TotalMaxMarks + "");
                Log.d(TAG, TotalMinMarks + "");
                Log.d(TAG, TotalObtainedMarks + "");

                examMarksList.add(examObject);
            }
            finalMarks = (double) ((double) (TotalObtainedMarks) / (double) (TotalMaxMarks)) * 100;
            Log.d(TAG, finalMarks + "");

            finalMarks = finalMarks*100;
            finalMarks = Math.round(finalMarks);
            finalMarks = finalMarks /100;

            txtmarksObtain.setText(TotalObtainedMarks + "");
            txtmaxMarks.setText(TotalMaxMarks + "");
            txtminMarks.setText(TotalMinMarks + "");
            result.setText(finalMarks + "%");

            ExamAdapter rcAdapter = new ExamAdapter(getActivity(), examMarksList);
            rView.setAdapter(rcAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private List<Exam> getAllMarksList()
    {
        List<Exam> examList = new ArrayList<Exam>();

        examList.add(new Exam("Kannada",120,45,110));
        examList.add(new Exam("Hindi",100,35,98));
        examList.add(new Exam("English",100,35,96));
        examList.add(new Exam("History",100,35,99));
        examList.add(new Exam("Mathematics",100,35,100));
        examList.add(new Exam("Science",100,35,98));
        examList.add(new Exam("Kannada",120,45,110));
        examList.add(new Exam("Hindi",100,35,98));

        return examList;
    }




}
