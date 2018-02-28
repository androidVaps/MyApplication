package com.example.san.myapplication;

/**
 * Created by vaps on 8/5/2017.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.san.myapplication.Module.FeesDetailsPay;
import com.example.san.myapplication.Module.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AppSingleton
{
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;
    private Session session;
    private FeesDetailsPay feesDetailsPay;
    private ArrayList<FeesDetailsPay> detailsPaysList;

    private HashMap<Integer,ArrayList<Boolean>> feesCheckboxHandler;

    private AppSingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

        /*// creating Object
        session = getInstance();*/

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Session getInstance()
    {
        if(session == null)
        {
            session = new Session();
        }
        return session;
    }

    public ArrayList<FeesDetailsPay> getFeesDetails()
    {
        if(detailsPaysList == null)
        {
            detailsPaysList = new ArrayList<FeesDetailsPay>();
        }
        return detailsPaysList;
    }

   public HashMap<Integer,ArrayList<Boolean>> getFeesChecks()
   {
       if(feesCheckboxHandler == null)
       {
           feesCheckboxHandler = new HashMap<Integer,ArrayList<Boolean>>();
       }
       return feesCheckboxHandler;
   }
}