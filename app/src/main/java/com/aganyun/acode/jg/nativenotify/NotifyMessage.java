package com.aganyun.acode.jg.nativenotify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.aganyun.acode.R;
import com.aganyun.acode.ui.act.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 孟晨 on 2018/7/17.
 */

public class NotifyMessage {

    public void startNotify(Context context, String title, String content) {
        Log.e("startNotify发送到通知栏: ", "title=" + title + "\n" + "content=" + content);
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //FIXME 8.0适配
        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_1";
            String description = "JG";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, description, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            manager.createNotificationChannel(mChannel);
            builder = new Notification.Builder(context, id);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setTicker("你有一条新的通知");
        builder.setSmallIcon(R.mipmap.ic_launcher);

//        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//        builder.setContentTitle(title);
//        builder.setContentText(content);
        /*自定义*/
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.jg_content_notify_layout);
        //头像
        view.setImageViewResource(R.id.img, R.mipmap.ic_launcher);
        //标题
        view.setTextViewText(R.id.tv_title, title);
        //内容
        view.setTextViewText(R.id.tv_content, content);
        //时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        view.setTextViewText(R.id.tv_time, simpleDateFormat.format(date) + "");

        //给builder对象添加布局
        builder.setContent(view);
        builder.setAutoCancel(true);
        builder.setWhen(SystemClock.currentThreadTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        //设置点击通知后执行的动作
        Intent intent = new Intent(context, HomeActivity.class);
        int requestId = (int) new Date().getTime();
        // 第四个参数有4种状态
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        manager.notify(requestId, builder.build());
    }
}
