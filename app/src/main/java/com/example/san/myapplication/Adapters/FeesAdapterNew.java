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

public class FeesAdapterNew extends RecyclerView.Adapter<FeesAdapterNew.MyViewHolder>
{
    private List<FeesPaid> feesPaidList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtPaidAmount, txtPaidDate, txtPaidMode;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            txtPaidAmount = (TextView) itemView.findViewById(R.id.txtPaid);
            txtPaidDate = (TextView) itemView.findViewById(R.id.txtPaidDate);
            txtPaidMode = (TextView) itemView.findViewById(R.id.txtPaidMode);
        }
    }

    public FeesAdapterNew(List<FeesPaid> feesPaidList) {
        this.feesPaidList = feesPaidList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_payment_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        FeesPaid feesPaid = feesPaidList.get(position);

        holder.txtPaidDate.setText(feesPaid.getPaidDate()+"");
        holder.txtPaidMode.setText("-"+feesPaid.getPaidMode());
        holder.txtPaidAmount.setText("Rs."+feesPaid.getPaidAmount());
    }

    @Override
    public int getItemCount() {
        return feesPaidList.size();
    }
}
