package com.aganyun.acode.jg.jgreceiver;


import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.aganyun.acode.config.Custom;
import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.WindowParamUtils;

/**
 * Created by 孟晨 on 2018/7/17.
 */

public class RegisterJGMessageReceiver {

    private RegisterJGMessageReceiver() {
    }

    public static RegisterJGMessageReceiver newInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        public static RegisterJGMessageReceiver instance = new RegisterJGMessageReceiver();
    }

    private MessageReceiver mMessageReceiver;

    //注册广播接收
    public void registerMessageReceiver() {
        if (mMessageReceiver == null) {
            mMessageReceiver = new MessageReceiver();
            IntentFilter filter = new IntentFilter();
            filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
            filter.addAction(Custom.MESSAGE_RECEIVED_ACTION);
            LocalBroadcastManager.getInstance(AppUtils.getAppContext()).registerReceiver(mMessageReceiver, filter);
        }
    }

    //注销广播接收器
    public void unregisterMessageReceiver() {
        if (mMessageReceiver == null) {
            AppUtils.getAppContext().unregisterReceiver(mMessageReceiver);
            mMessageReceiver = null;
        }
    }
}
