package com.example.san.myapplication;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.myapplication.Module.FeesDetailsPay;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Santoshkumar on 9/19/2017.
 */

public class FeesActivityNew extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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
   // @InjectView(R.id.payAmount)
    Button payAmount;

    int totalPayable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_new);

        ButterKnife.inject(this);
        toolbar.setTitle("Fees Details");

        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

    }

    public void handleClicks(View v) {

        if (v == chk_term1) {
            if (chk_term1.isChecked())
                totalPayable = Integer.parseInt(txtTerm1.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm1.getText().toString());

            changeButtonStatus();
        }
        if (v == chk_term2) {
            if (chk_term2.isChecked())
                totalPayable = Integer.parseInt(txtTerm2.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm2.getText().toString());

            changeButtonStatus();
        }
        if (v == chk_term3) {
            if (chk_term3.isChecked())
                totalPayable = Integer.parseInt(txtTerm3.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm3.getText().toString());

            changeButtonStatus();
        }
        if (v == chk_term4) {
            if (chk_term4.isChecked())
                totalPayable = Integer.parseInt(txtTerm4.getText().toString()) + totalPayable;
            else
                totalPayable = totalPayable - Integer.parseInt(txtTerm4.getText().toString());

            changeButtonStatus();
        }

        if (v == payAmount) {
            if (totalPayable == 0) {
                payAmount.setText("Please select Term to pay");
            } else {
                Toast.makeText(this, "NExt page for payment", Toast.LENGTH_SHORT).show();
            }

        }

        if (v == imgTerm1) {
            int colorID = ((ColorDrawable) txtTerm1.getBackground()).getColor();
            customePopUp("I Term", colorID);
        }

        if (v == imgTerm2) {
            int colorID = ((ColorDrawable) txtTerm2.getBackground()).getColor();
            customePopUp("II Term", colorID);
        }

        if (v == imgTerm3) {
            int colorID = ((ColorDrawable) txtTerm3.getBackground()).getColor();
            customePopUp("III Term", colorID);
        }

        if (v == imgTerm4) {
            int colorID = ((ColorDrawable) txtTerm4.getBackground()).getColor();
            customePopUp("IV Term", colorID);
        }
    }

    public void changeButtonStatus() {
        if (totalPayable == 0) {
            payAmount.setText("Please select Term to pay");
        } else {
            payAmount.setText("Pay Amount " + getString(R.string.Rs) + " " + totalPayable);
        }
    }

    public void customePopUp(String term, int bgColor) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fees_custom_dialog);
        // dialog.setTitle("Title...");

        TextView txtNetAmount = (TextView) dialog.findViewById(R.id.netamount);
        TextView txtConcession = (TextView) dialog.findViewById(R.id.concession);
        TextView txtFine = (TextView) dialog.findViewById(R.id.fine);
        TextView txtPayable = (TextView) dialog.findViewById(R.id.payable);
        TextView txtTitle = (TextView) dialog.findViewById(R.id.title);

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

        txtTitle.setText(term);


                /*// set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_launcher);*/

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_cancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
