package com.example.san.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.san.myapplication.Module.FeesPaid;
import com.example.san.myapplication.R;

import java.util.List;

/**
 * Created by vaps on 8/17/2017.
 */

public class FeesPaidAdapter extends RecyclerView.Adapter<FeesPaidAdapter.MyViewHolder> {

    private List<FeesPaid> feesPaidList;
    String rupeeSymbol;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtPaidAmount, txtPaidDate, txtPaidMode, txtbankname, txtReceiptNumber, txtDdCheqRef;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            txtDdCheqRef = (TextView) itemView.findViewById(R.id.txtDdCheqRef);
            txtReceiptNumber = (TextView) itemView.findViewById(R.id.txtReceiptno);
            txtbankname = (TextView) itemView.findViewById(R.id.txtBankName);
            txtPaidAmount = (TextView) itemView.findViewById(R.id.txtPaid);
            txtPaidDate = (TextView) itemView.findViewById(R.id.txtPaidDate);
            txtPaidMode = (TextView) itemView.findViewById(R.id.txtPaidMode);
        }
    }

    public FeesPaidAdapter(List<FeesPaid> feesPaidList) {
        this.feesPaidList = feesPaidList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_payment_history, parent, false);

        rupeeSymbol = itemView.getResources().getString(R.string.Rs);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
            FeesPaid feesPaid = feesPaidList.get(position);

            holder.txtPaidDate.setText(feesPaid.getPaidDate()+"");
            holder.txtPaidMode.setText(feesPaid.getPaidMode());
            holder.txtPaidAmount.setText(rupeeSymbol+feesPaid.getPaidAmount());
            holder.txtbankname.setText(feesPaid.getBankName());
            holder.txtReceiptNumber.setText(feesPaid.getReceipt_no());
            holder.txtDdCheqRef.setText(feesPaid.getDd_cheque_ref());
    }

    @Override
    public int getItemCount() {
        return feesPaidList.size();
    }
}
