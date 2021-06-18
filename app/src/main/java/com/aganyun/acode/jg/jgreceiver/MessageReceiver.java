package com.aganyun.acode.jg.jgreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.jg.nativenotify.NotifyMessage;
import com.aganyun.acode.utils.ObjIsNull;

/**
 * Created by 孟晨 on 2018/7/17.
 */

public class MessageReceiver extends BroadcastReceiver {

    public MessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Custom.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
            if (!ObjIsNull.ObjIsNull(intent)) {
                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                Log.e("推送消息接收: ", "title=" + title + "\n" + "content=" + content);
                new NotifyMessage().startNotify(context, title, content);
            }
        }
    }
}
