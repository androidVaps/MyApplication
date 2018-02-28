package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.san.myapplication.Module.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StudentDeatilsActivity extends AppCompatActivity {

    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentDetails";
    public static String TAG = StudentDeatilsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    int sessionAMST_Id = 0;
    int session_MI_ID = 0;
    int session_ASMAY_Id = 0;

    @InjectView(R.id.image_stud)
    ImageView imageView;
    @InjectView(R.id.edit_stud_name)
    TextView edit_stud_name;
    @InjectView(R.id.edit_stud_regno)
    TextView edit_stud_regno;
    @InjectView(R.id.edit_stud_doa)
    TextView edit_stud_doa;
    @InjectView(R.id.edit_stud_dob)
    TextView edit_stud_dob;
    @InjectView(R.id.edit_stud_contact)
    TextView edit_stud_contact;
    @InjectView(R.id.edit_stud_email)
    TextView edit_stud_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_details);

        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Student Profile");

        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    startActivity(new Intent(StudentDeatilsActivity.this,HomeActivity.class));
                finish();
            }
        });

        //    imageView = (ImageView) findViewById(R.id.image_stud);
        Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();

        sessionAMST_Id = session.getAMST_Id();
        session_MI_ID = session.getMI_Id();
        session_ASMAY_Id = session.getASMAY_Id();

        //    Toast.makeText(StudentDeatilsActivity.this,sessionAMST_Id+"+++++++"+session_MI_ID, Toast.LENGTH_SHORT).show();

        volleyJsonObjectRequest(URL, sessionAMST_Id, session_MI_ID, session_ASMAY_Id);
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
                                Toast.makeText(StudentDeatilsActivity.this, "Failed to access student data", Toast.LENGTH_SHORT).show();
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

    }

    /*******************************************************************************************************/
    /*******************************************************************************************************/


    private void studentDetailsParse(JSONObject response) {
        try {
            String stud_name = response.getString("AMST_FirstName");
            String stud_regNo = response.getString("AMST_RegistrationNo");
            String stud_admNo = formatDate(response.getString("AMST_Date"));
            String stud_dob = formatDate(response.getString("AMST_DOB"));
            String stud_contactNo = response.getString("AMST_MobileNo");
            String stud_mailId = response.getString("AMST_emailId");
            String stud_photo = response.getString("AMST_Photoname");


           /* JSONArray jsonArray = response.getJSONArray("stulist");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String stud_name = jsonObject.getString("amsT_FirstName");
            String stud_regNo = jsonObject.getString("amsT_RegistrationNo");
            String stud_admNo = jsonObject.getString("amsT_AdmNo");
            String stud_dob = jsonObject.getString("amsT_DOB");
            String stud_contactNo = jsonObject.getString("amsT_MobileNo");
            String stud_mailId = jsonObject.getString("amsT_emailId");
            String stud_photo = jsonObject.getString("amsT_Photoname");

            String str_date = null;
            stud_dob = stud_dob.substring(0,10);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            try {

                Date d1 = df.parse(stud_dob);
                str_date = new SimpleDateFormat("dd-MM-yyyy").format(d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/


            // BIND DATA TO VIEW
            edit_stud_name.setText(stud_name);
            edit_stud_regno.setText(stud_regNo);
            edit_stud_doa.setText(stud_admNo);
            edit_stud_dob.setText(stud_dob);
            edit_stud_contact.setText(stud_contactNo);  // Integer.toString(stud_contactNo)
            edit_stud_email.setText(stud_mailId);
            //  edit_stud_email.setText(stud_mailId);


            //    volleyImageLoader(stud_photo);
            profile_image(stud_photo);
            Log.d(TAG, stud_name + "+" + stud_regNo + "+" + stud_admNo + "+" + stud_dob + "+" + stud_contactNo + "+" + stud_mailId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    // Handling Image response
    public void volleyImageLoader(String url) {
        ImageLoader imageLoader = AppSingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    /*LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    showDialogView = li.inflate(R.layout.show_dialog, null);
                    outputImageView = (ImageView)showDialogView.findViewById(R.id.image_view_dialog);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setView(showDialogView);
                    alertDialogBuilder
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setCancelable(false)
                            .create();*/
                    imageView.setImageBitmap(response.getBitmap());
                    //   alertDialogBuilder.show();
                }
            }
        });
    }

    public void profile_image(String imgUrl) {
        Glide.with(this)
                .load(imgUrl)
                .asBitmap()
                .placeholder(R.drawable.circle_crop)
                .error(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),
                                Bitmap.createScaledBitmap(resource, 50, 50, false));
                        drawable.setCircular(true);
                        imageView.setImageDrawable(drawable);
                    }
                });
    }

    private String formatDate(String strDate) {
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

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
    }
}
