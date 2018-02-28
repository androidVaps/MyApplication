package com.example.san.myapplication.PaymentGateway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san.myapplication.FeesActivityNew1;
import com.example.san.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vaps on 31-Oct-17.
 */
public class PayUwebView extends Activity
{

    private static final String TAG = "PayUwebView";
    WebView webviewPayment;
    WebView mwebview;

    /*
	protected  void writeStatus(String str){
		txtview.setText(str);
	}*/

    private static String bytesToHexString(byte[] bytes)
    {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');

            }
            sb.append(hex);
        }
        return sb.toString();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu);

        webviewPayment = (WebView) findViewById(R.id.webView1);
        webviewPayment.getSettings().setJavaScriptEnabled(true);
        webviewPayment.getSettings().setDomStorageEnabled(true);
        webviewPayment.getSettings().setLoadWithOverviewMode(true);
        webviewPayment.getSettings().setUseWideViewPort(true);
        webviewPayment.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webviewPayment.getSettings().setSupportMultipleWindows(true);
        webviewPayment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webviewPayment.getSettings().setBuiltInZoomControls(true);
        webviewPayment.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");

        StringBuilder url_s = new StringBuilder();

        //    url_s.append("https://test.payu.in/_payment");

        url_s.append("https://secure.payu.in/_payment");

        Log.e(TAG, "call url " + url_s);


        //	webviewPayment.postUrl(url_s.toString(),EncodingUtils.getBytes(getPostString(), "utf-8"));

        webviewPayment.postUrl(url_s.toString(), getPostString().getBytes(Charset.forName("UTF-8")));

        webviewPayment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @SuppressWarnings("unused")
            public void onReceivedSslError(WebView view, SslErrorHandler handler) {
                Log.e("Error", "Exception caught!");
                handler.cancel();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });


    }

    private String getPostString()
    {
        String key = "7nSyBaiD";
        String salt = "Bwxo1cPe";
        String txnid = "Online/067/2017-2018";
        String amount = "2000";
        String firstname = "opsantosh";
        String email = "test@gmail.com";
        String productInfo = "Product1";

        String udf1 = "Rs.";
        String udf2 = "25066";
        String udf3 = "4";
        String udf4 = "0,1";
        String udf5 = "0,71";
        String udf6 = "1";

        StringBuilder post = new StringBuilder();
        post.append("key=");
        post.append(key);
        post.append("&");
        post.append("txnid=");
        post.append(txnid);
        post.append("&");
        post.append("amount=");
        post.append(amount);
        post.append("&");
        post.append("productinfo=");
        post.append(getJson());
        post.append("&");
        post.append("firstname=");
        post.append(firstname);
        post.append("&");
        post.append("email=");
        post.append(email);
        post.append("&");
        post.append("phone=");
        post.append("7259452000");
        post.append("&");
        post.append("surl=");
        //    post.append("https://www.payumoney.com/mobileapp/payumoney/success.php");
        post.append("http://stagingcampusux.azurewebsites.net/api/FeeOnlinePayment/paymentresponse/");
        //https://www.payumoney.com/mobileapp/payumoney/success.php
        //https://www.payumoney.com/mobileapp/payumoney/failure.php
        post.append("&");
        post.append("furl=");
        //    post.append("https://www.payumoney.com/mobileapp/payumoney/failure.php");
        post.append("http://stagingcampusux.azurewebsites.net/api/FeeOnlinePayment/paymentresponse/");
        post.append("&");
        post.append("udf1=");
        post.append("Rs.");
        post.append("&");
        post.append("udf2=");
        post.append("25066");
        post.append("&");
        post.append("udf3=");
        post.append("4");
        post.append("&");
        post.append("udf4=");
        post.append("0,1");
        post.append("&");
        post.append("udf5=");
        post.append("0,71");
        post.append("&");
        post.append("udf6=");
        post.append("1");
        post.append("&");
        StringBuilder checkSumStr = new StringBuilder();
		/* =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt) */
        MessageDigest digest = null;
        String hash;
        try {
            digest = MessageDigest.getInstance("SHA-512");// MessageDigest.getInstance("SHA-256");

            checkSumStr.append(key);
            checkSumStr.append("|");
            checkSumStr.append(txnid);
            checkSumStr.append("|");
            checkSumStr.append(amount);
            checkSumStr.append("|");
            checkSumStr.append(productInfo);
            checkSumStr.append("|");
            checkSumStr.append(firstname);
            checkSumStr.append("|");
            checkSumStr.append(email);
            checkSumStr.append("|");
            checkSumStr.append(udf1);
            checkSumStr.append("|");
            checkSumStr.append(udf2);
            checkSumStr.append("|");
            checkSumStr.append(udf3);
            checkSumStr.append("|");
            checkSumStr.append(udf4);
            checkSumStr.append("|");
            checkSumStr.append(udf5);
            checkSumStr.append("|");
            checkSumStr.append(udf6);
            checkSumStr.append("|||||");
            checkSumStr.append(salt);

            digest.update(checkSumStr.toString().getBytes());

            hash = bytesToHexString(digest.digest());
            post.append("hash=");
            //    post.append(hash);
            post.append("91c9df197906dac93157ce654979f083291e0168ea6935659a88d7c6ea0b26018167b37346a63942a9b1b258c617651a489fd56f14a89ed372559dcba76296ba");
            post.append("&");
            //    Log.i(TAG, "SHA result is " + hash);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        post.append("service_provider=");
        post.append("payu_paisa");
        return post.toString();
    }


    private JSONObject getJson()
    {
        JSONObject object =  new JSONObject();
        try {
            JSONObject innerObject = new JSONObject();
            innerObject.put("name","splitId1");
            innerObject.put("merchantId","5808907");
            innerObject.put("value","2000");
            innerObject.put("commission","0");
            innerObject.put("description","Online Payment");

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(innerObject);
            object.put("paymentParts",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object;
    }

    private final class PayUJavaScriptInterface {
        PayUJavaScriptInterface() {
        }

        @JavascriptInterface
        public void success(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

                    //        Toast.makeText(MainActivity1.this, "Status is txn is success " + " payment id is " + paymentId, 8000).show();
                    Toast.makeText(PayUwebView.this, "Status is txn is success " + " payment id is " + paymentId, Toast.LENGTH_LONG).show();
                    //String str="Status is txn is success "+" payment id is "+paymentId;
                    // new MainActivity().writeStatus(str);

                    TextView txtview;
                    txtview = (TextView) findViewById(R.id.textView1);
                    txtview.setText("Status is txn is success " + " payment id is " + paymentId);

                }
            });
        }

        @JavascriptInterface
        public void failure(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

//                    Toast.makeText(MainActivity1.this, "Status is txn is failed " + " payment id is " + paymentId, 8000).show();
                    Toast.makeText(PayUwebView.this, "Status is txn is failed " + " payment id is " + paymentId, Toast.LENGTH_LONG).show();
                    //String str="Status is txn is failed "+" payment id is "+paymentId;
                    // new MainActivity().writeStatus(str);

                    TextView txtview;
                    txtview = (TextView) findViewById(R.id.textView1);
                    txtview.setText("Status is txn is failed " + " payment id is " + paymentId);

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();
        startActivity(new Intent(PayUwebView.this, FeesActivityNew1.class));

    }

}
