package com.aganyun.acode.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 孟晨 on 2018/8/6.
 */

public class UpDateUtils {

    public static void upDateFromShop(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + "com.bihucj.mcandroid");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
