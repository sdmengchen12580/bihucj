package com.aganyun.acode.callback;

import com.androidkun.xtablayout.XTabLayout;

/**
 * Created by 孟晨 on 2018/8/17.
 */

public abstract class HomeInnerTabListener implements XTabLayout.OnTabSelectedListener {


    public abstract void mOnTabSelected(int position);

    @Override
    public void onTabSelected(XTabLayout.Tab tab) {
        mOnTabSelected(tab.getPosition());
    }

    @Override
    public void onTabUnselected(XTabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {

    }
}
