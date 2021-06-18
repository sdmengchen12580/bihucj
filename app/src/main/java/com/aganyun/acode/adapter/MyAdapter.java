package com.aganyun.acode.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 孟晨 on 2018/6/12.
 */

public class MyAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;
    public MyAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        int ret = 0;
        if (fragments != null && fragments.size() != 0) {
            ret = fragments.size();
        }
        return ret;
    }
}
