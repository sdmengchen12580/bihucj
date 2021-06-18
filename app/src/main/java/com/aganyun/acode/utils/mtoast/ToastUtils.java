package com.aganyun.acode.utils.mtoast;

import android.widget.Toast;

import com.aganyun.acode.utils.AppUtils;


public class ToastUtils {

    public static Toast toast;

    public static void show(String text) {
        if (toast == null) {
            toast = Toast.makeText(AppUtils.getAppContext(),
                    text,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
