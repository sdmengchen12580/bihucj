package com.aganyun.acode.utils;

/**
 * Created by 孟晨 on 2018/5/15.
 */

public class TypeTransform {

    //string-->int
    public static int stringToInt(String str) {
        return Integer.parseInt(str);
    }

    //int-->string
    public static String intToString(int num) {
        return num + "";
    }

    //string-->double
    public static double strToDouble(String str) {
        return Double.parseDouble(str);
    }

    //string-->long
    public static long strToLong(String str){
        return Long.valueOf(str).longValue();
    }
}
