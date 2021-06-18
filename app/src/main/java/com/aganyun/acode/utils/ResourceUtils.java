package com.aganyun.acode.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * Created by 孟晨 on 2018/5/16.
 */

public class ResourceUtils {
    public static int getResourceColor(Activity activity, int id) {
        return activity.getResources().getColor(id);
    }

    public static Drawable getResourceDrawable(Activity activity, int id) {
        return activity.getResources().getDrawable(id);
    }
}
