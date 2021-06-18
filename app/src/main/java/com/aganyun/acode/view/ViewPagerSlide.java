package com.aganyun.acode.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ViewPagerSlide extends ViewPager {

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    //第二个参数就是是否需要动画，
    //调用的都是一个参数的setCurrentItem，所以直接让它调用无动画的切换方法即可
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
