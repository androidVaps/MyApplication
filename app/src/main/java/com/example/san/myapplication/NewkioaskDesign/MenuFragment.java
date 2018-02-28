package com.example.san.myapplication.NewkioaskDesign;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.san.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        view.findViewById(R.id.btn_stud_det).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                StudentDetailsFragment sf = new StudentDetailsFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.ll_main_content,sf,"main_container");
                ft.commit();
            }
        });

        view.findViewById(R.id.btn_fees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(MenuFragment.this,FeesActivityNew1.class));
                //   finish();
            }
        });

        view.findViewById(R.id.btn_attend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(MenuFragment.this,AttendanceActivity.class));
                //   finish();
//                startActivity(new Intent(MenuFragment.this,CaldroidSampleActivity.class));
          //      startActivity(new Intent(MenuFragment.this, CaldroidSampleActivity.class));
            }
        });

        view.findViewById(R.id.btn_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      startActivity(new Intent(MenuFragment.this, CalendarEventsActivity.class));
            }
        });

        view.findViewById(R.id.btn_exam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        startActivity(new Intent(MenuFragment.this, ExamActivityNew.class));
            }
        });

        view.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* finish();
                Toast.makeText(MenuFragment.this, "Loged out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MenuFragment.this, LoginActivityNew.class);
                startActivity(intent);*/
            }
        });
        
        return view;


    }

}
