package com.aganyun.acode.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by 孟晨 on 2018/8/17.
 */

public class AllUtils {

    //全屏
    public static void fullScreen(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
