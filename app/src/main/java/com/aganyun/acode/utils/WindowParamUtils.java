package com.aganyun.acode.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 孟晨 on 2018/3/15.
 */

public class WindowParamUtils {


    /**
     * 版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("测试版本号", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 版本名
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("测试版本名称", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    //保存屏幕参数到sp
    public static void saveWindowParams2Sp(Activity activity) {
        //获取状态栏的高度
        SPUtil.put(AppUtils.getAppContext(), "barheight", WindowParamUtils.attainStatusBarHeight(activity) + "");
        //获取屏幕宽度
        SPUtil.put(AppUtils.getAppContext(), "screenwidth", "" + WindowParamUtils.screenWidth(activity));
    }

    //屏幕宽
    public static int screenWidth(Activity ac) {
        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    //屏幕高
    public static int screenHeight(Activity ac) {
        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param context 上下文
     * @param pid     进程的id
     * @return 返回进程的名字
     */
    public static String getAppName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }


    //获取状态栏的高度
    public static int attainStatusBarHeight(Activity activity) {
        View view = activity.getWindow().getDecorView();
        // 获取状态栏高度
        Rect frame = new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        int result = frame.top;
        return result;
    }

    //设置状态栏的颜色
    public static void setBarColor(Activity activity, int color, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }


}
