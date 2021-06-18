package com.aganyun.acode.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.aganyun.acode.utils.CustomDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    private View rootView;
    private CustomDialog customDialog;
    protected long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initViews(savedInstanceState, rootView);
        return rootView;
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState, View view);

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
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_chat, InputMethodManager.SHOW_FORCED);
    }

    //隐藏软键盘
    public void hideKeyboard(EditText et_chat) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_chat.getWindowToken(), 0);
    }

    protected void showLoadingDialog(String hint) {
        if (customDialog == null) {
            customDialog = new CustomDialog(getContext(), hint);
        }
        customDialog.setContent(hint);
        customDialog.show();
    }

    protected void hideLoadingDialog() {
        if (customDialog != null) {
            if (customDialog.isShowing()) {
                customDialog.dismiss();
            }
        }
    }
}
