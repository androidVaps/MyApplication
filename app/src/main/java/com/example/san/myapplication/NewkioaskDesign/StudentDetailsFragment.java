package com.example.san.myapplication.NewkioaskDesign;


import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.san.myapplication.AppSingleton;
import com.example.san.myapplication.Module.Session;
import com.example.san.myapplication.PrinterConfig.Constant;
import com.example.san.myapplication.PrinterConfig.Utils;
import com.example.san.myapplication.R;
import com.example.san.myapplication.StudentDeatilsActivity;
import com.printsdk.cmd.PrintCmd;
import com.printsdk.usbsdk.UsbDriver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailsFragment extends Fragment
{
    private static final String URL = "http://stagingmobileapp.azurewebsites.net/api/login/StudentDetails";
    public static String TAG = StudentDeatilsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    int sessionAMST_Id = 0;
    int session_MI_ID = 0;
    int session_ASMAY_Id = 0;

    ImageView imageView;
    TextView edit_stud_name;
    TextView edit_stud_regno;
    TextView edit_stud_doa;
    TextView edit_stud_dob;
    TextView edit_stud_contact;
    TextView edit_stud_email,edit_classname,edit_sectionname;
    ImageView mPrintTicket;
  //  Context mContext;
    String stud_name,stud_regNo,stud_admNo,stud_dob,stud_contactNo,stud_mailId,class_name,section_name;


    private UsbManager mUsbManager;
    UsbDriver mUsbDriver;
    UsbDevice mUsbDev1;
    UsbDevice mUsbDev2;

    private static final String ACTION_USB_PERMISSION =  "com.usb.sample.USB_PERMISSION";
    private String title = "", strData = "", num = "",codeStr = "",details = "";
    private int cutter = 0;
    static int Number = 1000;

    public static String TITLE_US;
    public static String DETAILS_TXT;

    public StudentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_details, container, false);
        ImageView img_back_button = (ImageView) view.findViewById(R.id.img_backIcon);
        mPrintTicket = (ImageView) view.findViewById(R.id.img_print);

        /********** Printer Config initialization **********/
        //       mContext = this;
        getUsbDriverService();
        getMsgByLanguage();

        mPrintTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printFinalString();
                getPrintTicketData(mUsbDev1);

            }
        });

        /***************************************************/



        img_back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), MainActivityKioask.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });



        imageView = (ImageView) view.findViewById(R.id.image_stud);
     //   edit_stud_name = (TextView) view.findViewById(R.id.edit_stud_name);
        edit_stud_regno = (TextView) view.findViewById(R.id.edit_stud_regno);
        edit_stud_doa = (TextView) view.findViewById(R.id.edit_stud_doa);
        edit_stud_dob = (TextView) view.findViewById(R.id.edit_stud_dob);

        edit_stud_contact = (TextView) view.findViewById(R.id.edit_stud_contact);
        edit_stud_email = (TextView) view.findViewById(R.id.edit_stud_email);
        edit_classname = (TextView) view.findViewById(R.id.edit_class);
        edit_sectionname = (TextView) view.findViewById(R.id.edit_section);

        stud_name = getArguments().getString("stud_name");
        stud_regNo = getArguments().getString("stud_regno");
        stud_admNo = getArguments().getString("stud_admNo");
        stud_dob = getArguments().getString("stud_dob");
        stud_contactNo = getArguments().getString("stud_contactNo");
        stud_mailId = getArguments().getString("stud_mailId");
        class_name = getArguments().getString("class_name");
        section_name = getArguments().getString("section_name");

        edit_stud_regno.setText(stud_regNo);
        edit_stud_doa.setText(stud_admNo);
        edit_stud_dob.setText(stud_dob);
        edit_stud_contact.setText(stud_contactNo);  // Integer.toString(stud_contactNo)
        edit_stud_email.setText(stud_mailId);
        edit_classname.setText(class_name);
        edit_sectionname.setText(section_name);

        Session session = AppSingleton.getInstance(getActivity()).getInstance();

        sessionAMST_Id = session.getAMST_Id();
        session_MI_ID = session.getMI_Id();
        session_ASMAY_Id = session.getASMAY_Id();

        //    Toast.makeText(StudentDeatilsActivity.this,sessionAMST_Id+"+++++++"+session_MI_ID, Toast.LENGTH_SHORT).show();

  //      volleyJsonObjectRequest(URL, sessionAMST_Id, session_MI_ID, session_ASMAY_Id);

  //      volleyJsonObjectRequest(URL, 944, 5, 3);


        return view;
    }


    // Handling Image response
    public void volleyImageLoader(String url) {
        ImageLoader imageLoader = AppSingleton.getInstance(getActivity()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    /*LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    showDialogView = li.inflate(R.layout.show_dialog, null);
                    outputImageView = (ImageView)showDialogView.findViewById(R.id.image_view_dialog);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setView(showDialogView);
                    alertDialogBuilder
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setCancelable(false)
                            .create();*/
                    imageView.setImageBitmap(response.getBitmap());
                    //   alertDialogBuilder.show();
                }
            }
        });
    }

    public void profile_image(String imgUrl) {
        Glide.with(this)
                .load(imgUrl)
                .asBitmap()
                .placeholder(R.drawable.circle_crop)
                .error(R.mipmap.ic_launcher_round)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getActivity().getResources(),
                                Bitmap.createScaledBitmap(resource, 50, 50, false));
                        drawable.setCircular(true);
                        imageView.setImageDrawable(drawable);
                    }
                });
    }

    private String formatDate(String strDate) {
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

    /***************************************** Printer configuration ************************************/






    public static String STRDATA_US = "North karnataka \n\n" + "Full meals  50.00";

    // Get UsbDriver(UsbManager) service
    private void getUsbDriverService() {
        mUsbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        mUsbDriver = new UsbDriver(mUsbManager, getActivity());
        PendingIntent permissionIntent1 = PendingIntent.getBroadcast(getActivity(), 0,
                new Intent(ACTION_USB_PERMISSION), 0);
        mUsbDriver.setPermissionIntent(permissionIntent1);

        // Broadcast listen for new devices
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        getActivity().registerReceiver(mUsbReceiver, filter);

        //USB线已经连接
        for (UsbDevice device : mUsbManager.getDeviceList().values()) {
            if((device.getProductId()==8211 && device.getVendorId()==1305)
                    ||(device.getProductId()==8213 && device.getVendorId()==1305))
            {
                if(mUsbDriver.usbAttached(device))
                {
                    // 打开设备
                    if (mUsbDriver.openUsbDevice(device)) {
                        if(device.getProductId()==8211)
                            mUsbDev1 = device;
                        else
                            mUsbDev2 = device;
                        Toast.makeText(getActivity(), getString(R.string.USB_Driver_Success),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.USB_Driver_Failed),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /*
     *  BroadcastReceiver when insert/remove the device USB plug into/from a USB port
     *
     */
    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                if(mUsbDriver.usbAttached(intent))
                {
                    UsbDevice device = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if(mUsbDriver.openUsbDevice(device))
                    {
                        if(device.getProductId()==8211)
                            mUsbDev1 = device;
                        else
                            mUsbDev2 = device;
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent
                        .getParcelableExtra(UsbManager.EXTRA_DEVICE);

                mUsbDriver.closeUsbDevice(device);
                if(device.getProductId()==8211)
                    mUsbDev1 = null;
                else
                    mUsbDev2 = null;
            } else if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this)
                {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                    {
                        if(mUsbDriver.openUsbDevice(device))
                        {
                            if(device.getProductId()==8211)
                                mUsbDev1 = device;
                            else
                                mUsbDev2 = device;
                        }
                    }
                    else {
                        Toast.makeText(getActivity(),"permission denied for device",
                                Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };


    private int checkStatus(int iStatus)
    {
        int iRet = -1;

        StringBuilder sMsg = new StringBuilder();

        switch (iStatus) {
            case 0:
                sMsg.append(normal);       // 正常
                iRet = 0;
                break;
            case 8:
                sMsg.append(paperWillExh); // 纸将尽
                iRet = 0;
                break;
            case 3:
                sMsg.append(printerHeadOpen); //打印头打开
                break;
            case 4:
                sMsg.append(cutterNotReset);
                break;
            case 5:
                sMsg.append(printHeadOverheated);
                break;
            case 6:
                sMsg.append(blackMarkError);
                break;
            case 7:
                sMsg.append(paperExh);     // 纸尽==缺纸
                break;
            case 1:
                sMsg.append(notConnectedOrNotPopwer);
                break;
            default:
                sMsg.append(abnormal);     // 异常
                break;
        }

        //      ShowMessage(sMsg.toString());
        return iRet;

    }


    private void getPrintTicketData(UsbDevice usbDev)
    {
        getStrDataByLanguage();
        int iStatus = getPrinterStatus(usbDev);
        if(checkStatus(iStatus)!=0)
            return;
        try
        {
            mUsbDriver.write(PrintCmd.SetClean(),usbDev);  // 初始化，清理缓存
            // 小票标题   // BOLD WITH TEXT
            mUsbDriver.write(PrintCmd.SetUnderline(2),usbDev);
            mUsbDriver.write(PrintCmd.SetBold(0),usbDev);
            mUsbDriver.write(PrintCmd.SetAlignment(1),usbDev);
            mUsbDriver.write(PrintCmd.SetSizetext(1, 1),usbDev);
            mUsbDriver.write(PrintCmd.PrintString(title, 0),usbDev);

            //       mUsbDriver.write(PrintCmd.SetAlignment(0),usbDev);
            //       mUsbDriver.write(PrintCmd.SetSizetext(1, 1),usbDev);
            // 小票号码   // BOLD with number
            mUsbDriver.write(PrintCmd.SetUnderline(0),usbDev);
            mUsbDriver.write(PrintCmd.SetBold(0),usbDev);
            mUsbDriver.write(PrintCmd.SetAlignment(0),usbDev);
            mUsbDriver.write(PrintCmd.SetSizetext(0, 0),usbDev);
            mUsbDriver.write(PrintCmd.PrintString(details, 0),usbDev);
            //    mUsbDriver.write(PrintCmd.SetBold(0),usbDev);



            // 小票主要内容   // plane text
            //       mUsbDriver.write(PrintCmd.SetAlignment(2),usbDev);
            //       mUsbDriver.write(PrintCmd.SetSizetext(0, 0),usbDev);
            //       mUsbDriver.write(PrintCmd.PrintString(strData, 0),usbDev);
            //       mUsbDriver.write(PrintCmd.PrintFeedline(2),usbDev); // 打印走纸2行
            //       mUsbDriver.write(PrintCmd.SetSizetext(0, 0),usbDev);



		/*	// 二维码    // Qrcode
			mUsbDriver.write(PrintCmd.SetAlignment(1),usbDev);
			mUsbDriver.write(PrintCmd.PrintQrcode(codeStr, 25, 6, 1),usbDev);           // 【1】MS-D347,13 52指令二维码接口，环绕模式1
//			mUsbDriver.write(PrintCmd.PrintQrcode(codeStr, 12, 2, 0),usbDev);           // 【2】MS-D245,MSP-100二维码，左边距、size、环绕模式0
//			mUsbDriver.write(PrintCmd.PrintQrCodeT500II(QrSize,Constant.WebAddress_zh),usbDev);// 【3】MS-532II+T500II二维码接口
			mUsbDriver.write(PrintCmd.PrintFeedline(2),usbDev);
			mUsbDriver.write(PrintCmd.SetAlignment(0),usbDev);
			// 日期时间  // Date time
			mUsbDriver.write(PrintCmd.SetAlignment(2),usbDev);
			mUsbDriver.write(PrintCmd.PrintString(sdf.format(new Date()).toString()
					+ "\n\n", 1),usbDev);
			mUsbDriver.write(PrintCmd.SetAlignment(0),usbDev);
			// 一维条码  // bar code
			mUsbDriver.write(PrintCmd.SetAlignment(1),usbDev);
			mUsbDriver.write(PrintCmd.Print1Dbar(2, 100, 0, 2, 10, "A12345678Z"),usbDev);// 一维条码打印
			mUsbDriver.write(PrintCmd.SetAlignment(0),usbDev);*/
            // 走纸换行、切纸、清理缓存
            SetFeedCutClean(cutter,usbDev);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    // Take the paper wrap, cut paper, clean up the cache
    private void SetFeedCutClean(int iMode,UsbDevice usbDev) {
        mUsbDriver.write(PrintCmd.PrintFeedline(5),usbDev);      // 走纸换行
        mUsbDriver.write(PrintCmd.PrintCutpaper(iMode),usbDev);  // 切纸类型
        mUsbDriver.write(PrintCmd.SetClean(),usbDev);            // 清理缓存
    }

    // print data here------------------------------
    // Get the test text based on the system language
    private void getStrDataByLanguage()
    {
        //   codeStr = etWrite.getText().toString().trim();
        codeStr = "";
        if("".equalsIgnoreCase(codeStr))
            codeStr = Constant.WebAddress;
        if(Utils.isZh(getActivity())){
            title = Constant.TITLE_CN;
            strData = Constant.STRDATA_CN;
        }else {
            title = TITLE_US;
            strData = STRDATA_US;
            details = DETAILS_TXT;
        }
        num = String.valueOf(Number) + "\n\n";
        Number++;
    }

    private int getPrinterStatus(UsbDevice usbDev)
    {
        int iRet = -1;

        byte[] bRead1 = new byte[1];
        byte[] bWrite1 = PrintCmd.GetStatus1();
        if(mUsbDriver.read(bRead1,bWrite1,usbDev)>0)
        {
            iRet = PrintCmd.CheckStatus1(bRead1[0]);
        }

        if(iRet!=0)
            return iRet;

        byte[] bRead2 = new byte[1];
        byte[] bWrite2 = PrintCmd.GetStatus2();
        if(mUsbDriver.read(bRead2,bWrite2,usbDev)>0)
        {
            iRet = PrintCmd.CheckStatus2(bRead2[0]);
        }

        if(iRet!=0)
            return iRet;

        byte[] bRead3 = new byte[1];
        byte[] bWrite3 = PrintCmd.GetStatus3();
        if(mUsbDriver.read(bRead3,bWrite3,usbDev)>0)
        {
            iRet = PrintCmd.CheckStatus3(bRead3[0]);
        }

        if(iRet!=0)
            return iRet;

        byte[] bRead4 = new byte[1];
        byte[] bWrite4 = PrintCmd.GetStatus4();
        if(mUsbDriver.read(bRead4,bWrite4,usbDev)>0)
        {
            iRet = PrintCmd.CheckStatus4(bRead4[0]);
        }


        return iRet;
    }

    String receive = "", state = "";
    String normal = "",notConnectedOrNotPopwer = "",notMatch = "",
            printerHeadOpen = "", cutterNotReset = "", printHeadOverheated = "",
            blackMarkError = "",paperExh = "",paperWillExh = "",abnormal = "";

    private void getMsgByLanguage() {

        receive = Constant.Receive_US;
        state = Constant.State_US;
        normal = Constant.Normal_US;
        notConnectedOrNotPopwer = Constant.NoConnectedOrNoOnPower_US;
        notMatch = Constant.PrinterAndLibraryNotMatch_US;
        printerHeadOpen = Constant.PrintHeadOpen_US;
        cutterNotReset = Constant.CutterNotReset_US;
        printHeadOverheated = Constant.PrintHeadOverheated_US;
        blackMarkError = Constant.BlackMarkError_US;
        paperExh = Constant.PaperExhausted_US;
        paperWillExh = Constant.PaperWillExhausted_US;
        abnormal = Constant.Abnormal_US;
    }


    public void printFinalString()
    {
         TITLE_US = "Student Details\n";   //+ "Transact business (1)\n\n";
    /*public static String DETAILS_TXT = " Student name:"+"Mohan"+"\n Admission no:"+"12345"+"\n        Class:"+"8"+
            "\n      Section:"+"A"+"\n          DOA:"+"12/09/2016"+"\n     Email-id:"+"aha@gmail.com"+"\n   Contact no:"+
            "9880765432"+"\n          DOB:"+"03/09/2011\n \n";*/

        DETAILS_TXT = " Student name\t:  "+stud_name+"\n Reg. no\t:  "+stud_regNo+"\n Class       \t:  "+class_name+
                "\n Section     \t:  "+section_name+"\n DOA         \t:  "+stud_admNo+"\n Email-id    \t:  "+stud_mailId+"\n Contact no  \t:  "+
                stud_contactNo+"\n DOB         \t:  "+stud_dob+"\n \n";

        /* DETAILS_TXT = " Student name\t:  "+"Mohan"+"\n Admission no\t:  "+"12345"+"\n Class       \t:  "+"8"+
                "\n Section     \t:  "+"A"+"\n DOA         \t:  "+"12/09/2016"+"\n Email-id    \t:  "+"aha@gmail.com"+"\n Contact no  \t:  "+
                "9880765432"+"\n DOB         \t:  "+"03/09/2011\n \n";*/
    }



    /****************************************************************************************************/
}
