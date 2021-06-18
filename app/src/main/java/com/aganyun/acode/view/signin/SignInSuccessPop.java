package com.aganyun.acode.view.signin;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.WindowParamUtils;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

/**
 * Created by 孟晨 on 2018/8/22.
 */

public class SignInSuccessPop {

    private View popView;
    private PopupWindow popWindow;
    //糖果数量
    private String sugerNum;
    private WeakReference<BaseActivity> weakReference;
    private Handler handler;

    public SignInSuccessPop(BaseActivity baseActivity) {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
        weakReference = new WeakReference<BaseActivity>(baseActivity);
    }

    //显示弹窗
    public PopupWindow showPop(String sugerNum, boolean isSignIn) {
        BaseActivity activity = weakReference.get();
        if (activity != null) {
            popView = LayoutInflater.from(activity)
                    .inflate(R.layout.pop_signin_success_layout, null, false);

            //糖果数量
            TextView tv_suger_num = popView.findViewById(R.id.tv_suger_num);
            tv_suger_num.setText(sugerNum);

            TextView tv_content = popView.findViewById(R.id.tv_content);
            if (isSignIn) {
                tv_content.setText("恭喜你，今日签到成功!");
            } else {
                tv_content.setText("恭喜你，分享成功!");
            }

            popWindow = new PopupWindow(popView,
                    DensityUtil.dip2px(activity, 250),
                    DensityUtil.dip2px(activity, 264),
                    true);
            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //点击外部，popupwindow会消失
            popWindow.setFocusable(false);
            popWindow.setOutsideTouchable(false);
//            popWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
            popWindow.showAtLocation(popView, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            showPopBlackBg(activity);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (handler != null) {
                        handler = null;
                    }
                    if (autoHidePopCallback != null) {
                        autoHidePopCallback.autoHidePop();
                    }
                }
            }, 1500);
        }
        return popWindow;
    }


    //----------------------------------------------背景颜色的改变----------------------------------
    //初始化弹窗的背景色

    public void showPopBlackBg(BaseActivity baseActivity) {
        if (baseActivity != null) {
            changePopBlackBg(0.2F);
        }
    }

    //恢复弹窗的背景色
    public void backNormalPopBg() {
        BaseActivity baseActivity = weakReference.get();
        if (baseActivity != null) {
            WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
            lp.alpha = 1f;
            baseActivity.getWindow().setAttributes(lp);
        }
    }

    //弹窗背景颜色
    public void changePopBlackBg(float blackBg) {
        BaseActivity baseActivity = weakReference.get();
        if (baseActivity != null) {
            WindowManager.LayoutParams lp = baseActivity.getWindow().getAttributes();
            lp.alpha = blackBg;
            baseActivity.getWindow().setAttributes(lp);
        }
    }

    public void dismissPop() {
        if (popWindow.isShowing()) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            popWindow.dismiss();
            popWindow = null;
            backNormalPopBg();
        }
    }


    //------------------------------------接口----------------------------------------
    public interface AutoHidePopCallback {
        void autoHidePop();
    }

    private AutoHidePopCallback autoHidePopCallback;

    public void setAutoHidePopCallback(AutoHidePopCallback autoHidePopCallback) {
        this.autoHidePopCallback = autoHidePopCallback;
    }

}
