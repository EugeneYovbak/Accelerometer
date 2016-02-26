package com.perspikyliator.user.accelerometer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.perspikyliator.user.accelerometer.fragment.MyGraphFragment;
import com.perspikyliator.user.accelerometer.fragment.MyListFragment;

/**
 * Adapter sets two fragments into ViewPager
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public CustomPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyListFragment();
            case 1:
                return new MyGraphFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
