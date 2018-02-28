package com.example.san.myapplication.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.myapplication.Adapters.FeesPaidAdapter;
import com.example.san.myapplication.FeesActivityNew1;
import com.example.san.myapplication.Module.FeesPaid;
import com.example.san.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeesPaidHistoryFragment extends Fragment
{
    public static final String TITLE = "Fees Paid History";
    public String TAG = FeesPaidHistoryFragment.class.getSimpleName();
    String myValue;
    JSONObject jsonObject;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.no_history)
    TextView txtNoHistory;

    private List<FeesPaid> paidList = new ArrayList<>();
    public FeesPaidAdapter feesPaidAdapter;

    public FeesPaidHistoryFragment() {
        // Required empty public constructor
    }

    public static FeesPaidHistoryFragment newInstance()
    {
        return new FeesPaidHistoryFragment();
    }

    public void setData(JSONObject jsonObject, Context context) {
        this.jsonObject = jsonObject;
   //     Toast.makeText(context, this.jsonObject+"" , Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Bundle bundle = this.getArguments();
        myValue =  bundle.getString("message");
        Toast.makeText(getActivity(), myValue , Toast.LENGTH_SHORT).show();*/
        // Inflate the layout for this fragment

        //   eventBus.register(this);

        View v = inflater.inflate(R.layout.fragment_fees_paid_history, container, false);
        ButterKnife.inject(this, v);
        parseJsonParsing(jsonObject);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feesPaidAdapter);
        return v;
    }

    public void parseJsonParsing(JSONObject jsonObject) {
        try {

            Object temp = jsonObject.get("paymenthistory");

            if (temp.equals(null))
            {
                //Toast.makeText(getActivity(), "No Paid history", Toast.LENGTH_SHORT).show();
                txtNoHistory.setVisibility(View.VISIBLE);

            }else {
                txtNoHistory.setVisibility(View.GONE);
                JSONArray jsonArray = jsonObject.getJSONArray("paymenthistory");

                if (jsonArray.length() > 0 && jsonArray != null) {
                    Log.d(TAG, "Good array length");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        int paidAmount = object.getInt("Paid_Amt");
                        String paidDate = formatDate(object.getString("Date"));
                        String paidBankCash = object.getString("Bank_Or_Cash");
                        String bankName = object.getString("Bank_Name");
                        String receiptNo = object.getString("Receipt_No");
                        String dd_cheq_no = object.getString("DD_Cheque_No");

                        if (paidBankCash.equals("C")) {
                            paidBankCash = "-Cash";
                        } else if (paidBankCash.equals("B")) {
                            paidBankCash = "-Bank";
                        } else if (paidBankCash.equals("O")) {
                            paidBankCash = "-Online";
                        } else if (paidBankCash.equals("S")) {
                            paidBankCash = "-Swipe";
                        } else if (paidBankCash.equals("R")) {
                            paidBankCash = "-RTGS/NEFT";
                        }

                        FeesPaid feesPaid = new FeesPaid(paidAmount, paidDate, paidBankCash, bankName, receiptNo, dd_cheq_no);
                        paidList.add(feesPaid);

                        feesPaidAdapter = new FeesPaidAdapter(paidList);

                        feesPaidAdapter.notifyDataSetChanged();

                        Log.d(TAG, paidAmount + "****" + paidDate + "***" + paidBankCash);
                    }

                }
            }
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
