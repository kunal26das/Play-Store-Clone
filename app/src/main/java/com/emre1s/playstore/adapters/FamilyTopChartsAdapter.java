package com.emre1s.playstore.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.emre1s.playstore.R;
import com.emre1s.playstore.fragments.FamilyTopChartsFragment;
import com.emre1s.playstore.fragments.TopChartsFragment;

public class FamilyTopChartsAdapter extends FragmentPagerAdapter {
    private static int ITEM_NUMBER = 3;
    String category;
    Context mContext;
    int limit;

    @StringRes
    private static final int[] topChartsTabItemNames =new int[] {R.string.topFree,R.string.trending,R.string.topPaid};

    public FamilyTopChartsAdapter(Context context, FragmentManager fragmentManager, int limit){
        super(fragmentManager);
        mContext=context;
        this.limit=limit;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                category="topselling_free";
                return FamilyTopChartsFragment.getInstance(category,limit);
            }
            case 1: {
                category="movers_shakers";
                return FamilyTopChartsFragment.getInstance(category,limit);
            }
            case 2: {
                category="topselling_paid";
                return FamilyTopChartsFragment.getInstance(category,limit);
            }
            default:{
                category="topselling_free";
                return FamilyTopChartsFragment.getInstance(category,limit);
            }
        }
    }

    @Override
    public int getCount() {
        return ITEM_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(topChartsTabItemNames[position]);
    }
}
