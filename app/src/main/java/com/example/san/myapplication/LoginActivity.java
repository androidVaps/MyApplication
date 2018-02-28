package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaps on 8/5/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();
    TextView txt_user, txt_pass;
    Button btn_login, btn_cancel;
    Spinner spinner;
    String session_mo_id;
    String session_mi_id;
    ProgressDialog progressDialog;
    Boolean user  = false, pass = false, institute = false;
    String str_username,str_password;
    CheckBox checkBox_rem;
    SharedPreferences pref;
    private static final String URL = "http://stagingcampus.azurewebsites.net/api/LoginFacade";

    public static String  PREFS_NAME="remainderPref";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_pass = (TextView) findViewById(R.id.txt_pass);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        spinner = (Spinner) findViewById(R.id.spinner);

        checkBox_rem = (CheckBox) findViewById(R.id.chk_remind);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.baldwinInstitute_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(staticAdapter);

        // Spinner item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                setSession((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 str_username = txt_user.getText().toString();
                 str_password = txt_pass.getText().toString();

                if (str_username.equals("") || str_username == null) {
                    txt_user.setError("UserName is Empty");
                    user  = true;
                } else {
                    user  = false;
                }
                    if (str_password.equals("") || str_password == null) {
                        txt_pass.setError("Password is Empty");
                        pass = true;
                    }else{
                        pass = false;
                }
                if(institute == true)
                {
                    Toast.makeText(LoginActivity.this, "Please select Institute", Toast.LENGTH_SHORT).show();
                }

                if(user == false && pass == false && institute == false)
                {
                    volleyJsonObjectRequest(URL,str_username,str_password,session_mi_id);
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

    public void getUser()
    {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            //directly show logout form
         //   showLogout(username);
            txt_user.setText(username);
            txt_pass.setText(password);
            Toast.makeText(LoginActivity.this, "You landed Safely !!!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }

    // If Login Success and Remember me, is checked.Login credentials will be save in Shared Preferencess
    public void rememberUser()
    {
        // Checkbox for checking remember me option
        if(checkBox_rem.isChecked())
        {
            //save username and password in SharedPreferences
            getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                    .edit()
                    .putString(PREF_USERNAME,str_username)
                    .putString(PREF_PASSWORD,str_password)
                    .commit();

            Toast.makeText(LoginActivity.this, "You landed Safely !!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }

    }

    public void logout()
    {
        pref =getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        // user="";
        //show login form
        Toast.makeText(LoginActivity.this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
    }

    // Assigning MO_ID and MI_ID
    void setSession(String institute_session_value)
    {
        switch (institute_session_value)
        {
            case "BBHS" :   session_mo_id = "2";
                            session_mi_id = "6";
                            institute = false;
                            break;
            case "BCEHS":   session_mo_id = "2";
                            session_mi_id = "5";
                            institute = false;
                            break;
            case "BGHSRS":  session_mo_id = "2";
                            session_mi_id = "4";
                            institute = false;
                            break;
            default:
                institute = true;
        }
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectRequest(String url, final String userName, final String passWord, final String mi_id)
    {

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.has("message")) {
                                String resp = response.getString("message");
                                Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                rememberUser();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.hide();
            }
        }){
            @Override
            public byte[] getBody() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");

                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);

    }

  /*******************************************************************************************************/
  /*******************************************************************************************************/

}


