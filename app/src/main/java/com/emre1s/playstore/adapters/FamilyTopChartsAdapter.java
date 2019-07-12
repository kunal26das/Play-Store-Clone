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

    @StringRes
    private static final int[] topChartsTabItemNames =new int[] {R.string.topFree,R.string.trending,R.string.topPaid};

    public FamilyTopChartsAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        mContext=context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                category = "topselling_free";
                return new FamilyTopChartsFragment(category);
            }
            case 1: {
                category = "movers_shakers";
                return new FamilyTopChartsFragment(category);
            }
            case 2: {
                category="topselling_paid";
                return new FamilyTopChartsFragment(category);
            }
            default:{
                category="topselling_free";
                return new FamilyTopChartsFragment(category);
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