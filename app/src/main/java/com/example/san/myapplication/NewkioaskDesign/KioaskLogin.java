package com.example.san.myapplication.NewkioaskDesign;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.HomeActivity;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KioaskLogin extends AppCompatActivity
{
    
    private static final String URL_Login = "http://stagingmobileapp.azurewebsites.net/api/login";
    public static String TAG = KioaskLogin.class.getSimpleName();
    public static String PREFS_NAME = "remainderPref";

    EditText txt_user, txt_pass;
    int sessionAMST_Id;
    int session_MI_ID;
    int sessionASMAY_Id;
    String MI_Name;
    ProgressDialog progressDialog;
    Boolean user = false, pass = false, institute = false;
    String str_username, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kioask_login);

        Button btn_submitname = (Button)findViewById(R.id.btn_submit);
        Button btn_cancelname = (Button)findViewById(R.id.btn_cancel);
         txt_user = (EditText) findViewById(R.id.uname);
         txt_pass = (EditText) findViewById(R.id.pass);


       /* txt_user.setText("AHANA");
        txt_pass.setText("**********");*/

        slideShow();

        btn_submitname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* txt_user.setText("AHA7592");
                txt_pass.setText("Password@123");*/

                str_username = txt_user.getText().toString();
                str_password = txt_pass.getText().toString();

                if (str_username.equals("") || str_username == null) {
                    txt_user.setError("UserName is Empty");
                    user = true;
                } else {
                    user = false;
                }
                if (str_password.equals("") || str_password == null) {
                    txt_pass.setError("Password is Empty");
                    pass = true;
                } else {
                    pass = false;
                }

                if (user == false && pass == false) {
                    //    volleyJsonObjectRequestGetMI_ID(URL_MIdata, str_username, str_password);
                    volleyJsonObjectRequestLogin(URL_Login, str_username, str_password);
                }
            }
        });

        btn_cancelname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //  logout();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
     }


    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectRequestLogin(String url, final String userName, final String passWord)  // , final String mi_id
    {

        String REQUEST_TAG = "com.login";
       /* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.isNull("message"))
                            {
                                getAMST_IDfromResponce(response);
                               // finish();
                                Intent i = new Intent(KioaskLogin.this,MainActivityKioask.class);
                             //   i.putExtra("mi_name",MI_Name);
                                startActivity(i);
                                Toast.makeText(KioaskLogin.this, "Loged in Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                String resp = response.getString("message");
                                Toast.makeText(KioaskLogin.this, resp, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //         progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error2: " + error.getMessage());
                //    progressDialog.dismiss();
            }
        }) {
            @Override
            public byte[] getBody() {

                Map<String, String> params = new HashMap<String, String>();

                //      Map<Object, Object> params = new HashMap<Object, Object>();

                params.put("username", userName);
                params.put("password", passWord);
                //     params.put("MI_Id", mi_id);


               /* params.put("username","AHA7592");
                params.put("password","Password@123");
                params.put("MI_ID","5");*/


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

    private void getAMST_IDfromResponce(JSONObject response) {
        try {

            session_MI_ID = response.getInt("MI_Id");
            MI_Name = response.getString("MI_Name");

            getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                    .edit()
                    .putString("MI_NAME",MI_Name)
                    .commit();

            sessionAMST_Id = response.getInt("AMST_Id");
            sessionASMAY_Id = response.getInt("ASMAY_Id");

            Log.d(TAG, sessionAMST_Id + ":sessionAMST_Id");
            Log.d(TAG, sessionASMAY_Id + ":sessionASMAY_Id");
            Log.d(TAG, session_MI_ID +":session_MI_ID");

            // put the MI_ID and amsT_Id in session
            //    Toast.makeText(KioaskLogin.this, sessionAMST_Id + "***" + sessionASMAY_Id, Toast.LENGTH_SHORT).show();
            Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();

            session.setMI_Id(session_MI_ID);
            session.setAMST_Id(sessionAMST_Id);
            session.setASMAY_Id(sessionASMAY_Id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txt_user.setText("");
        txt_pass.setText("");

        //   txt_user.requestFocus();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        String user_name = txt_user.getText().toString();
        if(user_name.length() == 0)
        {
            if(txt_user.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                txt_pass.clearFocus();
            }
        }
    }

    public void slideShow()
    {
           final ImageView img = (ImageView) findViewById(R.id.img_gallary);

        final int[] imageArray = { R.drawable.pic1, R.drawable.pic2};

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                img.setImageResource(imageArray[i]);
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

}
