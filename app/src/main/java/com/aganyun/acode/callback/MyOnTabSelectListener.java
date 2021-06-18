package com.aganyun.acode.callback;

import com.flyco.tablayout.listener.OnTabSelectListener;

/**
 * Created by 孟晨 on 2018/5/11.
 */

public abstract class MyOnTabSelectListener implements OnTabSelectListener {

    public abstract void whichSelected(int position);

    @Override
    public void onTabSelect(int position) {
        whichSelected(position);
    }

    @Override
    public void onTabReselect(int position) {
    }

}
