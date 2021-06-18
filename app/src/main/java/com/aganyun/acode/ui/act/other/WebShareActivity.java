package com.aganyun.acode.ui.act.other;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetPearlByReadArticleBean;
import com.aganyun.acode.http.suger.bean.GetPearlBySharegetPearlByShareArticleBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.view.WebShareActivityPop;
import com.aganyun.acode.view.signin.SignInSuccessPop;
import com.aganyun.acode.ymshare.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;

public class WebShareActivity extends BaseActivity {

    private TextView tvTitleContent;
    private WebView webview;
    //计时的布局
    private FrameLayout framelayoutTimer;
    //计时的时间
    private TextView tvTimer;
    //计时成功的tv
    private TextView tvPlusSuger;
    //分享
    private WebShareActivityPop webShareActivityPop;
    public PopupWindow popupWindow;
    private String webUrl;
    private String des;
    //计时
    private Timer timer = null;
    private int currentTime = 10;
    //分享文章获得的糖果
    private String suger = "0";
    //文章是否有分享
    public boolean newsHasShared = false;
    //分享成功弹窗
    public PopupWindow popSignSuccess;
    public SignInSuccessPop signInSuccessPop;
    //handler
    private Handler handler = new Handler();
    private Subscription getPearlByReadArticle;
    private Subscription getPearlByShareArticle;

    @Override
    protected int getViewId() {
        return R.layout.activity_web_share;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("weburl");
        String titleContent = intent.getStringExtra("title");
        des = intent.getStringExtra("des");
        tvTitleContent.setText(titleContent);
        //加载网页
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //FIXME  查看详情加上：?hideDown=1
        webview.loadUrl(webUrl + "?hideDown=1");
        //开始计时
        startTimer();
    }

    public void onResume() {
        super.onResume();
        if (newsHasShared) {
            newsHasShared = false;
            if (!suger.trim().toString().equals("0")) {
                showSignSuccessPop("+" + suger);
            }
        }
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtils.cancelSub(getPearlByReadArticle);
        OtherUtils.cancelSub(getPearlByShareArticle);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (webview != null) {
            webview.stopLoading();
            webview.setWebViewClient(null);
            webview.clearHistory();
            webview.clearCache(true);
            webview.loadUrl("about:blank");
            webview.pauseTimers();
            webview = null;
        }
    }

    //--------------------------------------------工具类-----------------------------------------
    public void onViewClicked() {
        tvTitleContent = findViewById(R.id.tv_title_content);
        webview = findViewById(R.id.webview);
        framelayoutTimer = findViewById(R.id.framelayout_timer);
        tvTimer = findViewById(R.id.tv_timer);
        tvPlusSuger = findViewById(R.id.tv_plus_suger);
        /*返回*/
        findViewById(R.id.ll_web_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        });
        /*分享*/
        findViewById(R.id.bt_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                shareUrlToPlat(webUrl);
            }
        });
    }

    public void showSignSuccessPop(String sugerNum) {
        signInSuccessPop = new SignInSuccessPop((BaseActivity) WebShareActivity.this);
        signInSuccessPop.setAutoHidePopCallback(new SignInSuccessPop.AutoHidePopCallback() {
            @Override
            public void autoHidePop() {
                signInSuccessPop.dismissPop();
                signInSuccessPop = null;
                popSignSuccess = null;
            }
        });
        popSignSuccess = signInSuccessPop.showPop(sugerNum, false);
    }

    private void startTimer() {
        //显示计时布局
        ViewUtils.showView(framelayoutTimer);
        //开始倒计时
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime--;
                //计时结束
                if (currentTime == -1) {
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ViewUtils.GoneView(framelayoutTimer);
                            }
                        });
                        //分享
                        getPearlByReadArticle();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTimer.setText(currentTime + "");
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    //--------------------------------------------接口-----------------------------------------
    private void getPearlByReadArticle()//倒计时加糖果
    {
        String key = (String) SPUtil.get(WebShareActivity.this, "key", "");
        HttpListener<GetPearlByReadArticleBean> httpListener = new HttpListener<GetPearlByReadArticleBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试阅读文章获取糖果失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetPearlByReadArticleBean getPearlByReadArticleBean) {
                ViseLog.e("测试阅读文章获取糖果成功：" + getPearlByReadArticleBean);
                if (getPearlByReadArticleBean.getCode() == 500) {
                    ToastUtils.show(getPearlByReadArticleBean.getMessages().get(0).getMessage());
                    return;
                }
                if (!getPearlByReadArticleBean.getData().getResponse().getPearl().toString().equals("0")) {
                    tvPlusSuger.setText("恭喜您，+" + getPearlByReadArticleBean.getData().getResponse().getPearl() + "糖果");
                    ViewUtils.showView(tvPlusSuger);
                    //半秒后隐藏
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ViewUtils.GoneView(tvPlusSuger);
                        }
                    }, 1500);
                }
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        getPearlByReadArticle = sugerLoader.getPearlByReadArticle(Custom.APPID, key, httpListener);
    }

    private void getPearlByShareArticle()//分享加糖果
    {
        String key = (String) SPUtil.get(WebShareActivity.this, "key", "");
        HttpListener<GetPearlBySharegetPearlByShareArticleBean> httpListener = new HttpListener<GetPearlBySharegetPearlByShareArticleBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试分享文章加糖果失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetPearlBySharegetPearlByShareArticleBean getPearlBySharegetPearlByShareArticleBean) {
                ViseLog.e("测试分享文章加糖果成功:" + getPearlBySharegetPearlByShareArticleBean);
                if (getPearlBySharegetPearlByShareArticleBean.getCode() == 500) {
                    ToastUtils.show(getPearlBySharegetPearlByShareArticleBean.getMessages().get(0).getMessage());
                    return;
                }
                newsHasShared = true;
                suger = getPearlBySharegetPearlByShareArticleBean.getData().getResponse().getPearl();
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        getPearlByShareArticle = sugerLoader.getPearlByShareArticle(Custom.APPID, key, httpListener);
    }

    //--------------------------------------------分享-----------------------------------------------
    public void shareUrlToPlat(String webUrl) //新闻政策等网页的分享
    {
        Log.e("测试分享: ", "分享内容:" + webUrl);
        webShareActivityPop = new WebShareActivityPop(WebShareActivity.this);
        webShareActivityPop.setClickCallback(new WebShareActivityPop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String shareContent, View view) {
                //FIXME 判断微信朋友圈，必须要title
                if (type == SHARE_MEDIA.WEIXIN_CIRCLE) {
                    shareToWhichPlat(type, shareContent, true);
                    return;
                }
                shareToWhichPlat(type, shareContent, false);
            }
        });
        popupWindow = webShareActivityPop.showPop(webUrl, false);
    }

    public void shareToWhichPlat(SHARE_MEDIA type, String shareContent, boolean isWX_MustTitle)//分享到各个平台
    {
        //FIXME  查看详情加上：?hideDown=0
        ShareUtils.newInstance().share2platform_web(WebShareActivity.this,
                type,
                shareContent + "?hideDown=0",
                des,
                des,
                Custom.LOGO_URL,
                false,
                isWX_MustTitle);
        //分享文章
        getPearlByShareArticle();
    }

    public void dissmissPop()//关闭弹窗
    {
        if (popupWindow != null && popupWindow.isShowing()) {
            webShareActivityPop.backNormalPopBg();
            webShareActivityPop = null;
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//关闭弹窗
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    dissmissPop();
                    return true;
                }
            }
            //分享弹窗关闭
            if (signInSuccessPop != null) {
                if (popSignSuccess != null) {
                    if (popSignSuccess.isShowing()) {
                        signInSuccessPop.dismissPop();
                        signInSuccessPop = null;
                        popSignSuccess = null;
                        return true;
                    }
                }
            }
            ViewManager.getInstance().finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
