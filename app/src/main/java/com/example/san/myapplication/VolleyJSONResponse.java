package com.example.san.myapplication;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaps on 9/5/2017.
 */

public class VolleyJSONResponse
{
    String  REQUEST_TAG = "com.student.details";
    String result;

        public String handleReq(String url,final int sessionAMST_Id,final int session_MI_ID,final int session_ASMAY_Id,final String TAG,Context context)
        {
            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,url, null,
                    new Response.Listener<JSONObject>(){

                        @Override
                        public void onResponse(JSONObject response)
                        {
                            result = response.toString();
                        }
                    },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                //    result = error.getMessage();
                }
            }){
                @Override
                public byte[] getBody() {
                    Map<String, Integer> params = new HashMap<String, Integer>();

                /*params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");*/

                    params.put("AMST_Id",sessionAMST_Id);
                    params.put("MI_Id",session_MI_ID);
                    params.put("ASMAY_Id",session_ASMAY_Id);

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
            AppSingleton.getInstance(context).addToRequestQueue(jsonObjectReq,REQUEST_TAG);

            return result;
        }
}
