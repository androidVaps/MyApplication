package com.example.san.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.san.myapplication.Module.Exam;
import com.example.san.myapplication.R;

import java.util.List;

/**
 * Created by vaps on 9/9/2017.
 */

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder>
{
    private List<Exam> examList;
    private Context context1;
    int[] marksBg;
    int[] subjectBg;
    int colorCodeLight[] = {R.color.orange_400,R.color.teal_400,R.color.deep_purple_400,R.color.deep_orange_400,R.color.light_blue_400,
            R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400
    ,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400,
            R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400,
            R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400, R.color.ambar_100,R.color.purple_100,R.color.cyan_400,R.color.cyan_400};
    int colorCodeDeep[] = {R.color.orange_600,R.color.teal_600,R.color.deep_purple_600,R.color.deep_orange_600,R.color.light_blue_600,
            R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600
    ,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,
            R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600,R.color.ambar_200,R.color.purple_200,R.color.cyan_600,R.color.cyan_600};


    public ExamAdapter( Context context,List<Exam> examList)
    {
        this.examList = examList;
        context1 = context;

        marksBg = context1.getResources().getIntArray(R.array.marksBoxBackground);
        subjectBg = context1.getResources().getIntArray(R.array.subjectBoxBackground);
    }



    @Override
    public ExamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
     //   View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_exam_details, null); row_exam_details_new.xml
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_exam_details_new, null);
        ExamAdapter.ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExamAdapter.ViewHolder holder, int position)
    {
        holder.tv_subName.setText(examList.get(position).getSubName());
   //     holder.tv_subName.setBackgroundColor(context1.getResources().getColor(colorCodeDeep[position]));
    //    holder.tv_subName.setBackgroundColor(subjectBg[position]);
        holder.tv_subMarks.setText(examList.get(position).getObtainMarks()+"");
    //    holder.tv_subMarks.setBackgroundColor(marksBg[position]);
 //       holder.tv_subMarks.setBackgroundColor(context1.getResources().getColor(colorCodeLight[position]));
    }

    @Override
    public int getItemCount()
    {
        return examList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_subMarks;
        private TextView tv_subName;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_subMarks = (TextView) itemView.findViewById(R.id.subMarks);
            tv_subName = (TextView) itemView.findViewById(R.id.subNames);
        }
    }
}
