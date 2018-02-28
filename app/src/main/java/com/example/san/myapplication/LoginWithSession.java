package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Module.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaps on 8/19/2017.
 */

public class LoginWithSession
{
/*package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Module.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

    public class LoginActivityNew extends AppCompatActivity {
        private static final String URL_MIdata = "http://stagingmobileapp.azurewebsites.net/api/login/MIdata";
        private static final String URL_Login = "http://stagingmobileapp.azurewebsites.net/api/login";
        public static String TAG = MainActivity.class.getSimpleName();
        public static String PREFS_NAME = "remainderPref";
        public static String PREF_USERNAME = "username";
        public static String PREF_PASSWORD = "password";
        TextView txt_user, txt_pass;
        Button btn_login, btn_cancel;
        int sessionAMST_Id;
        int session_MI_ID;
        int sessionASMAY_Id;
        ProgressDialog progressDialog;
        Boolean user = false, pass = false, institute = false;
        String str_username, str_password;
        CheckBox checkBox_rem;
        SharedPreferences pref;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_new);

            txt_user = (TextView) findViewById(R.id.txt_user);
            txt_pass = (TextView) findViewById(R.id.txt_pass);

            btn_login = (Button) findViewById(R.id.btn_login);
            btn_cancel = (Button) findViewById(R.id.btn_cancel);

            checkBox_rem = (CheckBox) findViewById(R.id.chk_remind);

            txt_user.setText("AHA7592");
            txt_pass.setText("Password@123");

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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
                        volleyJsonObjectRequestGetMI_ID(URL_MIdata, str_username, str_password);
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
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
            getUser();
        }

        // Session
        public void getUser() {
            SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String username = pref.getString(PREF_USERNAME, null);
            String password = pref.getString(PREF_PASSWORD, null);

            if (username != null || password != null) {
                //directly show logout form
                //   showLogout(username);
                txt_user.setText(username);
                txt_pass.setText(password);
                Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "You landed Safely !!!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(com.example.san.myapplication.LoginActivityNew.this, HomeActivity.class));
                finish();
            }
        }

        // If Login Success and Remember me, is checked.Login credentials will be save in Shared Preferencess
        public void rememberUser() {
            // Checkbox for checking remember me option
            if (checkBox_rem.isChecked()) {
                //save username and password in SharedPreferences
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_USERNAME, str_username)
                        .putString(PREF_PASSWORD, str_password)
                        .commit();

                Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "You landed Safely !!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(com.example.san.myapplication.LoginActivityNew.this, HomeActivity.class));
                finish();
            }else{
                Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "You landed Safely !!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(com.example.san.myapplication.LoginActivityNew.this, HomeActivity.class));
                finish();
            }

        }

        public void logout() {
            pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            // user="";
            //show login form
            Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
        }


        *//***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************//*
        *//*****************************************************************************************************//*
        public void volleyJsonObjectRequestGetMI_ID(String url, final String userName, final String passWord) {

            String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                if (response.isNull("message")) {
                                    Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    rememberUser();
                                    getMI_IDfromResponce(response);
                                } else {
                                    String resp = response.getString("message");
                                    Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, resp, Toast.LENGTH_SHORT).show();
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

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", userName);
                    params.put("password", passWord);

               *//* params.put("username","AHA7592");
                params.put("password","Password@123");*//*

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


        *//*******************************************************************************************************//*
        *//*******************************************************************************************************//*

        private void getMI_IDfromResponce(JSONObject response) {
            try {
            *//*JSONArray jsonArray = response.getJSONArray("mIdata");
            JSONObject jsonObject = jsonArray.getJSONObject(0);*//*

                session_MI_ID = response.getInt("mI_Id");

                Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, session_MI_ID + "", Toast.LENGTH_SHORT).show();
                // Check Login Services
                volleyJsonObjectRequestLogin(URL_Login, str_username, str_password, Integer.toString(session_MI_ID));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        *//***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************//*
        *//*****************************************************************************************************//*
        public void volleyJsonObjectRequestLogin(String url, final String userName, final String passWord, final String mi_id) {

            String REQUEST_TAG = "com.login";
       *//* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*//*

            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                if (response.isNull("message")) {
                                    getAMST_IDfromResponce(response);
                                    Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, "Login Success AMST_ID", Toast.LENGTH_SHORT).show();
                                } else {
                                    String resp = response.getString("message");
                                    Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, resp, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //         progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //    progressDialog.dismiss();
                }
            }) {
                @Override
                public byte[] getBody() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", userName);
                    params.put("password", passWord);
                    params.put("MI_ID", mi_id);

               *//* params.put("username","AHA7592");
                params.put("password","Password@123");
                params.put("MI_ID","5");*//*


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


        *//*******************************************************************************************************//*
        *//*******************************************************************************************************//*

        private void getAMST_IDfromResponce(JSONObject response) {
            try {
                sessionAMST_Id = response.getInt("amsT_Id");
                sessionASMAY_Id = response.getInt("asmaY_Id");

                Log.d(TAG, sessionAMST_Id + ":sessionAMST_Id");
                Log.d(TAG, sessionASMAY_Id + ":sessionASMAY_Id");
                Log.d(TAG, session_MI_ID +":session_MI_ID");

                // put the MI_ID and amsT_Id in session
                Toast.makeText(com.example.san.myapplication.LoginActivityNew.this, sessionAMST_Id + "***" + sessionASMAY_Id, Toast.LENGTH_SHORT).show();
                Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();

                session.setMI_Id(session_MI_ID);
                session.setAMST_Id(sessionAMST_Id);
                session.setASMAY_Id(sessionASMAY_Id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }*/

}
