package com.example.san.myapplication.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.san.myapplication.Fragments.FeesDetailsFragment;
import com.example.san.myapplication.Fragments.FeesPaidHistoryFragment;
import com.example.san.myapplication.Module.Test;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by vaps on 9/20/2017.
 */

public class FeesTabAdapter extends FragmentStatePagerAdapter
{

    private static int TAB_COUNT = 2;

    JSONObject object;
    Context context;

    public FeesTabAdapter(FragmentManager fm, JSONObject jsonObject, Context context)
    {
        super(fm);
        object = jsonObject;
        this.context = context;
     }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
              FeesDetailsFragment feesDetailsFragment = new FeesDetailsFragment();
              feesDetailsFragment.setData(object,context);
              return feesDetailsFragment;

            case 1:

                FeesPaidHistoryFragment historyFragment = new FeesPaidHistoryFragment();
              //  historyFragment.setArguments(bundle);
                historyFragment.setData(object,context);
                return historyFragment;

           //    return FeesPaidHistoryFragment.newInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return FeesDetailsFragment.TITLE;

            case 1:
                return FeesPaidHistoryFragment.TITLE;

        }
        return super.getPageTitle(position);
    }
}
