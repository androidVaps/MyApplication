package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    public static String TAG = MainActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    View showDialogView;
    TextView outputTextView, display_result;
    ImageView outputImageView;
    StringBuilder stringBuilder;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        stringBuilder = new StringBuilder();
        display_result = (TextView) findViewById(R.id.textView1);


        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   volleyJsonArrayRequest("https://androidtutorialpoint.com/api/volleyJsonArray");
             //    volleyJsonObjectRequest("http://stagingcampusux.azurewebsites.net/api/login/setvirtauldeatilsnew/103");
                volleyJsonObjectRequest("http://stagingcampus.azurewebsites.net/api/LoginFacade");
            }
        });

        findViewById(R.id.btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  volleyImageLoader("https://androidtutorialpoint.com/api/lg_nexus_5x");
//                jsonStringRequest("http://stagingcampusux.azurewebsites.net/api/Authorization/connect/token");

                jsonStringRequest("http://stagingcampus.azurewebsites.net/api/LoginFacade");
            }
        });

    }


    public void volleyJsonArrayRequest(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonArrayRequest";
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        Log.d(TAG, response.toString());
                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        showDialogView = li.inflate(R.layout.show_dialog, null);
                        outputTextView = (TextView) showDialogView.findViewById(R.id.text_view_dialog);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(showDialogView);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        parseResult(response.toString());
                                        display_result.setText(stringBuilder);
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        outputTextView.setText(response.toString());
                        alertDialogBuilder.show();
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.hide();
            }
        });
       /* {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");   // "application/json"
            //    headers.put("apiKey", "xxxxxxxxxxxxxxx");
              *//*  headers.put("password","Radha@123");
                headers.put("username","rad");*//*
                return headers;
            }

                @Override
                protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password","Radha@123");
                params.put("username","rad");
                return params;
            }

        };*/

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    // Handling Object response

    public void volleyJsonObjectRequest(String url)
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
                       LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        showDialogView = li.inflate(R.layout.show_dialog, null);
                        outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(showDialogView);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(response.has("message"))
                                        {
                                            Toast.makeText(MainActivity.this,"Invalid",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(MainActivity.this,"Valid",Toast.LENGTH_LONG).show();
                                        }

                                        // volleyJsonObjectRequest1("http://stagingcampusux.azurewebsites.net/api/Authorization/connect/token");
                                        // jsonStringRequest("http://stagingcampusux.azurewebsites.net/api/Authorization/connect/token");
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        outputTextView.setText(response.toString());
                        alertDialogBuilder.show();
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
                params.put("username","rakesh");
                params.put("password","Rakesh@123");
                params.put("MI_Id","5");
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
       /* RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectReq);*/
    }


    public void volleyJsonObjectRequest1(String url)
    {

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequet";
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "Radha");
        params.put("password","Radha@123");
        params.put("grant_type","password");
        params.put("scope","Offline_access");
        params.put("ClientId","4");

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        showDialogView = li.inflate(R.layout.show_dialog, null);
                        outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(showDialogView);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                }
                                })
                                .setCancelable(false)
                                .create();
                        outputTextView.setText(response.toString());
                        alertDialogBuilder.show();
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        Log.d("Error Response 1++",res.toString());
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);

                        Log.d("Error Response 2++",obj.toString());

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

                progressDialog.hide();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");    // "application/json"
                //   headers.put("Content-Type","application/json; charset=utf-8");
                Log.d("Header","Inside Header");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Log.d("Params","Inside Params");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "Radha");
                params.put("password","Radha@123");
                params.put("grant_type","password");
                params.put("scope","Offline_access");
                params.put("ClientId","4");
                return params;

            }

        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }


    public void jsonStringRequest(String url)
    {
        String  REQUEST_TAG = "com.androidtutorialpoint.jsonStringRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                Log.d("Error Response 1++",res.toString());
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);

                                Log.d("Error Response 2++",obj.toString());

                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }){
            /*@Override
            protected Map<String,String> getParams(){
                Log.d("Params","Inside Params");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "Rakesh");
                params.put("password","Rakesh@123");
                params.put("grant_type","password");
                params.put("scope","Offline_access");
                params.put("ClientId","5");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                 headers.put("Content-Type", "application/x-www-form-urlencoded");   // "application/json"
                //  headers.put("Content-Type","application/json; charset=utf-8");
                Log.d("Header","Inside Header");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "Rakesh");
                params.put("password","Rakesh@123");
                params.put("grant_type","password");
                params.put("scope","Offline_access");
                params.put("ClientId","5");
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }  // https://stackoverflow.com/questions/33573803/how-to-send-a-post-request-using-volley-with-string-body
*/

           /* @Override
            public byte[] getBody() throws AuthFailureError {
                Log.d("Params","Inside Params");
                JSONObject params = new JSONObject();
                *//*jsonBody.put("Title", "Android Volley Demo");
                jsonBody.put("Author", "BNK");*//*

                //        Map<String, String> params = new HashMap<String, String>();
                String requestBody = null;
                try {
                    params.put("username", "Radha");
                    params.put("password","Radha@123");
                    params.put("grant_type","password");
                    params.put("scope","Offline_access");
                    params.put("ClientId","4");
                    requestBody = params.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }

            }

            @Override
            public String getBodyContentType()
            {
                Log.d("Header","Inside Header");
              //  return "application/text/html; charset=us-ascii";
                return "application/x-www-form-urlencoded";
              //  return "application/json";
            }*/

          /*  @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.d("Params","Inside Params");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "Radha");
                params.put("password","Radha@123");
                params.put("grant_type","password");
                params.put("scope","Offline_access");
                params.put("ClientId","4");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");   // "application/json"
                //  headers.put("Content-Type","application/json; charset=utf-8");
                Log.d("Header","Inside Header");
                return headers;
            }*/

           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("Params","Inside Params");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username","rakesh");
                params.put("password","Rakesh@123");
                params.put("MI_Id","5");
                params.put("Entry_Date","01/07/2016");
                return params;

            }*/

            @Override
            public byte[] getBody() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username","rakesh");
                params.put("password","Rakesh@123");
                params.put("MI_Id","5");
                params.put("Entry_Date","01/07/2016");

                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }


        };


        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);
    }


















    // Handling Image response
    public void volleyImageLoader(String url){
        ImageLoader imageLoader = AppSingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
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
                            .create();
                    outputImageView.setImageBitmap(response.getBitmap());
                    alertDialogBuilder.show();
                }
            }
        });
    }

    private void parseResult(String jsonInput)
    {

        try {
            JSONArray jsonArray = new JSONArray(jsonInput);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                String val1 = jsonObject.getString("rom");
                String val2 = jsonObject.getString("screenSize");
                String val3 = jsonObject.getString("backCamera");
                String val4 = jsonObject.getString("companyName");
                printLog(val1 + " " + val2 + " " + val3 + " " + val4);
                stringBuilder.append(val1 + " " + val2 + " " + val3 + " " + val4);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void printLog(String logMessage)
    {
        Log.d(TAG, logMessage);
    }
}
