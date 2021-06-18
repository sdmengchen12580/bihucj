package com.aganyun.acode.ui.base;

import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.aganyun.acode.jg.jgreceiver.RegisterJGMessageReceiver;
import com.aganyun.acode.utils.stack.ViewManager;

public abstract class BaseBroadCastActivity extends AppCompatActivity {

    public static boolean isForeground = false;
    protected Activity mContext;

    protected abstract int getViewId();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //栈管理
        ViewManager.getInstance().addActivity(this);
        //控件
        setContentView(getViewId());
        initView(savedInstanceState);
        //注册广播接收
        RegisterJGMessageReceiver.newInstance().registerMessageReceiver();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    protected void onDestroy() {
        RegisterJGMessageReceiver.newInstance().unregisterMessageReceiver();
        super.onDestroy();
    }
    protected long lastClickTime;
    protected boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}