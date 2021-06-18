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
public abstract class BaseLazyLoadFragment extends Fragment {

    private View rootView;
    //FIXME 控件是否初始化完成
    private boolean isViewCreated;
    //FIXME 数据是否已加载完毕
    private boolean isLoadDataCompleted;
    protected long lastClickTime;
    private CustomDialog customDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initViews(savedInstanceState, rootView);
        //FIXME 控件初始化完成
        isViewCreated = true;
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    /*loadData()会在两个地方执行？
    因为，ViewPager 默认显示第一页，第一页肯定要先加载数据，
    而且 setUserVisibleHint 的执行顺序又是在 onCreatView 之前，
    同时 onCreatView 需要初始化界面和修改 isViewCreated 的值。
    所以就需要在 onActivityCreated 里执行一次。*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            loadData();
        }
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

    //子类实现加载数据的方法
    public abstract void loadData();

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState, View view);
}
