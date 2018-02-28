package com.example.san.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.san.myapplication.Adapters.FeesPaidAdapter;
import com.example.san.myapplication.Module.FeesPaid;
import com.example.san.myapplication.Module.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeesActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;
    public static String TAG = FeesActivity.class.getSimpleName();
    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentFeeDetails";

    private List<FeesPaid> paidList = new ArrayList<>();
    private FeesPaidAdapter feesPaidAdapter;

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.recyclerView) RecyclerView recyclerView;
    @InjectView(R.id.nextDueDate) TextView txtnextDueDate;
    @InjectView(R.id.nextDueAmount) TextView txtnextDueAmount;
    @InjectView(R.id.totalDueAmount) TextView txttotalDueAmount;
    @InjectView(R.id.paidDate) TextView txtpaidDate;
    @InjectView(R.id.paidAmount) TextView txtpaidAmount;
    @InjectView(R.id.totReceipt) TextView txtTotalReceipt;
    @InjectView(R.id.concision) TextView txtConcision;
    @InjectView(R.id.oncePay) TextView txtOncePay;
    @InjectView(R.id.anyTime) TextView txtAnyTime;
    @InjectView(R.id.payNextDue) Button btnNextPay;
    @InjectView(R.id.payTotalDue) Button btnTotalPay;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        ButterKnife.inject(this);

        toolbar.setTitle("Fees Details");

        setSupportActionBar(toolbar);

        /*TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "keepcalm.ttf");
        toolbarTitle.setTypeface(myTypeface);
     //   toolbarTitle.setText("Fees Details");
        toolbarTitle.setTextSize(20);*/

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Session session = AppSingleton.getInstance(getApplicationContext()).getInstance();
        int miid = session.getMI_Id();
        int amst_id = session.getAMST_Id();
        int asmay =session.getASMAY_Id();

       /* // Handling session
        if(miid == 0 || amst_id == 0 || asmay == 0)
        {
            SharedPreferences sharedPrefs = getSharedPreferences(LoginActivity.PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.clear();
            editor.commit();
            //  user="";

            //show login form
            finish();
            Toast.makeText(FeesActivity.this, "Successfully LogOut", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(FeesActivity.this, LoginActivityNew.class);
            startActivity(intent);
        }else {

            volleyJsonObjectFeesRequest(URL, miid, amst_id, asmay);

            feesPaidAdapter = new FeesPaidAdapter(paidList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(feesPaidAdapter);
        }*/

        volleyJsonObjectFeesRequest(URL, miid, amst_id, asmay);

        feesPaidAdapter = new FeesPaidAdapter(paidList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feesPaidAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
           //     startActivity(new Intent(FeesActivity.this,HomeActivity.class));
            }
        });
    }


    /***************************** SERVER REQUEST AND RESPONSE HANDLING **********************************/
    /*****************************************************************************************************/
    public void volleyJsonObjectFeesRequest(String url,final int mi_id,final int amst_id,final int asmay_id)
    {
        String  REQUEST_TAG = "com.fees.details";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d(TAG, response.toString());
                        try{
                            int pendingAmount = response.getInt("totalpending");
                            JSONArray jsonArray = response.getJSONArray("paymenthistory");

                            if(pendingAmount == 0 && jsonArray.length() == 0)
                            {
                                Toast.makeText(FeesActivity.this, "Failed to access Fees data", Toast.LENGTH_SHORT).show();
                            }else{
//                                Toast.makeText(FeesActivity.this, "got Fees data", Toast.LENGTH_SHORT).show();
                                FeesDetailsParse(response);
                            }
                        }catch (JSONException e){
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
        }){
            @Override
            public byte[] getBody() {

                Map<String, Integer> params = new HashMap<String, Integer>();

                /*params.put("username",userName);
                params.put("password",passWord);
                params.put("MI_Id",mi_id);
                params.put("Entry_Date","01/07/2016");*/

                /*params.put("AMST_Id",sessionAMST_Id);
                params.put("MI_Id",session_MI_ID);*/

                /*params.put("AMST_Id",4221);
                params.put("MI_Id",5);*/

               /* params.put("ASMAY_Id",3);    // Single history
                params.put("AMST_Id",944);
                params.put("MI_Id",5);*/

                /*params.put("ASMAY_Id",3);    // Multiple history
                params.put("AMST_Id",4378);
                params.put("MI_Id",5);*/

               /* params.put("ASMAY_Id",2);   // No paid history
                params.put("AMST_Id",136);
                params.put("MI_Id",4);*/

                params.put("ASMAY_Id",asmay_id);
                params.put("AMST_Id",amst_id);
                params.put("MI_Id",mi_id);

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

    public void FeesDetailsParse(JSONObject jsonObject)
    {
        try
        {
            int NextdueAmount = jsonObject.getInt("nextdueamount");
            String dueDate = jsonObject.getString("nextduedate");
            int totalDueAmount = jsonObject.getInt("totalpending");
            int totalReceipt = jsonObject.getInt("totalReceipt");
            int concision = jsonObject.getInt("totalconcession");
            int onceCarrier = jsonObject.getInt("totalonceinacarrier");
            int anyTime = jsonObject.getInt("totalanytime");

            Log.d(TAG,NextdueAmount+"****"+dueDate+"***"+totalDueAmount);

            if(NextdueAmount == 0)
            {
                btnNextPay.setVisibility(View.INVISIBLE);
            }

            if(totalDueAmount == 0)
            {
                btnTotalPay.setVisibility(View.INVISIBLE);
            }

            if(dueDate == "null"){
                txtnextDueDate.setText("");
            }else
            {
                txtnextDueDate.setText(dueDate);
            }

            // Bind values to views

            txtnextDueAmount.setText("Rs."+NextdueAmount+"");
            txttotalDueAmount.setText("Rs."+totalDueAmount);
            txtConcision.setText("Rs."+concision);
            txtTotalReceipt.setText("Rs."+totalReceipt);
            txtAnyTime.setText("Rs. "+anyTime);
            txtOncePay.setText("Rs. "+onceCarrier);

            int totalPaidAmount = 0;

            JSONArray jsonArray = jsonObject.getJSONArray("paymenthistory");

            int paidAmount;
            String paidDate = "";

            if(jsonArray.length() > 0)
            {
                Log.d(TAG,"Good array length");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);

                    paidAmount = object.getInt("FTP_Paid_Amt");
                    paidDate = formatDate(object.getString("FYP_Date"));
                    String paidBankCash = object.getString("FYP_Bank_Or_Cash");

                    if(paidBankCash.equals("C")){
                        paidBankCash = "-Cash";
                    }else if(paidBankCash.equals("B")){
                        paidBankCash = "-Bank";
                    }else if(paidBankCash.equals("O")){
                        paidBankCash = "-Online";
                    }

                    // Calculate Total Due Amount
                    totalPaidAmount = totalPaidAmount + paidAmount;

                    FeesPaid feesPaid = new FeesPaid(paidAmount,paidDate,paidBankCash);
                    paidList.add(feesPaid);

                    feesPaidAdapter.notifyDataSetChanged();

                    Log.d(TAG, paidAmount + "****" + paidDate + "***" + paidBankCash);
                }
                txtpaidDate.setText(paidDate);
                txtpaidAmount.setText("Rs."+totalPaidAmount);
            }else{
                txtpaidDate.setText("");
                txtpaidAmount.setText("0");
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    private String formatDate(String strDate)
    {
        String str_date = null;
        strDate = strDate.substring(0,9);
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
