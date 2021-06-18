package com.aganyun.acode.callback;

import android.support.v4.view.ViewPager;


/**
 * Created by 孟晨 on 2018/8/1.
 */

public abstract class MyPagerSelectAdapter implements ViewPager.OnPageChangeListener {


    public abstract void whichSelected(int position);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        whichSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
