package com.example.san.myapplication.NewkioaskDesign;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Caland.CaldroidSampleActivity;
import com.example.san.myapplication.Caland.CalendarEventsActivity;
import com.example.san.myapplication.ExamActivityNew;
import com.example.san.myapplication.FeesActivityNew1;
import com.example.san.myapplication.HomeActivity;
import com.example.san.myapplication.LoginActivityNew;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.R;
import com.example.san.myapplication.StudentDeatilsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivityKioask extends AppCompatActivity implements View.OnClickListener
{

    HashMap<String,String> Hash_file_maps;

    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentDetails";
    public static String TAG = StudentDeatilsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    int sessionAMST_Id = 0;
    int session_MI_ID = 0;
    int session_ASMAY_Id = 0;
    TextView txt_stud_name;
    ImageView img_stud;
    String stud_name,stud_lastname,stud_middlename,stud_regNo,stud_admNo,stud_dob,stud_contactNo,stud_mailId,class_name,section_name,stud_photo;
    Bundle stud_bundle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kioask);

        TextView marque_text = (TextView) this.findViewById(R.id.marque_text);
        marque_text.setSelected(true);

        txt_stud_name = (TextView) findViewById(R.id.edit_stud_name);

        VerticalScrollingTextView tvContent1 = (VerticalScrollingTextView) findViewById(R.id.tvContent1);
        tvContent1.setMovementMethod(new ScrollingMovementMethod());
        tvContent1.scroll();
        tvContent1.setText("The Commencement exercises is carried out every year in January when the graduating students of class X walk up the aisle in traditional white sarees. Deserving graduates are given prestigious awards instituted by various donors." +
                "\n\n The Annual Prize TTDay is held in the last week of June every year. At this time, academic excellence is recognized and the brilliant performers in the previous year’s examinations are given prizes. The first 3 rank holders in each section from Std. I to Std. X are identified and rewarded for their hard work. A cultural show precedes the prize distribution. Overall winners across sections are presented with the Proficiency cup for the class." +
                "\n\n An inter-school literary and cultural festival organized bi-annually is a much awaited event on the school calendar. It is organized by the students, under the guidance of teachers and invitations to compete in various events like quiz, debate, crossword, sudoku, choir and dance performances, creative writing, rangoli, face-painting, art, cooking and a host of other activities, are sent out to many schools in the city" +
                "\n\n Every year in November, the founding father Mr. John Baldwin, is remembered, with gratitude by the students. Miss Urdell Montgomery was the first Principal of Baldwin Girls’ High School and the main hall on the campus is named after her");

        VerticalScrollingTextView tvContent = (VerticalScrollingTextView) findViewById(R.id.tvContent);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        tvContent.scroll();
        tvContent.setText(">> Drawing compitation prize distribution \n\n >> Reading technics seminar \n\n >> Writing technics seminar \n\n >> Poem singing compitation \n\n" +
                ">> Clay model extra class in I block \n\n >> Mother day celebration \n\n >>An inter-school literary and cultural festival \n\n\n" +
                ">> Father day celebration \n\n");

        /*MenuFragment fragment = new MenuFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ll_main_content,fragment,"main_container");
        ft.commit();*/

        Session session = AppSingleton.getInstance(this).getInstance();

        sessionAMST_Id = session.getAMST_Id();
        session_MI_ID = session.getMI_Id();
        session_ASMAY_Id = session.getASMAY_Id();

        img_stud = (ImageView) findViewById(R.id.img_stud);


        volleyJsonObjectRequest(URL, 944, 5, 3);

        onclickFunction();

    }

    private void onclickFunction() {

        findViewById(R.id.btn_stud_det).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StudentDetailsFragment fragment = new StudentDetailsFragment();
                fragment.setArguments(stud_bundle);
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.ll_main_content,fragment,"main_container");
                ft1.addToBackStack(null);
                ft1.commit();

            }
        });

        findViewById(R.id.btn_exam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExamFragment exam_fragment = new ExamFragment();
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.ll_main_content,exam_fragment,"main_container");
                ft2.addToBackStack(null);
                ft2.commit();

            }
        });


        findViewById(R.id.btn_attend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AttendanceFragmentNew attend_fragment = new AttendanceFragmentNew();
                FragmentManager fm3 = getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.ll_main_content,attend_fragment,"main_container");
                ft3.addToBackStack(null);
                ft3.commit();
            }
        });

        findViewById(R.id.btn_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventFragmentNew events_fragment = new EventFragmentNew();
                FragmentManager fm4 = getSupportFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                ft4.replace(R.id.ll_main_content,events_fragment,"main_container");
                ft4.addToBackStack(null);
                ft4.commit();
            }
        });

        findViewById(R.id.btn_fees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeesFragment fees_fragment = new FeesFragment();
                FragmentManager fm5 = getSupportFragmentManager();
                FragmentTransaction ft5 = fm5.beginTransaction();
                ft5.replace(R.id.ll_main_content,fees_fragment,"main_container");
                ft5.addToBackStack(null);
                ft5.commit();
            }
        });


        findViewById(R.id.btn_tt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableFragment tt_fragment = new TimeTableFragment();
                FragmentManager fm6 = getSupportFragmentManager();
                FragmentTransaction ft6 = fm6.beginTransaction();
                ft6.replace(R.id.ll_main_content,tt_fragment,"main_container");
                ft6.addToBackStack(null);
                ft6.commit();
            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(MainActivityKioask.this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivityKioask.this, KioaskLogin.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
      super.onStop();
    }

   /* @Override
    public void onBackPressed() {
        final StudentDetailsFragment fragment = (StudentDetailsFragment) getSupportFragmentManager().findFragmentByTag("main_container");
        if( fragment.allowBackPressed() == 1) // and then you define a method allowBackPressed with the logic to allow back pressed or not
        {
            super.onBackPressed();
        }
    }*/


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_stud_det:

                break;

            case R.id.btn_exam:

                break;

            case R.id.btn_events:
                // do your code
                break;

            case R.id.btn_attend:

                break;

            case R.id.btn_fees:
                // do your code
                break;

            case R.id.btn_exit:
                // do your code
                break;

            default:
                break;
        }
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectRequest(String url, final int sessionAMST_Id, final int session_MI_ID, final int session_ASMAY_Id) {
        String REQUEST_TAG = "com.student.details";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            int ID = Integer.parseInt(response.getString("AMST_Id"));
                            if (ID == 0) {
                                Toast.makeText(MainActivityKioask.this, "Failed to access student data", Toast.LENGTH_SHORT).show();
                            } else {
//                                Toast.makeText(StudentDeatilsActivity.this, "got data", Toast.LENGTH_SHORT).show();
                                studentDetailsParse(response);
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

                /*params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");*/

                params.put("AMST_Id", sessionAMST_Id);
                params.put("MI_Id", session_MI_ID);
                params.put("ASMAY_Id", session_ASMAY_Id);

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
        AppSingleton.getInstance(this).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/

    private void studentDetailsParse(JSONObject response)
    {
        try {
            stud_name = response.getString("AMST_FirstName");
            stud_lastname = response.getString("AMST_LastName");
            stud_middlename = response.getString("AMST_MiddleName");
            stud_regNo = response.getString("AMST_RegistrationNo");
            stud_admNo = formatDate(response.getString("AMST_Date"));
            stud_dob = formatDate(response.getString("AMST_DOB"));
            stud_contactNo = response.getString("AMST_MobileNo");
            stud_mailId = response.getString("AMST_emailId");
            stud_photo = response.getString("AMST_Photoname");
            class_name = response.getString("ASMCL_ClassName");
            section_name = response.getString("ASMC_SectionName");
            //       String email =  response.getString("AMST_emailId");

            // Image loading
            Glide.with(this)
                    .load(stud_photo)
                    .into(img_stud);

            // https://bdcampusstrg.blob.core.windows.net/files/4/StudentProfilePics/2013-252.JPG
            // https://bdcampusstrg.blob.core.windows.net/files/5/StudentProfilePics/2013n-002.jpg

            stud_bundle = new Bundle();
            stud_bundle.putString("stud_name",stud_name);
            stud_bundle.putString("stud_regno",stud_regNo);
            stud_bundle.putString("stud_admNo",stud_admNo);
            stud_bundle.putString("stud_dob",stud_dob);
            stud_bundle.putString("stud_contactNo",stud_contactNo);
            stud_bundle.putString("stud_mailId",stud_mailId);
            stud_bundle.putString("class_name",class_name);
            stud_bundle.putString("section_name",section_name);


            txt_stud_name.setText(stud_name+"\n  "+stud_middlename+"\n"+stud_lastname);
            Log.d(TAG, stud_name + "+" + stud_regNo + "+" + stud_admNo + "+" + stud_dob + "+" + stud_contactNo + "+" + stud_mailId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String formatDate(String strDate)
    {
        String str_date = null;
        strDate = strDate.substring(0, 9);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = df.parse(strDate);
            str_date = new SimpleDateFormat("dd-MM-yyyy").format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str_date;
    }


}
