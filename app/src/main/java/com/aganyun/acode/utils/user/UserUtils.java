package com.aganyun.acode.utils.user;

import android.util.Log;

import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孟晨 on 2018/5/18.
 */

public class UserUtils {

    /**
     * 是否登录
     */
    public static boolean hasNotLoginIn() {
        return ObjIsNull.isEmpty((String) SPUtil.get(AppUtils.getAppContext(), "key", ""));
    }


    // FIXME 用户退出登录的清空
    public static void userSpClear() {
        /*登录状态的key*/
        SPUtil.remove(AppUtils.getAppContext(), "key");
        //name
        SPUtil.remove(AppUtils.getAppContext(), "name");
        //头像
        SPUtil.remove(AppUtils.getAppContext(), "avatar");
        //mobile
        SPUtil.remove(AppUtils.getAppContext(), "mobile");
        //password
        SPUtil.remove(AppUtils.getAppContext(), "password");


        //-------------------------供应商5搜索记录------------------------
        try {
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder1");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder2");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder3");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder4");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder5");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder6");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder7");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder8");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder9");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder10");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearHis() {
        try {
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder1");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder2");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder3");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder4");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder5");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder6");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder7");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder8");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder9");
            SPUtil.remove(AppUtils.getAppContext(), "shoprecoder10");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] shops = {"shoprecoder1", "shoprecoder2", "shoprecoder3", "shoprecoder4", "shoprecoder5",
            "shoprecoder6", "shoprecoder7", "shoprecoder8", "shoprecoder9", "shoprecoder10"};


    //存入供应商的记录
    public static void putShopRecoder(String str) {
        int num = shopRecoderSize();
        if (num == 0) {
            SPUtil.put(AppUtils.getAppContext(), shops[0], str);
            return;
        }
        if (num < 10) {
            SPUtil.put(AppUtils.getAppContext(), shops[num], str);
        } else {
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder10", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder9", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder9", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder8", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder8", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder7", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder7", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder6", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder6", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder5", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder5", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder4", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder4", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder3", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder3", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder2", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder2", (String) SPUtil.get(AppUtils.getAppContext(), "shoprecoder1", ""));
            SPUtil.put(AppUtils.getAppContext(), "shoprecoder1", str);
        }
    }


    //获取供应商记录
    public static List<String> getShopRecoder() {
        List<String> list = new ArrayList<>();
        if (shopRecoderSize() == 0) {
            return null;
        }
        for (int i = 0; i < shopRecoderSize(); i++) {
            list.add((String) SPUtil.get(AppUtils.getAppContext(), shops[i], ""));
        }
        Log.e("测试记录: ", "供应商个数:" + list.size());
        return list;
    }


    //判断供应商搜索记录的个数
    public static int shopRecoderSize() {
        int num = 0;
        for (int i = 0; i < 10; i++) {
            if (!ObjIsNull.isEmpty((String) SPUtil.get(AppUtils.getAppContext(), shops[i], ""))) {
                num++;
            } else {
                break;
            }
        }
        return num;
    }
}
