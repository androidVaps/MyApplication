package com.example.san.myapplication.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.myapplication.Adapters.FeesPayableAdapter;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Module.FeesDetailsPay;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.PaymentGateway.PayUwebView;
import com.example.san.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeesDetailsFragment extends Fragment
{

    public static final String TITLE = "Fees Details";
    @InjectView(R.id.term1amt)
    TextView txtTerm1;
    @InjectView(R.id.term2amt)
    TextView txtTerm2;
    @InjectView(R.id.term3amt)
    TextView txtTerm3;
    @InjectView(R.id.term4amt)
    TextView txtTerm4;
    @InjectView(R.id.imgViewTerm1)
    ImageView imgTerm1;
    @InjectView(R.id.imgViewTerm2)
    ImageView imgTerm2;
    @InjectView(R.id.imgViewTerm3)
    ImageView imgTerm3;
    @InjectView(R.id.imgViewTerm4)
    ImageView imgTerm4;
    @InjectView(R.id.checkBox1)
    CheckBox chk_term1;
    @InjectView(R.id.checkBox2)
    CheckBox chk_term2;
    @InjectView(R.id.checkBox3)
    CheckBox chk_term3;
    @InjectView(R.id.checkBox4)
    CheckBox chk_term4;
    @InjectView(R.id.netAmount)
    TextView txtNetAmount;
    @InjectView(R.id.paidAmount)
    TextView txtPaidAmount;
    @InjectView(R.id.balance)
    TextView txtBalance;
    @InjectView(R.id.concession)
    TextView txtConcession;
    @InjectView(R.id.payAmount)
    Button payAmount;
    int totalPayable;
    JSONObject jsonObject;
    JSONObject objectTerm1, objectTerm2, objectTerm3, objectTerm4;
    FeesDetailsPay detailsPay;
    List<FeesDetailsPay> detailsPaysList;

    HashMap<Integer,ArrayList<Boolean>> feesCheckboxHandler;
    ArrayList<Boolean> checksUnchecksArrayList;
    String termID = null;
    int payble = 0;
    Session session ;
    public FeesDetailsFragment() {
        // Required empty public constructor
    }

    public static FeesDetailsFragment newInstance() {

        return new FeesDetailsFragment();
    }

    public void setData(JSONObject jsonObject, Context context) {
        this.jsonObject = jsonObject;
        //       Toast.makeText(context, this.jsonObject+"" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fees_details, container, false);
        ButterKnife.inject(this, view);

        session = AppSingleton.getInstance(getActivity()).getInstance();

        if (jsonObject != null) {
            parseJSONData(jsonObject);
        }


  /*    detailsPay = new FeesDetailsPay("1", "School Fees1", "46001", false);
        detailsPaysList.add(detailsPay);
        detailsPay = new FeesDetailsPay("2", "School Fees2", "46002", false);
        detailsPaysList.add(detailsPay);
        detailsPay = new FeesDetailsPay("3", "School Fees3", "46003", false);
        detailsPaysList.add(detailsPay);    */

        return view;
    }

    @OnClick({R.id.imgViewTerm1, R.id.imgViewTerm2, R.id.imgViewTerm3, R.id.imgViewTerm4,
            R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4, R.id.payAmount})
    public void clickEvent(View v) {
        // Toast.makeText(getActivity(), "Clicked Term 1", Toast.LENGTH_SHORT).show();
        if (v == imgTerm1) {
            int colorID = ((ColorDrawable) txtTerm1.getBackground()).getColor();
            customePopUp(colorID, objectTerm1, chk_term1.isChecked());
        }

        if (v == imgTerm2) {
            int colorID = ((ColorDrawable) txtTerm2.getBackground()).getColor();
            customePopUp(colorID, objectTerm2, chk_term2.isChecked());
        }

        if (v == imgTerm3) {
            int colorID = ((ColorDrawable) txtTerm3.getBackground()).getColor();
            customePopUp(colorID, objectTerm3, chk_term3.isChecked());
        }

        if (v == imgTerm4) {
            int colorID = ((ColorDrawable) txtTerm4.getBackground()).getColor();
            customePopUp(colorID, objectTerm4, chk_term4.isChecked());
        }

        if (v == chk_term1) {
            if (chk_term1.isChecked())
                totalPayable = Integer.parseInt(txtTerm1.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm1.getText().toString());

            changeButtonStatus(totalPayable);
        }
        if (v == chk_term2) {
            if (chk_term2.isChecked())
                totalPayable = Integer.parseInt(txtTerm2.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm2.getText().toString());

            changeButtonStatus(totalPayable);
        }
        if (v == chk_term3) {
            if (chk_term3.isChecked())
                totalPayable = Integer.parseInt(txtTerm3.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm3.getText().toString());

            changeButtonStatus(totalPayable);
        }
        if (v == chk_term4) {
            if (chk_term4.isChecked())
                totalPayable = Integer.parseInt(txtTerm4.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm4.getText().toString());

            changeButtonStatus(totalPayable);
        }

        if (v == payAmount) {
            if (totalPayable == 0) {
                payAmount.setText("Please select Term to pay");
            } else {
             //   Toast.makeText(getActivity(), "Next page for payment", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Intent i = new Intent(getActivity(), PayUwebView.class);
                startActivity(i);
            }

        }
    }

    public void changeButtonStatus(int totalPayable) {
        if (totalPayable == 0) {
            payAmount.setText("Please select Term to pay");
        } else {
            payAmount.setText("Pay Amount " + getString(R.string.Rs) + " " + totalPayable);
        }
    }

    public void customePopUp(int bgColor, JSONObject jsonObject, Boolean isChecked)
    {
        // Custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fees_custom_dialog);
        // dialog.setTitle("Title...");

        TextView txtNetAmount = (TextView) dialog.findViewById(R.id.netamount);
        TextView txtPaidAmount = (TextView) dialog.findViewById(R.id.paidamount);
        TextView txtConcession = (TextView) dialog.findViewById(R.id.concession);
        TextView txtFine = (TextView) dialog.findViewById(R.id.fine);
        TextView txtPayable = (TextView) dialog.findViewById(R.id.payable);
        TextView txtTitle = (TextView) dialog.findViewById(R.id.title);

        detailsPaysList = new ArrayList<FeesDetailsPay>();
        try {
            termID = jsonObject.getString("FMT_Id");
            JSONArray jsonArray = jsonObject.getJSONArray("customgroup");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                detailsPay = new FeesDetailsPay(termID,object.getString("FMG_Id"), object.getString("FMGG_GroupName"), object.getString("pending"), isChecked);
                detailsPaysList.add(detailsPay);
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            }
        }

      /*  if(isChecked == true)
        {
            feesCheckboxHandler = AppSingleton.getInstance(getActivity()).getFeesChecks();
            checksUnchecksArrayList = new ArrayList<Boolean>(Collections.nCopies(detailsPaysList.size(), true));
            feesCheckboxHandler.put(Integer.parseInt(fmtId),checksUnchecksArrayList);
        }else if(isChecked == false) {
            feesCheckboxHandler = AppSingleton.getInstance(getActivity()).getFeesChecks();
            checksUnchecksArrayList = new ArrayList<Boolean>(Collections.nCopies(detailsPaysList.size(), false));
            feesCheckboxHandler.put(Integer.parseInt(fmtId),checksUnchecksArrayList);
        }*/

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewFeesDialog);
        FeesPayableAdapter payableAdapter = new FeesPayableAdapter(detailsPaysList,isChecked);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(payableAdapter);
        payableAdapter.notifyDataSetChanged();

        try
        {
            txtNetAmount.setText(jsonObject.getInt("TotalTermamount") + "");
            txtPaidAmount.setText(jsonObject.getInt("PaidAmount") + "");
            txtConcession.setText(jsonObject.getInt("Concession") + "");
            txtFine.setText(jsonObject.getInt("FineAmount") + "");
            txtPayable.setText(jsonObject.getInt("pending") + "");
            txtTitle.setText(jsonObject.getString("FMT_Name") + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        LinearLayout ll1 = (LinearLayout) dialog.findViewById(R.id.layout1);
        LinearLayout ll2 = (LinearLayout) dialog.findViewById(R.id.layout2);
        LinearLayout ll3 = (LinearLayout) dialog.findViewById(R.id.layout3);
        LinearLayout ll4 = (LinearLayout) dialog.findViewById(R.id.layout4);
        LinearLayout ll5 = (LinearLayout) dialog.findViewById(R.id.layout5);

        ll1.setBackgroundColor(bgColor);
        ll2.setBackgroundColor(bgColor);
        ll3.setBackgroundColor(bgColor);
        ll4.setBackgroundColor(bgColor);
        ll5.setBackgroundColor(bgColor);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_cancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                FeesDetailsPay detailsPay;

               /* for (int i = 0; i < detailsPaysList.size(); i++)
                {
                    boolean b = detailsPaysList.get(i).getChecked();
                    if (b == true)
                    {
                        payble = Integer.parseInt(detailsPaysList.get(i).getFmgAmount()) + payble;
                        // detailsPaysList.get(i).setChecked(false);
                    }
                }

                Toast.makeText(getActivity(), "" + payble, Toast.LENGTH_SHORT).show();*/

                feesCheckboxHandler = AppSingleton.getInstance(getActivity()).getFeesChecks();
                checksUnchecksArrayList = feesCheckboxHandler.get(Integer.parseInt(termID));

               /* if(checksUnchecksArrayList != null)
                {
                    for (int i = 0; i < checksUnchecksArrayList.size(); i++)
                    {
                        boolean status = checksUnchecksArrayList.get(i);
                        if (status == true)
                        {
                            payble = Integer.parseInt(detailsPaysList.get(i).getFmgAmount()) + payble;

                        }else if(status == false){
                            payble = payble - Integer.parseInt(detailsPaysList.get(i).getFmgAmount());
                        }
                    }
                }
                Toast.makeText(getActivity(), "" + payble, Toast.LENGTH_SHORT).show();
                totalPayable = payble;
                changeButtonStatus(payble);*/

                changeButtonStatus(session.getPAYABLE_AMOUNT());
            }
        });
        dialog.show();
    }

    public void parseJSONData(JSONObject jsonObject) {
        try {
            txtNetAmount.setText(jsonObject.getInt("TotalNetAmount") + "");
            txtPaidAmount.setText(jsonObject.getInt("TotalPaidAmount") + "");
            txtBalance.setText(jsonObject.getInt("Totalpending") + "");
            txtConcession.setText(jsonObject.getInt("Totalconcession") + "");

            JSONArray jsonArray = jsonObject.getJSONArray("Feeterm_array");
            objectTerm1 = jsonArray.getJSONObject(0);
            objectTerm2 = jsonArray.getJSONObject(1);
            objectTerm3 = jsonArray.getJSONObject(2);
            objectTerm4 = jsonArray.getJSONObject(3);

            chk_term1.setText(objectTerm1.getString("FMT_Name") + "");
            chk_term2.setText(objectTerm2.getString("FMT_Name") + "");
            chk_term3.setText(objectTerm3.getString("FMT_Name") + "");
            chk_term4.setText(objectTerm4.getString("FMT_Name") + "");

            txtTerm1.setText(objectTerm1.getString("pending"));
            txtTerm2.setText(objectTerm2.getString("pending"));
            txtTerm3.setText(objectTerm3.getString("pending"));
            txtTerm4.setText(objectTerm4.getString("pending"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
