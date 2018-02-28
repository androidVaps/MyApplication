package com.example.san.myapplication.Adapters;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.TextView;

        import com.example.san.myapplication.AppSingleton;
        import com.example.san.myapplication.Module.FeesDetailsPay;
        import com.example.san.myapplication.Module.Session;
        import com.example.san.myapplication.R;


        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;

/**
 * Created by vaps on 16-Oct-17.
 */

public class FeesPayableAdapter extends RecyclerView.Adapter<FeesPayableAdapter.MyViewHolder>
{

    private List<FeesDetailsPay> feesDetailsPays;
    HashMap<Integer,ArrayList<Boolean>> feesCheckboxHandler;
    Context context;
    ArrayList<Boolean> checksUnchecksArrayList;
    int keyTermId;
    public int pay_amount = 0;
    Session session;
    int arryListCount;

    public FeesPayableAdapter(List<FeesDetailsPay> feesDetailsPays,Boolean isChecked)
    {
        this.feesDetailsPays = feesDetailsPays;
        feesCheckboxHandler = AppSingleton.getInstance(context).getFeesChecks();
        checksUnchecksArrayList = new ArrayList<Boolean>(Collections.nCopies(feesDetailsPays.size(),false));
        session = AppSingleton.getInstance(context).getInstance();

        /*feesCheckboxHandler = AppSingleton.getInstance(context).getFeesChecks();
        if(isChecked == true)
        checksUnchecksArrayList = new ArrayList<Boolean>(Collections.nCopies(feesDetailsPays.size(),true));
        if(isChecked == false)
        checksUnchecksArrayList = new ArrayList<Boolean>(Collections.nCopies(feesDetailsPays.size(),false));*/

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public CheckBox checkBoxGroup;
        public TextView txtFmgName, txtFmgAmount;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            checkBoxGroup = (CheckBox) itemView.findViewById(R.id.chekboxFeesDialog);
            txtFmgName = (TextView) itemView.findViewById(R.id.fmgName);
            txtFmgAmount = (TextView) itemView.findViewById(R.id.fmgAmount);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fees_dialog, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {

        final FeesDetailsPay feesDetailsPay = feesDetailsPays.get(position);
        Log.d("Test Position value", position + "");
        Log.d("group", feesDetailsPay.getFmgGroupName());
        Log.d("Amount", feesDetailsPay.getFmgAmount());

        final int amount = Integer.parseInt(feesDetailsPay.getFmgAmount());


        holder.txtFmgName.setText(feesDetailsPay.getFmgGroupName());
        holder.txtFmgAmount.setText(feesDetailsPay.getFmgAmount());

        keyTermId = Integer.parseInt(feesDetailsPay.getTermId());
//            checksUnchecksArrayList = new ArrayList<Boolean>(feesDetailsPays.size());

        if(feesCheckboxHandler.containsKey(keyTermId))
        {
            // Get Arraylist of boolean checks/un checks

            checksUnchecksArrayList = feesCheckboxHandler.get(keyTermId);
            holder.checkBoxGroup.setChecked(checksUnchecksArrayList.get(position));
        }
        else
        {
//                feesCheckboxHandler.put(keyTermId,checksUnchecksArrayList);
            holder.checkBoxGroup.setChecked(false);
        }


        // Displaying checked or un checked

          /*  if(feesDetailsPay.getChecked() == true)
            {
                holder.checkBoxGroup.setChecked(true);
            }*/

        //              holder.checkBoxGroup.setChecked(feesDetailsPay.getChecked());


        // Check box checked listener
        holder.checkBoxGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                checksUnchecksArrayList = new ArrayList<Boolean>(feesDetailsPays.size());


                if(isChecked == true)
                {
                    //    feesDetailsPay.getFmgAmount() = feesDetailsPay.getFmgAmount() + 0;
                   /* FeesDetailsPay detailsPay = feesDetailsPays.get(position);
                    detailsPay.setChecked(true);*/

                    pay_amount = amount + session.getPAYABLE_AMOUNT();


                    checksUnchecksArrayList.set(position,true);
//                    feesCheckboxHandler.put(keyTermId,checksUnchecksArrayList);
                    Log.d("Amount check",pay_amount+"");

                }
                else{
                    /*FeesDetailsPay detailsPay = feesDetailsPays.get(position);
                    detailsPay.setChecked(false);*/

                    pay_amount = session.getPAYABLE_AMOUNT() - amount;

                    checksUnchecksArrayList.set(position,false);
//                    feesCheckboxHandler.put(keyTermId,checksUnchecksArrayList);
                    Log.d("Amount uncheck",pay_amount+"");
                }

                feesCheckboxHandler.put(keyTermId,checksUnchecksArrayList);
                session.setPAYABLE_AMOUNT(pay_amount);
                Log.d("----PAY Amount---",pay_amount+"");

            }
        });


    }

    @Override
    public int getItemCount() {
        return feesDetailsPays.size();
    }
}
