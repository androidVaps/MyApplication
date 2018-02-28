package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.ExamAdapter;
import com.example.san.myapplication.Module.Exam;
import com.example.san.myapplication.Module.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExamActivityNew extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener
{
    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/ExamMarks";
    private static final String URL1 = "http://stagingmobileapp.azurewebsites.net/api/login/Examid";
    public static String TAG = ExamActivity.class.getSimpleName();
    List<Exam> examMarksList;

    double finalMarks;
    ArrayList<Integer> examIds = new ArrayList<>();
    ArrayList<String> examNames = new ArrayList<>();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.marksObtain)
    TextView txtmarksObtain;
    @InjectView(R.id.maxMarks)
    TextView txtmaxMarks;
    @InjectView(R.id.minMarks)
    TextView txtminMarks;
    @InjectView(R.id.result)
    TextView result;
    @InjectView(R.id.spinAcademic)
    Spinner spinnerAcademic;
    @InjectView(R.id.spinExamtype)
    Spinner spinnerExamType;

    RecyclerView rView;

    private GridLayoutManager lLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_new);

        List<Exam> examRows = getAllMarksList();

        lLayout = new GridLayoutManager(ExamActivityNew.this, 3);

        rView = (RecyclerView)findViewById(R.id.recyclerViewMarks);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

      /*  ExamAdapter rcAdapter = new ExamAdapter(ExamActivityNew.this, examRows);
        rView.setAdapter(rcAdapter);*/

        ButterKnife.inject(this);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Exam Details");
     //   toolbar.setTitle("Exam Details");

        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();
        int miid = session.getMI_Id();
        int amst_id = session.getAMST_Id();
        int asmay = session.getASMAY_Id();


        volleyJsonObjectExamIDRequest(URL1, miid, amst_id, asmay);

        String[] spinner0Data = {"Select Academic Year", "2017-18"};


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner0Data);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aa.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        spinnerAcademic.setAdapter(aa);
        spinnerAcademic.setOnItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //     startActivity(new Intent(FeesActivity.this,HomeActivity.class));
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        ((TextView) arg0.getChildAt(0)).setGravity(Gravity.CENTER);

        switch (arg0.getId()) {
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
        progressDialog = new ProgressDialog(this);
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
                                Toast.makeText(ExamActivityNew.this, "Not valid data", Toast.LENGTH_SHORT).show();
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

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
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, examNames);
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
        progressDialog = new ProgressDialog(this);
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
                                Toast.makeText(ExamActivityNew.this, "Not valid data", Toast.LENGTH_SHORT).show();
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

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

            ExamAdapter rcAdapter = new ExamAdapter(ExamActivityNew.this, examMarksList);
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
