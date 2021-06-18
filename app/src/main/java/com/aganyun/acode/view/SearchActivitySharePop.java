package com.aganyun.acode.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.WindowParamUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

import rx.Subscription;

/**
 * Created by 孟晨 on 2018/8/2.
 */

public class SearchActivitySharePop implements View.OnClickListener {

    private View popView;
    private PopupWindow popWindow;
    private WeakReference<BaseActivity> weakReference;
    //分享的内容（我的页面是网页-快讯是图文）
    private String shareContent;
    private View framelayout;

    public SearchActivitySharePop(BaseActivity baseActivity) {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
        weakReference = new WeakReference<BaseActivity>(baseActivity);
    }


    //显示弹窗
    public PopupWindow showPop(String des, String time, String title) {
        BaseActivity baseActivity = weakReference.get();
        if (baseActivity != null) {
            popView = LayoutInflater.from(baseActivity)
                    .inflate(R.layout.fragment_suggest_friend, null, false);
            //分享渠道
            LinearLayout ll_weixin = popView.findViewById(R.id.ll_weixin);
            ll_weixin.setOnClickListener(this);
            LinearLayout ll_friend = popView.findViewById(R.id.ll_friend);
            ll_friend.setOnClickListener(this);
            LinearLayout ll_qq = popView.findViewById(R.id.ll_qq);
            ll_qq.setOnClickListener(this);
            LinearLayout ll_qq_zone = popView.findViewById(R.id.ll_qq_zone);
            ll_qq_zone.setOnClickListener(this);
            LinearLayout ll_weibo = popView.findViewById(R.id.ll_weibo);
            ll_weibo.setOnClickListener(this);
            //取消
            TextView tv_cancel = popView.findViewById(R.id.tv_cancel);
            tv_cancel.setOnClickListener(this);

            //显示布局
            framelayout = popView.findViewById(R.id.framelayout_no_zxing);
            ViewUtils.showView(framelayout);
            //时间
            TextView tv_time = popView.findViewById(R.id.tv_time);
            tv_time.setText(time);
            //标题1
            TextView tv_title1 = popView.findViewById(R.id.tv_title1);
//            //标题2
//            TextView tv_title2 = popView.findViewById(R.id.tv_title2);
//            /**标题个数做10的判断*/
//            if (title.length() > 20) {
            tv_title1.setText(title);
//                tv_title2.setText(title.substring(20));
//            } else {
//                tv_title1.setText(title);
//                ViewUtils.GoneView(tv_title2);
//            }
            //内容
            CustomTextView tv_content = popView.findViewById(R.id.tv_content);
            shareContent = des;
            tv_content.setSpacing(7f);
            tv_content.setMYLineSpacing(1.7f);
            tv_content.setText(OtherUtils.ToDBC(des));

            popWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (WindowParamUtils.screenHeight(baseActivity) - DensityUtil.dip2px(20)),
                    true);
            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //点击外部，popupwindow会消失
            popWindow.setFocusable(false);
            popWindow.setOutsideTouchable(false);
//            popWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
            popWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            showPopBlackBg(baseActivity);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*微信*/
            case R.id.ll_weixin:
                shareInfo2P(SHARE_MEDIA.WEIXIN);
                break;
            /*朋友圈*/
            case R.id.ll_friend:
                shareInfo2P(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            /*qq*/
            case R.id.ll_qq:
                shareInfo2P(SHARE_MEDIA.QQ);
                break;
            /*qq空间*/
            case R.id.ll_qq_zone:
                shareInfo2P(SHARE_MEDIA.QZONE);
                break;
            /*微博*/
            case R.id.ll_weibo:
                shareInfo2P(SHARE_MEDIA.SINA);
                break;
            /*取消*/
            case R.id.tv_cancel:
                dismissPop();
                break;
        }
    }

    private void dismissPop() {
        if (popWindow.isShowing()) {
            popWindow.dismiss();
            popWindow = null;
            backNormalPopBg();
        }
    }

    //分享-微信1  朋友圈2  qq3  qq空间4  微博5
    private void shareInfo2P(SHARE_MEDIA type) {
        dismissPop();
        if (clickCallback != null) {
            if (ObjIsNull.isEmpty(shareContent)) {
                ToastUtils.show("分享失败,请重试!");
            } else {
                clickCallback.clickShared(type, shareContent, framelayout);
            }
        }
    }


    //-----------------------------------接口-----------------------------------
    public interface ClickCallback {
        void clickShared(SHARE_MEDIA type, String shareContent, View view);
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}
