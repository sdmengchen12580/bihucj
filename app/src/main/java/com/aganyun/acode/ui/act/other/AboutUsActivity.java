package com.aganyun.acode.ui.act.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.other.OtherLoader;
import com.aganyun.acode.http.other.bean.AboutBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.vise.log.ViseLog;
import org.lcsyjt.mylibrary.http.callback.HttpListener;
import rx.Subscription;

public class AboutUsActivity extends BaseActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private ImageView imgIcon;
    private TextView tvVersion;
    private Subscription aboutRequest;

    @Override
    protected int getViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        aboutRequest(Custom.APPID);
    }

    public void onViewClicked() {
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tv4 = findViewById(R.id.tv_4);
        imgIcon = findViewById(R.id.img_icon);
        tvVersion = findViewById(R.id.tv_version);
        /*返回*/
        findViewById(R.id.ll_about_us_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        });
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtils.cancelSub(aboutRequest);
    }

    //-----------------------------------------接口---------------------------------------------
    public void aboutRequest(String appId) {
        HttpListener<AboutBean> httpListener = new HttpListener<AboutBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试关于我们失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(AboutBean aboutBean) {
                ViseLog.e("测试关于我们成功:" + aboutBean);
                if (aboutBean.getCode() == 500) {
                    ToastUtils.show(aboutBean.getMessages().get(0).getMessage());
                    return;
                }
                tv1.setText(getString(R.string._str_about_us_1) + aboutBean.getData().getResponse().getBusineCooperate());
                tv2.setText(getString(R.string._str_about_us_2) + aboutBean.getData().getResponse().getFeedback());
                tv3.setText(getString(R.string._str_about_us_3) + aboutBean.getData().getResponse().getContribute());
                tv4.setText(getString(R.string._str_about_us_4) + aboutBean.getData().getResponse().getSuppertWeChat());
                GlideUtils.glideLoadImg(AboutUsActivity.this,
                        aboutBean.getData().getResponse().getWeIconUrl(),
                        imgIcon);
                tvVersion.setText(aboutBean.getData().getResponse().getVerson());
            }
        };
        OtherLoader otherLoader = new OtherLoader(OtherLoader.BASEURL);
        aboutRequest = otherLoader.about(appId, httpListener);
    }
}
