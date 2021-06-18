package com.aganyun.acode.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.jaeger.library.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mContext;
    protected long lastClickTime;

    protected abstract int getViewId();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setTransparent(this);
        mContext = this;
        AppUtils.init(getApplicationContext());
        //栈管理
        ViewManager.getInstance().addActivity(this);
        //控件
        setContentView(getViewId());
        initView(savedInstanceState);
    }

    protected boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //显示软键盘
    protected void showKeyboard(EditText et_chat) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_chat, InputMethodManager.SHOW_FORCED);
    }

    //隐藏软键盘
    public void hideKeyboard(EditText et_chat) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_chat.getWindowToken(), 0);
    }
}
