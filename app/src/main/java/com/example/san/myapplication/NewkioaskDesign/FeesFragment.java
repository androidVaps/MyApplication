package com.example.san.myapplication.NewkioaskDesign;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.FeesTabAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.FeesActivityNew1;
import com.example.san.myapplication.Module.EventHandlingJSON;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.Module.Test;
import com.example.san.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeesFragment extends Fragment
{

    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentFeeTerm";
    public static String TAG = FeesActivityNew1.class.getSimpleName();
    ProgressDialog progressDialog;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private FeesTabAdapter feesAdapter;
    private TabLayout mTabLayout;
    public EventBus eventBus = EventBus.getDefault();
    public Test test;
    EventHandlingJSON eventHandlingJSON;
    public static final int payable_amount = 0;

    public FeesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fees, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab);

        ImageView img_back_button = (ImageView) view.findViewById(R.id.img_backIcon);
        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });


        Session session = AppSingleton.getInstance(getActivity()).getInstance();
        int miid = session.getMI_Id();
        int amst_id = session.getAMST_Id();
        int asmay = session.getASMAY_Id();

        volleyJsonObjectFeesRequest(URL, miid, amst_id, asmay);

        return view;
    }


    private void setViewPager(JSONObject object)
    {
        Bundle bundle = new Bundle();
        String myMessage = "Stackoverflow is cool!";
        bundle.putString("message", myMessage);


        feesAdapter = new FeesTabAdapter(getChildFragmentManager(), object,getActivity());
        mViewPager.setAdapter(feesAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectFeesRequest(String url, final int mi_id, final int amst_id, final int asmay_id) {
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
                            int totalNetAmount = response.getInt("TotalNetAmount");

                            setViewPager(response);

                            //    Object temp = response.get("paymenthistory");

                        /*    JSONArray jsonArray = response.getJSONArray("paymenthistory");
                            JSONArray jsonArray = objectToJSONArray(temp);
                                if (totalNetAmount == 0 && jsonArray.length() == 0) {
                                    Toast.makeText(FeesActivityNew1.this, "Failed to access Fees data", Toast.LENGTH_SHORT).show();
                                } else {
//                                Toast.makeText(FeesActivity.this, "got Fees data", Toast.LENGTH_SHORT).show();
//                                FeesDetailsParse(response);
                                    setViewPager(response);
                                }
                           */

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
            public byte[] getBody()
            {

                Map<String, Integer> params = new HashMap<String, Integer>();

                /*params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");*/

                /*params.put("AMST_Id",sessionAMST_Id);
                params.put("MI_Id",session_MI_ID);*/

                /*params.put("AMST_Id",4221);
                params.put("MI_Id",5);*/

              /*  params.put("ASMAY_Id",3);  // Single history
                params.put("AMST_Id",944);  // 944
                params.put("MI_Id",5);*/

                params.put("AMST_Id",289);
                params.put("ASMAY_Id",3);
                params.put("MI_Id",5);

                /*params.put("ASMAY_Id",3);    // Multiple history
                params.put("AMST_Id",4378);
                params.put("MI_Id",5);*/

               /* params.put("ASMAY_Id",2);   // No paid history
                params.put("AMST_Id",136);
                params.put("MI_Id",4);*/

               /* params.put("ASMAY_Id", asmay_id);
                params.put("AMST_Id", amst_id);
                params.put("MI_Id", mi_id);*/

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


}
