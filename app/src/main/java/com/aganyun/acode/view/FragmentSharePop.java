package com.aganyun.acode.view;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.other.OtherLoader;
import com.aganyun.acode.http.other.bean.GetShareUrlBean;
import com.aganyun.acode.ui.base.BaseFragment;
import com.aganyun.acode.ui.fra.myself.MySelfFragment;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.WindowParamUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.lang.ref.WeakReference;

import rx.Subscription;

/**
 * Created by 孟晨 on 2018/7/20.
 */

public class FragmentSharePop implements View.OnClickListener {

    private View popView, shareView;
    private View view;

    private PopupWindow popWindow;
    private WeakReference<BaseFragment> weakReference;
    public Subscription getZxing = null;
    //分享的内容（我的页面是网页-快讯是图文）
    private String shareContent;

    public FragmentSharePop(BaseFragment mySelfFragment) {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
        weakReference = new WeakReference<BaseFragment>(mySelfFragment);
    }

    public void getZxing(final Context context, final TextView tv_code) {
        String key = (String) SPUtil.get(context, "key", "");
        HttpListener<GetShareUrlBean> httpListener = new HttpListener<GetShareUrlBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试获取二维码失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetShareUrlBean getShareUrlBean) {
                ViseLog.e("测试获取二维码成功:" + getShareUrlBean);
                if (getShareUrlBean.getCode() == 500) {
                    ToastUtils.show(getShareUrlBean.getMessages().get(0).getMessage());
                    return;
                }
                tv_code.setText(getShareUrlBean.getData().getResponse().getInvateCode());
                shareContent = getShareUrlBean.getData().getResponse().getShareUrl();
            }
        };
        OtherLoader otherLoader = new OtherLoader(OtherLoader.BASEURL);
        getZxing = otherLoader.getShareUrl(Custom.APPID, key, httpListener);
    }

    private int which;

    //显示弹窗
    public PopupWindow showPop(int which, String des, String time, String title) {
        this.which = which;

        BaseFragment fragment = weakReference.get();
        if (fragment != null) {
            popView = LayoutInflater.from(fragment.getContext())
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


            switch (which) {
                /*首页*/
                case 0:
                    shareContent = des;
                    break;
                /*快讯*/
                case 1:
                    //显示布局
                    shareView = (ScrollView) popView.findViewById(R.id.framelayout_no_zxing);
                    ViewUtils.showView(shareView);
                    //时间
                    TextView tv_time = popView.findViewById(R.id.tv_time);
                    tv_time.setText(time);
                    //标题1
                    TextView tv_title1 = popView.findViewById(R.id.tv_title1);
//                    //标题2
//                    TextView tv_title2 = popView.findViewById(R.id.tv_title2);
//                    /**标题个数做10的判断*/
//                    if (title.length() > 20) {
//                        tv_title1.setText(title.substring(0, 20));
                    tv_title1.setText(title);
//                        tv_title2.setText(title.substring(20));
//                    } else {
//                        tv_title1.setText(title);
//                        ViewUtils.GoneView(tv_title2);
//                    }
                    //内容
                    CustomTextView tv_content = popView.findViewById(R.id.tv_content);
                    tv_content.setSpacing(7f);
                    tv_content.setMYLineSpacing(1.7f);
                    shareContent = des;
                    tv_content.setText(OtherUtils.ToDBC(des));
                    break;
                /*我的*/
                case 2:
                    //显示布局
                    LinearLayout linearLayout = popView.findViewById(R.id.framelayout_zxing);
                    ViewUtils.showView(linearLayout);
                    //分享的view
                    view = popView.findViewById(R.id.view);

//                    //二维码
//                    ImageView img_zxing = popView.findViewById(R.id.img_zxing);
//                    ViewUtils.showView(img_zxing);
                    TextView tv_code = popView.findViewById(R.id.tv_code);
                    getZxing(fragment.getContext(), tv_code);
                    break;
            }

            switch (which) {
                case 1:
                    popWindow = new PopupWindow(popView,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,// (int) (WindowParamUtils.screenHeight(fragment.getActivity()))- DensityUtil.dip2px(20)
                            true);
                    break;
                case 0:
                case 2:
                    popWindow = new PopupWindow(popView,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) (WindowParamUtils.screenHeight(fragment.getActivity()) * 0.9),
                            true);
                    break;
            }

            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //点击外部，popupwindow会消失
            popWindow.setFocusable(false);
            popWindow.setOutsideTouchable(false);
//            popWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
            popWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            showPopBlackBg(fragment);
        }
        return popWindow;
    }

    //----------------------------------------------背景颜色的改变----------------------------------
    //初始化弹窗的背景色
    public void showPopBlackBg(BaseFragment fragment) {
        if (fragment != null) {
            changePopBlackBg(0.2F);
        }
    }

    //恢复弹窗的背景色
    public void backNormalPopBg() {
        BaseFragment fragment = weakReference.get();
        if (fragment != null) {
            WindowManager.LayoutParams lp = fragment.getActivity().getWindow().getAttributes();
            lp.alpha = 1f;
            fragment.getActivity().getWindow().setAttributes(lp);
        }
    }

    //弹窗背景颜色
    public void changePopBlackBg(float blackBg) {
        BaseFragment fragment = weakReference.get();
        if (fragment != null) {
            WindowManager.LayoutParams lp = fragment.getActivity().getWindow().getAttributes();
            lp.alpha = blackBg;
            fragment.getActivity().getWindow().setAttributes(lp);
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
        //取消获取二维码订阅
        OtherUtils.cancelSub(getZxing);
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
                switch (which) {
                    case 0:
                        clickCallback.clickShared(type, shareContent, null);
                        break;
                    case 1:
                        clickCallback.clickShared(type, shareContent, shareView);
                        break;
                    case 2:
                        clickCallback.clickShared(type, shareContent, view);
                        break;
                }
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
