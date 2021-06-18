package com.aganyun.acode.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 孟晨 on 2018/7/17.
 */

public class BroadCastUtils {

    private static class SingletonHolder {
        public static BroadCastUtils instance = new BroadCastUtils();
    }

    private BroadCastUtils() {
    }

    public static BroadCastUtils newInstance() {
        return SingletonHolder.instance;
    }


    public void sendBroadCast(Context context, Intent intent) {
        /*自定义广播的包名和自定义广播的路径*/
        intent.setComponent(new ComponentName("com.aganyun.acode",
                "com.aganyun.acode.jg.mbroadcast.MessageReceiver"));
        context.sendBroadcast(intent);
    }

}
