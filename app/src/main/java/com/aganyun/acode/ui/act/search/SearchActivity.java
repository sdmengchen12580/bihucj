package com.aganyun.acode.ui.act.search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.adapter.SearchEndAdapter;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.content.ContentLoader;
import com.aganyun.acode.http.content.bean.GetFlashsBean;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetPearlByShareFlashBean;
import com.aganyun.acode.ui.act.other.WebActivity;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.PermissionsUtils;
import com.aganyun.acode.utils.RecyclerViewUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.SoftKeyboardUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.utils.user.UserUtils;
import com.aganyun.acode.view.SearchActivitySharePop;
import com.aganyun.acode.view.RecoderViewGroup;
import com.aganyun.acode.view.ShowSearchEndNumPop;
import com.aganyun.acode.view.signin.SignInSuccessPop;
import com.aganyun.acode.ymshare.ShareUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscription;

public class SearchActivity extends BaseActivity implements SearchEndAdapter.ClickItemCallback {

    private MyHandler myHandler;
    /*搜索*/
    private EditText etSearchContent;
    private ImageView imgDelect;
    private LinearLayout llSearchLayout;
    /*搜索结果*/
    private TextView tv_has_no_date;
    private RecyclerView rvSearchDate;
    private SwipeRefreshLayout refresh;
    private SearchEndAdapter searchEndAdapter;
    private ShowSearchEndNumPop showSearchEndNumPop;
    /*搜索历史*/
    private RecoderViewGroup recoderVg;
    private LinearLayout llSearchHistory;
    //刷新加载
    //总的数据页面
    private int allPage = 0;
    //总的数据量
    private int allDataNum = 0;
    //当前加载页数
    private int currentPage = 1;
    //数据是否加载完毕
    private boolean isLoadAll = false;
    //是否刷新中
    private boolean mIsRefreshing = false;
    //搜索内容
    private String searchContent;
    //分享权限
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.READ_LOGS,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.SET_DEBUG_APP,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.GET_ACCOUNTS,
//            Manifest.permission.WRITE_APN_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE,};
    //推荐好友弹窗
    private SearchActivitySharePop searchActivitySharePop;
    public PopupWindow popupWindow;
    //分享内容
    private String shareContent;
    public String shareTitle;
    public String shareTime;
    //文章是否有分享
    public boolean newsHasShared = false;
    public PopupWindow popSignSuccess;
    public SignInSuccessPop signInSuccessPop;
    //分享次數或者分享超过次数
    public String shareFlash = null;
    //分享送的糖果个数
    public String suger = "0";
    private Subscription search;
    private Subscription quickNewsShare;

    @Override
    protected int getViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        myHandler = new MyHandler(SearchActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        rvSearchDate.setLayoutManager(linearLayoutManager);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadAll = false;
                if (mIsRefreshing) {
                    return;
                }
                mIsRefreshing = true;
                currentPage = 1;
                search(searchContent, currentPage, false);
            }
        });
        //显示搜索历史
        showSearchHistory();
        //搜索内容监听
        initSearchContentListener();
    }

    public void onViewClicked() {
        tv_has_no_date = findViewById(R.id.tv_has_no_date);
        rvSearchDate = findViewById(R.id.rv_search_date);
        etSearchContent = findViewById(R.id.et_search_content);
        imgDelect = findViewById(R.id.img_delect);
        llSearchLayout = findViewById(R.id.ll_search_layout);
        refresh = findViewById(R.id.refresh);
        recoderVg = findViewById(R.id.recoder_vg);
        llSearchHistory = findViewById(R.id.ll_search_history);
        /*删除搜索内容*/
        findViewById(R.id.img_delect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                searchContent = null;
                etSearchContent.setText("");
                imgDelect.setVisibility(View.GONE);
            }
        });
        /*搜索*/
        findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                searchContent = etSearchContent.getText().toString().trim();
                if (ObjIsNull.isEmpty(searchContent)) {
                    ToastUtils.show("请输入搜索关键词");
                    return;
                }
                hideKeyboard(etSearchContent);
                hideSearchHistory();
                //存入搜索内容
                UserUtils.putShopRecoder(searchContent);
                isLoadAll = false;
                mIsRefreshing = false;
                currentPage = 1;
                search(searchContent, currentPage, false);
            }
        });
        /*退出*/
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        });
        /*删除搜索历史*/
        findViewById(R.id.ll_delect_search_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //清空所有子控件
                if (recoderVg.getChildCount() > 0) {
                    recoderVg.removeAllViews();
                }
                //清空sp
                UserUtils.clearHis();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (showSearchEndNumPop != null) {
            showSearchEndNumPop.dismissPop();
            showSearchEndNumPop = null;
        }
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

    //-----------------------------------工具类----------------------------------------------
    //显示登录成功的弹窗
    public void showSignSuccessPop(String sugerNum) {
        signInSuccessPop = new SignInSuccessPop((BaseActivity) SearchActivity.this);
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

    //显示搜索历史
    private void showSearchHistory() {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(AppUtils.getAppContext(), 30));
        if (UserUtils.getShopRecoder() == null) {
            return;
        }
        List<String> list = UserUtils.getShopRecoder();
        for (int i = 0; i < list.size(); i++) {
            final TextView textView = new TextView(SearchActivity.this);
            textView.setText(list.get(i));
            textView.setMaxLines(1);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(DensityUtil.dip2px(AppUtils.getAppContext(), 10),
                    DensityUtil.dip2px(AppUtils.getAppContext(), 2),
                    DensityUtil.dip2px(AppUtils.getAppContext(), 10),
                    DensityUtil.dip2px(AppUtils.getAppContext(), 2));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextColor(getResources().getColor(R.color.tx_color_333333));
            textView.setBackgroundResource(R.drawable.bt_xml_15radiu_brownsolid);
            recoderVg.addView(textView, lp);
            textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                hideSearchHistory();
                                                searchContent = textView.getText().toString().trim();
                                                etSearchContent.setText(searchContent);
                                                search(searchContent, currentPage, false);
                                            }
                                        }
            );
        }
    }

    //隐藏搜索历史
    private void hideSearchHistory() {
        llSearchHistory.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        rvSearchDate.setVisibility(View.VISIBLE);
    }

    //edittext文字监听
    private void initSearchContentListener() {
        etSearchContent.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                String content = etSearchContent.getText().toString().trim();
                if (!ObjIsNull.isEmpty(content)) {
                    imgDelect.setVisibility(View.VISIBLE);
                } else {
                    imgDelect.setVisibility(View.GONE);
                }
            }
        });
    }

    //隐藏搜索结果数量的弹窗
    private static class MyHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<SearchActivity> mActivty;

        public MyHandler(SearchActivity activity) {
            mActivty = new WeakReference<SearchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SearchActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                if (msg.what == 1) {
                    if (activity.showSearchEndNumPop != null) {
                        activity.showSearchEndNumPop.dismissPop();
                        activity.showSearchEndNumPop = null;
                    }
                }
            }
        }
    }

    //加载更多监听
    private RecyclerView.OnScrollListener OnLoadMoreListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = RecyclerViewUtils.isVisBottom(recyclerView);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom && !mIsRefreshing) {
                    // TODO: 考虑底部添加布局   可能此处存在bug-2次触发滑动到底部的监听-
                    mIsRefreshing = true;
                    currentPage++;
                    //当前页数大于所有的页数
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    search(searchContent, currentPage, true);
                }
            }
        };
    }

    //停止刷新
    private void stopRefresh() {
        mIsRefreshing = false;
        if (refresh != null) {
            //关闭掉刷新的ui
            if (refresh.isRefreshing()) {
                refresh.setRefreshing(false);
            }
        }
    }

    //------------------------------------------接口----------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtils.cancelSub(search);
        OtherUtils.cancelSub(quickNewsShare);
    }

    private void search(String content, int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetFlashsBean> httpListener = new HttpListener<GetFlashsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试获取我的发言失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetFlashsBean getFlashsBean) {
                stopRefresh();
                if (getFlashsBean.getCode() == 500) {
                    ToastUtils.show(getFlashsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getFlashsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    tv_has_no_date.setVisibility(View.VISIBLE);
                    rvSearchDate.setVisibility(View.GONE);
                    if (searchEndAdapter.getListSize() != 0) {
                        searchEndAdapter.clearData();
                    }
                    return;
                }
                tv_has_no_date.setVisibility(View.GONE);
                rvSearchDate.setVisibility(View.VISIBLE);
                //FIXME 数据弹窗
                if (showSearchEndNumPop != null) {
                    showSearchEndNumPop.dismissPop();
                    showSearchEndNumPop = null;
                }
                showSearchEndNumPop = new ShowSearchEndNumPop(SearchActivity.this, llSearchLayout);
                showSearchEndNumPop.showPop(allDataNum + "");
                myHandler.sendEmptyMessageDelayed(1, 2000);
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    rvSearchDate.addOnScrollListener(OnLoadMoreListener());
                }
                //当前页的数据
                List<GetFlashsBean.DataBean.ResponseBean.RowsBean> list = getFlashsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (searchEndAdapter == null) {
                    searchEndAdapter = new SearchEndAdapter(SearchActivity.this, list);
                    searchEndAdapter.setOnItenClickCallBack(SearchActivity.this);
                    rvSearchDate.setAdapter(searchEndAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
                if (isBottom) {
                    searchEndAdapter.addBottomData(list);
                } else {
                    searchEndAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        search = contentLoader.getFlashs(Custom.APPID, page, 10, content, httpListener);
    }

    //快讯的分享
    private void quickNewsShare() {
        String key = (String) SPUtil.get(SearchActivity.this, "key", "");
        HttpListener<GetPearlByShareFlashBean> httpListener = new HttpListener<GetPearlByShareFlashBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试快讯分享失败:" + errorMsg);
            }

            @Override
            public void onSuccess(GetPearlByShareFlashBean getPearlByShareFlashBean) {
                ViseLog.e("测试快讯分享失败:" + getPearlByShareFlashBean);
                if (getPearlByShareFlashBean.getCode() == 500) {
                    ToastUtils.show(getPearlByShareFlashBean.getMessages().get(0).getMessage());
                    return;
                }
                shareFlash = getPearlByShareFlashBean.getData().getResponse().getShareFlash();
                suger = getPearlByShareFlashBean.getData().getResponse().getPearl();
                //满足条件,回来再弹窗
                newsHasShared = true;
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        quickNewsShare = sugerLoader.getPearlByShareFlash(Custom.APPID, key, httpListener);
    }

    //-------------------------------------------------回调------------------------------------------
    @Override
    public void clickCancelAttention(String webTitle, String webUrl) {
        ChangeActUtils.changeActivity_2_webAct(SearchActivity.this, WebActivity.class, webTitle, webUrl);
    }

    @Override
    public void clickShare(String content, String time, String title) {
        shareContent = content;
        shareTitle = title;
        shareTime = time;
        judgePermissions(content);
    }

    //-------------------------------------------------分享------------------------------------------
    //分享到各个平台
    public void shareToWhichPlat(SHARE_MEDIA type, Bitmap bitmap) {
        ShareUtils.newInstance().share2platform_bitmap(SearchActivity.this,
                type,
                shareTitle,
                bitmap, false, null);
        //分享
        quickNewsShare();
    }

    //推荐好友弹窗
    public void showSuggestFriendDialog() {
        Log.e("测试分享: ", "分享内容:" + shareContent);
        Log.e("测试分享: ", "分享标题:" + shareTitle);
        Log.e("测试分享: ", "分享时间:" + shareTime);
        SoftKeyboardUtil.hideSoftKeyboard(SearchActivity.this);
        searchActivitySharePop = new SearchActivitySharePop(SearchActivity.this);
        searchActivitySharePop.setClickCallback(new SearchActivitySharePop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String mShareContent, View view) {
//                Bitmap bitmap = ViewUtils.drawTextOnBitmap(SearchActivity.this, content, 16, Color.WHITE);
//                Bitmap bitmap = ViewUtils.gitBitmapFromView(view);
                Bitmap bitmap = ViewUtils.getBitmapByView((ScrollView) view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("分享失败,请重试!");
                    return;
                }
                shareToWhichPlat(type, bitmap);
            }
        });
        popupWindow = searchActivitySharePop.showPop(shareContent, shareTime, shareTitle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //权限判断
    public void judgePermissions(String content) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionsUtils.checkPermission(SearchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.CALL_PHONE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_LOGS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SET_DEBUG_APP)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.GET_ACCOUNTS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SYSTEM_ALERT_WINDOW)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.WRITE_APN_SETTINGS)
                    || !PermissionsUtils.checkPermission(SearchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //拒绝-点击了不在询问
                for (int i = 0; i < permissions.length; i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SearchActivity.this, permissions[i])) {
                        Log.e("测试分享: ", "拒绝过");
                        ToastUtils.show("您已拒绝分享所用到的相关权限,可到应用管理中打开权限!");
                        break;
                    }
                }
                Log.e("测试分享: ", "开始请求权限");
                //没拒绝去请求权限
                ActivityCompat.requestPermissions(SearchActivity.this, permissions, 1);
            } else {
                Log.e("测试分享: ", "有权限，直接弹窗");
                showSuggestFriendDialog();
            }
        } else {
            Log.e("测试分享: ", "小版本,不申请权限,直接弹窗");
            showSuggestFriendDialog();
        }
    }

    //权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("测试分享: ", "回调");
        switch (requestCode) {
            //快讯回调
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("测试分享: ", "回调，权限通过：弹窗");
                    showSuggestFriendDialog();
                    return;
                }
                //拒绝后-会走这里的方法
                ToastUtils.show("我们需要读写内存的权限去分享您的信息!");
                PermissionsUtils.toAppSetting(SearchActivity.this);
                break;
        }
    }

    /*按两次退出程序*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    dissmissPop();
                    return true;
                }
            }
            //签到成功弹窗关闭
            if (popSignSuccess != null) {
                if (popSignSuccess.isShowing()) {
                    dissmissPop();
                    return true;
                }
            }
            ViewManager.getInstance().finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //关闭弹窗
    public void dissmissPop() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                searchActivitySharePop.backNormalPopBg();
                searchActivitySharePop = null;
                popupWindow.dismiss();
                popupWindow = null;
            }
        }
        //分享成功
        if (popSignSuccess != null && popSignSuccess.isShowing()) {
            signInSuccessPop.backNormalPopBg();
            signInSuccessPop = null;
            popSignSuccess.dismiss();
            popSignSuccess = null;
        }
    }
}
