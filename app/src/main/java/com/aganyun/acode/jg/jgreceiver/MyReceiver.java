package com.aganyun.acode.jg.jgreceiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.aganyun.acode.ui.base.BaseBroadCastActivity;

import cn.jpush.android.api.JPushInterface;


public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private String title;
    private String content;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //SDK 向 JPush Server 注册所得到的注册 ID
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        }
        //自定义消息 Push
        else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "测试MyReceiver接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //EXTRA_TITLE    标题
            //EXTRA_MESSAGE  消息内容
            //EXTRA_EXTRA   附加JSON字符串
            //EXTRA_MSG_ID  唯一标识消息的 ID, 可用于上报统计
        }
        //收到了通知 Push。如果通知的内容为空，则在通知栏上不会展示通知
        //取到通知内容外的其他信息-标题-通知内容- JSON字符串等
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //FIXME 接收到通知
            processNotifyMessage(context, bundle);
        }
        //用户点击了通知。一般情况下,用户不需要配置此 receiver action。
        //如果开发者在 AndroidManifest.xml 里未配置此 receiver action，那么，
        //SDK 会默认打开应用程序的主 Activity，相当于用户点击桌面图标的效果
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知内容" + bundle.getString(JPushInterface.EXTRA_ALERT));
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知json" + bundle.getString(JPushInterface.EXTRA_EXTRA));
        }
        //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        }
        //JPush 服务的连接状态发生变化
        else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private void processNotifyMessage(Context context, Bundle bundle) {
        //EXTRA_NOTIFICATION_TITLE   通知的标题
        //EXTRA_ALERT   通知内容
        //EXTRA_EXTRA   附加JSON字符串
        //如果服务端内容（alert）字段为空，则notification id 为0
        //EXTRA_RICHPUSH_HTML_RES  富媒体通知推送下载的图片资源的文件名,多个文件名用 “，” 分开
        //与 “JPushInterface.EXTRA_RICHPUSH_HTML_PATH” 位于同一个路径
        //EXTRA_MSG_ID  唯一标识通知消息的 ID, 可用于上报统计
        //EXTRA_BIG_TEXT  大文本通知样式中大文本的内容
        //EXTRA_BIG_PIC_PATH  大图片通知样式中大图片的路径/地址
        //EXTRA_INBOX  收件箱通知样式中收件箱的内容
        //EXTRA_NOTI_PRIORITY  通知的优先级
        //EXTRA_NOTI_CATEGORY 如通知栏的排序   通知分类
        //EXTRA_RICHPUSH_HTML_PATH  富媒体通知推送下载的HTML的文件路径,用于展现WebView
        //EXTRA_NOTIFICATION_ID  通知栏的Notification ID，可以用于清除Notification


        //EXTRA_NOTIFICATION_TITLE   通知的标题
        title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, "测试MyReceiver接收到推送下来的通知,标题: " + title);

        //EXTRA_ALERT   通知内容
        content = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "测试MyReceiver接收到推送下来的通知,通知内容: " + content);

        //前台接受推送
        if (BaseBroadCastActivity.isForeground) {
            Intent intent = new Intent("com.aganyun.acode.MESSAGE_RECEIVED_ACTION");
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            //包名+广播的路径
            intent.setComponent(new ComponentName("com.aganyun.acode",
                    "com.aganyun.acode.jg.mbroadcast.MessageReceiver"));
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}