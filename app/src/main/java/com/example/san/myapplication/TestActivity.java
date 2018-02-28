package com.example.san.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by vaps on 9/1/2017.
 */

public class TestActivity extends Activity
{
    int i = 0;

    LinearLayout ll_parent,ll_child1,ll_child2,ll;
    LinearLayout.LayoutParams llparamChild1Textview,llparamChild2Textview;
    ScrollView scrl;
    int colorsLight[] = {R.color.orange_400,R.color.teal_400,R.color.deep_purple_400,R.color.deep_purple_400,R.color.deep_purple_400};
    int colorsDark[] =  {R.color.orange_600,R.color.teal_600,R.color.deep_purple_600,R.color.deep_purple_600,R.color.deep_purple_600};
    int marks[] = {96,90,92,98,90};
    String subjects[] = {"Kannada","Mathematics","Science","Mathematics","Science"}; // ,"Hindi", "English"
    int childIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_exam);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout.LayoutParams llp_textView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        scrl = new ScrollView(this);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.setLayoutParams(llp);
 //       scrl.addView(ll);
        Button add_btn = new Button(this);
        add_btn.setText("Click to add TextViiews and EditTexts");
        ll.addView(add_btn);

       /* add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                i++;
                TextView tv = new TextView(getApplicationContext());
                tv.setText("Number" + i);
                tv.setGravity(Gravity.CENTER);
                ll.addView(tv);
                EditText et = new EditText(getApplicationContext());
                et.setLayoutParams(llp_textView);
                et.setText(i + ")");
                ll.addView(et);
            }
        });*/
 //       final LinearLayout ll_master = new LinearLayout(this);

        // Today
       /* final LinearLayout ll_parent = new LinearLayout(this);
        final LinearLayout ll_child1 = new LinearLayout(this);
        final LinearLayout ll_child2 = new LinearLayout(this);*/

       /* <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="0.6"
        android:orientation="vertical"
        android:layout_marginRight="3dp"
        android:layout_marginLeft="3dp"
        android:weightSum="3">*/

       /* LinearLayout.LayoutParams llparamMaster = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llparamParent.setMargins(dpToPx(3),dpToPx(0),dpToPx(3),dpToPx(0));
        ll_parent.setLayoutParams(llparamParent);
        ll_parent.setOrientation(LinearLayout.VERTICAL);
        ll_parent.setWeightSum(3.0f);*/


// Today
/*
        LinearLayout.LayoutParams llparamParent = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0,0.9f);
        llparamParent.setMargins(dpToPx(6),dpToPx(0),dpToPx(3),dpToPx(0));
        ll_parent.setLayoutParams(llparamParent);
        ll_parent.setOrientation(LinearLayout.VERTICAL);
        ll_parent.setWeightSum(3.0f);

        LinearLayout.LayoutParams llparamChild1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,2);
        ll_child1.setLayoutParams(llparamChild1);
        ll_child1.setOrientation(LinearLayout.HORIZONTAL);
        ll_child1.setWeightSum(3.0f);

        LinearLayout.LayoutParams llparamChild2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
        ll_child2.setLayoutParams(llparamChild2);
        ll_child2.setOrientation(LinearLayout.HORIZONTAL);
        ll_child2.setWeightSum(3.0f);

        LinearLayout.LayoutParams llparamChild1Textview = new LinearLayout.LayoutParams(0,dpToPx(80),1);

        LinearLayout.LayoutParams llparamChild2Textview = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
*/



        /*  TextView textView1 = new TextView(this);
        llparamChild1Textview.setMargins(dpToPx(6),0,dpToPx(6),0);
        textView1.setLayoutParams(llparamChild1Textview);
        textView1.setGravity(Gravity.CENTER);
        textView1.setText("90");
        textView1.setBackgroundColor(getResources().getColor(R.color.orange_400));
        textView1.setTextColor(getResources().getColor(R.color.white));
        textView1.setTextSize(50.0f);

        TextView textView2 = new TextView(this);
        llparamChild1Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView2.setLayoutParams(llparamChild1Textview);
        textView2.setGravity(Gravity.CENTER);
        textView2.setText("92");
        textView2.setBackgroundColor(getResources().getColor(R.color.teal_400));
        textView2.setTextColor(getResources().getColor(R.color.white));
        textView2.setTextSize(50.0f);

        TextView textView3 = new TextView(this);
        llparamChild1Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView3.setLayoutParams(llparamChild1Textview);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText("94");
        textView3.setBackgroundColor(getResources().getColor(R.color.deep_purple_400));
        textView3.setTextColor(getResources().getColor(R.color.white));
        textView3.setTextSize(50.0f);*/

      /*int colorsLight[] = {R.color.orange_400,R.color.teal_400,R.color.deep_purple_400};
      int colorsDark[] =  {R.color.orange_600,R.color.teal_600,R.color.deep_purple_600};
      int marks[] = {96,98,92};
      String subjects[] = {"Kannada","Mathematics","Science"};*/


// Today
      /*  for(int i = 0 ; i < 3 ; i++)
        {
            TextView textView = new TextView(this);
            textView.setId(i);
            llparamChild1Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
            textView.setLayoutParams(llparamChild1Textview);
            textView.setGravity(Gravity.CENTER);
            textView.setText(marks[i]+"");
            textView.setBackgroundColor(getResources().getColor(colorsLight[i]));
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(50.0f);

            ll_child1.addView(textView);

            TextView textView1 = new TextView(this);
            llparamChild2Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
            textView1.setLayoutParams(llparamChild2Textview);
            textView1.setGravity(Gravity.CENTER);
            textView1.setText(subjects[i]+"");
            textView1.setBackgroundColor(getResources().getColor(colorsDark[i]));
            textView1.setTextColor(getResources().getColor(R.color.white));
            ll_child2.addView(textView1);
        }*/




        int counter = 0;
        int countRows = 0;
        int countColumns = 3;


        if(subjects.length%3 == 0)
        {
            countRows = subjects.length/3;
        }else{
            countRows = subjects.length/3 + 1;
        }

        System.out.println("****"+countRows);

        String[][] matrix = new String[countRows][countColumns];

        for(int i=0;i<=2;i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                if((counter < subjects.length))
                {
                    matrix[i][j] = subjects[counter];
                    counter++;

                    //   System.out.println("count" + n);
                }
            }
          //  countRows++;
        }


        for (int i = 0; i < matrix.length; i++) {

  //          System.out.println("-----------creat layout----------------"+matrix.length);
            createParentLayout();

            for (int j = 0; j < matrix[i].length; j++) {
                {
                    if(matrix[i][j] == null) {
                        break ;
                    }
                    System.out.print(matrix[i][j] + "  ");

  //                  System.out.println("----------- content ----------------"+matrix[i].length);
                    createChildViews();
                    attachViews();
                }
            }
            System.out.println();
         //   attachViews();
        }


        ///  Working matrix LOgic

       /* String dummy[] = {"sa1", "sa2", "sa3", "sa4", "sa5", "sa6", "sa7", "sa8"};
        //  int Number[][]=new int[3][3];
        String[][] Number = new String[20][3];
        int n = 0;
        for (int i = 0; i <= 2; i++) {

            for (int j = 0; j <= 2; j++) {
                if ((n < dummy.length)) {
                    Number[i][j] = dummy[n];
                    n++;

                    //   System.out.println("count" + n);
                }
            }

        }

        for (int i = 0; i < Number.length; i++) {

//            System.out.println("-----------creat layout----------------");

            for (int j = 0; j < Number[i].length; j++) {
                {
                    if (Number[i][j] == null) {
                        break;
                    }
                    System.out.print(Number[i][j] + " ");


//                    System.out.println("----------- content ----------------");
                }
            }
            System.out.println();
        }
*/







        /*TextView textView4 = new TextView(this);
        llparamChild2Textview.setMargins(dpToPx(6),0,dpToPx(6),0);
        textView4.setLayoutParams(llparamChild2Textview);
        textView4.setGravity(Gravity.CENTER);
        textView4.setText("Hindi");
        textView4.setBackgroundColor(getResources().getColor(R.color.orange_600));
        textView4.setTextColor(getResources().getColor(R.color.white));


        TextView textView5 = new TextView(this);
        llparamChild2Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView5.setLayoutParams(llparamChild2Textview);
        textView5.setGravity(Gravity.CENTER);
        textView5.setText("English");
        textView5.setBackgroundColor(getResources().getColor(R.color.teal_600));
        textView5.setTextColor(getResources().getColor(R.color.white));


        TextView textView6 = new TextView(this);
        llparamChild2Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView6.setLayoutParams(llparamChild2Textview);
        textView6.setGravity(Gravity.CENTER);
        textView6.setText("Kannada");
        textView6.setBackgroundColor(getResources().getColor(R.color.deep_purple_600));
        textView6.setTextColor(getResources().getColor(R.color.white));*/


      /*  for(int j = 0 ; j < 3 ; j++)
        {
            TextView textView = new TextView(this);
            llparamChild2Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
            textView.setLayoutParams(llparamChild2Textview);
            textView.setGravity(Gravity.CENTER);
            textView.setText(subjects[j]+"");
            textView.setBackgroundColor(getResources().getColor(colorsDark[j]));
            textView.setTextColor(getResources().getColor(R.color.white));
            ll_child2.addView(textView);
        }*/



        /*ll_child1.addView(textView1);
        ll_child1.addView(textView2);
        ll_child1.addView(textView3);*/

        /*ll_child2.addView(textView4);
        ll_child2.addView(textView5);
        ll_child2.addView(textView6);*/


// Today
        /*ll_parent.addView(ll_child1);
        ll_parent.addView(ll_child2);

      //  scrl.addView(ll_parent);
        ll.addView(ll_parent);*/




        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

// Today

        this.setContentView(scrl);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }



    public void createParentLayout()
    {
        ll_parent = new LinearLayout(this);
        ll_child1 = new LinearLayout(this);
        ll_child2 = new LinearLayout(this);

        LinearLayout.LayoutParams llparamParent = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0,0.6f);
        llparamParent.setMargins(dpToPx(6),dpToPx(0),dpToPx(3),dpToPx(9));
        ll_parent.setLayoutParams(llparamParent);
        ll_parent.setOrientation(LinearLayout.VERTICAL);
        ll_parent.setWeightSum(3.0f);

        LinearLayout.LayoutParams llparamChild1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,2);
        ll_child1.setLayoutParams(llparamChild1);
        ll_child1.setOrientation(LinearLayout.HORIZONTAL);
        ll_child1.setWeightSum(3.0f);

        LinearLayout.LayoutParams llparamChild2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
        ll_child2.setLayoutParams(llparamChild2);
        ll_child2.setOrientation(LinearLayout.HORIZONTAL);
        ll_child2.setWeightSum(3.0f);


    }

    public void createChildViews()
    {
        llparamChild1Textview = new LinearLayout.LayoutParams(0,dpToPx(80),1);

        llparamChild2Textview = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);

        TextView textView = new TextView(this);
        llparamChild1Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView.setLayoutParams(llparamChild1Textview);
        textView.setGravity(Gravity.CENTER);
        textView.setText(marks[childIndex]+"");
        textView.setBackgroundColor(getResources().getColor(colorsLight[childIndex]));
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTextSize(50.0f);
        if(textView.getParent()!=null)
            ((ViewGroup)textView.getParent()).removeView(textView);
        ll_child1.addView(textView);

        TextView textView1 = new TextView(this);
        llparamChild2Textview.setMargins(dpToPx(3),0,dpToPx(6),0);
        textView1.setLayoutParams(llparamChild2Textview);
        textView1.setGravity(Gravity.CENTER);
        textView1.setText(subjects[childIndex]+"");
        textView1.setBackgroundColor(getResources().getColor(colorsDark[childIndex]));
        textView1.setTextColor(getResources().getColor(R.color.white));
        if(textView1.getParent()!=null)
            ((ViewGroup)textView1.getParent()).removeView(textView1);
        ll_child2.addView(textView1);

        childIndex++ ;
    }

    public void attachViews()
    {
        if(ll_child1.getParent()!=null)
            ((ViewGroup)ll_child1.getParent()).removeView(ll_child1);

        ll_parent.addView(ll_child1);

        if(ll_child2.getParent()!=null)
            ((ViewGroup)ll_child2.getParent()).removeView(ll_child2);

        ll_parent.addView(ll_child2);

        if(ll_parent.getParent()!=null)
            ((ViewGroup)ll_parent.getParent()).removeView(ll_parent);

        ll.addView(ll_parent);

        if(ll.getParent()!=null)
            ((ViewGroup)ll.getParent()).removeView(ll);

        scrl.addView(ll);

    //    this.setContentView(scrl);
    }
}

