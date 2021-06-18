package com.aganyun.acode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.other.OtherLoader;
import com.aganyun.acode.http.other.bean.InvateUserBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.WindowParamUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.lang.ref.WeakReference;
import java.util.Hashtable;

/**
 * Created by 孟晨 on 2018/8/22.
 */

public class WebShareActivityPop implements View.OnClickListener {

    private View popView;
    private View shareView = null;
    private PopupWindow popWindow;
    private WeakReference<BaseActivity> weakReference;
    //分享的内容（我的页面是网页-快讯是图文）
    private String shareContent;

    public WebShareActivityPop(BaseActivity baseActivity) {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
        weakReference = new WeakReference<BaseActivity>(baseActivity);
    }


    //显示弹窗
    public PopupWindow showPop(String des, boolean needImg) {
        BaseActivity baseActivity = weakReference.get();
        if (baseActivity != null) {
            popView = LayoutInflater.from(baseActivity)
                    .inflate(R.layout.webactivityshare_pop_layout, null, false);

            shareContent = des;
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

            if (needImg) {
                shareView = popView.findViewById(R.id.framelayout_no_zxing);
                ViewUtils.showView(popView.findViewById(R.id.img_shareview));
                //zxing
                ImageView zxingImg = popView.findViewById(R.id.imgzxing);
                //获取二维码
                invateUser(baseActivity,zxingImg,needImg);
            }

            popWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (WindowParamUtils.screenHeight(baseActivity) * 0.9),
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

    //-----------------------------------接口-----------------------------------
    //邀请好友的连接地址
    private void invateUser(final BaseActivity baseActivity, final ImageView imageView, final boolean needImg) {
        String key = (String) SPUtil.get(baseActivity, "key", "");
        HttpListener<InvateUserBean> httpListener = new HttpListener<InvateUserBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试邀请好友的连接地址失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(InvateUserBean invateUserBean) {
                ViseLog.e("测试邀请好友的连接地址成功:" + invateUserBean);
                if (invateUserBean.getCode() == 500) {
                    ToastUtils.show(invateUserBean.getMessages().get(0).getMessage());
                    return;
                }
                String webUrl = invateUserBean.getData().getResponse().getInvateUrl();
                if (!ObjIsNull.isEmpty(webUrl)) {
                    if (needImg) {
                        String trueUrl=webUrl.replace("/u003d","=");
                        shareView = popView.findViewById(R.id.framelayout_no_zxing);
                        ViewUtils.showView(imageView);
                        imageView.setImageBitmap(createQRcodeImage(baseActivity,trueUrl));
                    }
                }
            }
        };
        OtherLoader otherLoader = new OtherLoader(OtherLoader.BASEURL);
        otherLoader.invateUser(Custom.APPID, key, httpListener);
    }

    //生成二维码x
    public Bitmap createQRcodeImage(Context context, String url) {
        int w = DensityUtil.dip2px(context, 150);
        int h = DensityUtil.dip2px(context, 150);
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    }
                    else {
                        if(x>25&&x<(w-25)&&y>25&&y<(h-25)){
                            pixels[y * w + x] = 0xffffffff;
                        }
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
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
                clickCallback.clickShared(type, shareContent,shareView);
            }
        }
    }

    //-----------------------------------接口-----------------------------------
    public interface ClickCallback {
        void clickShared(SHARE_MEDIA type, String shareContent,View view);
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}

