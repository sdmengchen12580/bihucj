package com.aganyun.acode.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.renderscript.BaseObj;
import android.support.v4.app.ActivityCompat;

/**
 * Created by 孟晨 on 2018/5/28.
 */

public class ChangeActUtils {


    public static void changeActivity_2_webAct(Context fromContext, Class<?> act, String keyValue1, String keyValue2) {
        Intent intent = new Intent();
        intent.setClass(fromContext, act);
        intent.putExtra("title", keyValue1);
        intent.putExtra("weburl", keyValue2);
        fromContext.startActivity(intent);
    }

    public static void changeActivity_2_webShareAct(Context fromContext,
                                               Class<?> act,
                                               String keyValue1,
                                               String keyValue2,
                                               String keyValue3) {
        Intent intent = new Intent();
        intent.setClass(fromContext, act);
        intent.putExtra("title", keyValue1);
        intent.putExtra("weburl", keyValue2);
        intent.putExtra("des", keyValue3);
        fromContext.startActivity(intent);
    }

    public static void changeActivity(Context fromContext, Class<?> act) {
        fromContext.startActivity(new Intent(fromContext, act));
    }

    public static void changeToSignInActivity(Context fromContext, Class<?> act, boolean needSignIn){
        Intent intent = new Intent();
        intent.setClass(fromContext, act);
        intent.putExtra("needSignIn", needSignIn);
        fromContext.startActivity(intent);
    }

    public static void changeAct_withId(Context fromContext, Class<?> toClass, int id, String idKey) {
        Intent intent = new Intent();
        intent.setClass(fromContext, toClass);
        intent.putExtra(idKey, id);
        fromContext.startActivity(intent);
    }

    //跳转应用市场列表
    public static void changeToShop(Activity activity){
        Intent intent=new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
