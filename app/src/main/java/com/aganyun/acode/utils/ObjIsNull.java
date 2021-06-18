package com.aganyun.acode.utils;

/**
 * Created by 孟晨 on 2018/5/12.
 */

public class ObjIsNull {

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean AllIsEmpty(String... args) {
        for (String string : args) {
            if (isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ObjIsNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }
}
