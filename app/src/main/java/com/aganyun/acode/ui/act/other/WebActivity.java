package com.aganyun.acode.ui.act.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.stack.ViewManager;

public class WebActivity extends BaseActivity {

    private TextView tvTitleContent;
    private WebView webview;

    @Override
    protected int getViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        Intent intent = getIntent();
        String webUrl = intent.getStringExtra("weburl");
        String titleContent = intent.getStringExtra("title");
        tvTitleContent.setText(titleContent);
        //加载网页
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webview.loadUrl(url);
                return false;
            }
        });
        webview.loadUrl(webUrl);
    }

    public void onViewClicked() {
        tvTitleContent = findViewById(R.id.tv_title_content);
        webview = findViewById(R.id.webview);
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
}
