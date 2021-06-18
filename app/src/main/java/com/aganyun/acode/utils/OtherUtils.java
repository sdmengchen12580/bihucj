package com.aganyun.acode.utils;

import android.graphics.Paint;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;

/**
 * Created by 孟晨 on 2018/8/1.
 */

public class OtherUtils {

    public static void cancelSub(Subscription subscription) {
        if (!ObjIsNull.ObjIsNull(subscription)) {
            if (subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
