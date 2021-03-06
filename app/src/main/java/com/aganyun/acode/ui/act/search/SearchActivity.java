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
    /*??????*/
    private EditText etSearchContent;
    private ImageView imgDelect;
    private LinearLayout llSearchLayout;
    /*????????????*/
    private TextView tv_has_no_date;
    private RecyclerView rvSearchDate;
    private SwipeRefreshLayout refresh;
    private SearchEndAdapter searchEndAdapter;
    private ShowSearchEndNumPop showSearchEndNumPop;
    /*????????????*/
    private RecoderViewGroup recoderVg;
    private LinearLayout llSearchHistory;
    //????????????
    //??????????????????
    private int allPage = 0;
    //???????????????
    private int allDataNum = 0;
    //??????????????????
    private int currentPage = 1;
    //????????????????????????
    private boolean isLoadAll = false;
    //???????????????
    private boolean mIsRefreshing = false;
    //????????????
    private String searchContent;
    //????????????
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
    //??????????????????
    private SearchActivitySharePop searchActivitySharePop;
    public PopupWindow popupWindow;
    //????????????
    private String shareContent;
    public String shareTitle;
    public String shareTime;
    //?????????????????????
    public boolean newsHasShared = false;
    public PopupWindow popSignSuccess;
    public SignInSuccessPop signInSuccessPop;
    //????????????????????????????????????
    public String shareFlash = null;
    //????????????????????????
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
        //??????????????????
        showSearchHistory();
        //??????????????????
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
        /*??????????????????*/
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
        /*??????*/
        findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                searchContent = etSearchContent.getText().toString().trim();
                if (ObjIsNull.isEmpty(searchContent)) {
                    ToastUtils.show("????????????????????????");
                    return;
                }
                hideKeyboard(etSearchContent);
                hideSearchHistory();
                //??????????????????
                UserUtils.putShopRecoder(searchContent);
                isLoadAll = false;
                mIsRefreshing = false;
                currentPage = 1;
                search(searchContent, currentPage, false);
            }
        });
        /*??????*/
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        });
        /*??????????????????*/
        findViewById(R.id.ll_delect_search_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //?????????????????????
                if (recoderVg.getChildCount() > 0) {
                    recoderVg.removeAllViews();
                }
                //??????sp
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

    //-----------------------------------?????????----------------------------------------------
    //???????????????????????????
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

    //??????????????????
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

    //??????????????????
    private void hideSearchHistory() {
        llSearchHistory.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        rvSearchDate.setVisibility(View.VISIBLE);
    }

    //edittext????????????
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

    //?????????????????????????????????
    private static class MyHandler extends Handler {
        //???????????????HandlerActivity,GC????????????????????????.
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

    //??????????????????
    private RecyclerView.OnScrollListener OnLoadMoreListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = RecyclerViewUtils.isVisBottom(recyclerView);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom && !mIsRefreshing) {
                    // TODO: ????????????????????????   ??????????????????bug-2?????????????????????????????????-
                    mIsRefreshing = true;
                    currentPage++;
                    //?????????????????????????????????
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    search(searchContent, currentPage, true);
                }
            }
        };
    }

    //????????????
    private void stopRefresh() {
        mIsRefreshing = false;
        if (refresh != null) {
            //??????????????????ui
            if (refresh.isRefreshing()) {
                refresh.setRefreshing(false);
            }
        }
    }

    //------------------------------------------??????----------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtils.cancelSub(search);
        OtherUtils.cancelSub(quickNewsShare);
    }

    private void search(String content, int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetFlashsBean> httpListener = new HttpListener<GetFlashsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("?????????????????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetFlashsBean getFlashsBean) {
                stopRefresh();
                if (getFlashsBean.getCode() == 500) {
                    ToastUtils.show(getFlashsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
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
                //FIXME ????????????
                if (showSearchEndNumPop != null) {
                    showSearchEndNumPop.dismissPop();
                    showSearchEndNumPop = null;
                }
                showSearchEndNumPop = new ShowSearchEndNumPop(SearchActivity.this, llSearchLayout);
                showSearchEndNumPop.showPop(allDataNum + "");
                myHandler.sendEmptyMessageDelayed(1, 2000);
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    rvSearchDate.addOnScrollListener(OnLoadMoreListener());
                }
                //??????????????????
                List<GetFlashsBean.DataBean.ResponseBean.RowsBean> list = getFlashsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (searchEndAdapter == null) {
                    searchEndAdapter = new SearchEndAdapter(SearchActivity.this, list);
                    searchEndAdapter.setOnItenClickCallBack(SearchActivity.this);
                    rvSearchDate.setAdapter(searchEndAdapter);
                    return;
                }
                //????????????????????????????????????
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

    //???????????????
    private void quickNewsShare() {
        String key = (String) SPUtil.get(SearchActivity.this, "key", "");
        HttpListener<GetPearlByShareFlashBean> httpListener = new HttpListener<GetPearlByShareFlashBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("????????????????????????:" + errorMsg);
            }

            @Override
            public void onSuccess(GetPearlByShareFlashBean getPearlByShareFlashBean) {
                ViseLog.e("????????????????????????:" + getPearlByShareFlashBean);
                if (getPearlByShareFlashBean.getCode() == 500) {
                    ToastUtils.show(getPearlByShareFlashBean.getMessages().get(0).getMessage());
                    return;
                }
                shareFlash = getPearlByShareFlashBean.getData().getResponse().getShareFlash();
                suger = getPearlByShareFlashBean.getData().getResponse().getPearl();
                //????????????,???????????????
                newsHasShared = true;
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        quickNewsShare = sugerLoader.getPearlByShareFlash(Custom.APPID, key, httpListener);
    }

    //-------------------------------------------------??????------------------------------------------
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

    //-------------------------------------------------??????------------------------------------------
    //?????????????????????
    public void shareToWhichPlat(SHARE_MEDIA type, Bitmap bitmap) {
        ShareUtils.newInstance().share2platform_bitmap(SearchActivity.this,
                type,
                shareTitle,
                bitmap, false, null);
        //??????
        quickNewsShare();
    }

    //??????????????????
    public void showSuggestFriendDialog() {
        Log.e("????????????: ", "????????????:" + shareContent);
        Log.e("????????????: ", "????????????:" + shareTitle);
        Log.e("????????????: ", "????????????:" + shareTime);
        SoftKeyboardUtil.hideSoftKeyboard(SearchActivity.this);
        searchActivitySharePop = new SearchActivitySharePop(SearchActivity.this);
        searchActivitySharePop.setClickCallback(new SearchActivitySharePop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String mShareContent, View view) {
//                Bitmap bitmap = ViewUtils.drawTextOnBitmap(SearchActivity.this, content, 16, Color.WHITE);
//                Bitmap bitmap = ViewUtils.gitBitmapFromView(view);
                Bitmap bitmap = ViewUtils.getBitmapByView((ScrollView) view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("????????????,?????????!");
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

    //????????????
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
                //??????-?????????????????????
                for (int i = 0; i < permissions.length; i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SearchActivity.this, permissions[i])) {
                        Log.e("????????????: ", "?????????");
                        ToastUtils.show("??????????????????????????????????????????,?????????????????????????????????!");
                        break;
                    }
                }
                Log.e("????????????: ", "??????????????????");
                //????????????????????????
                ActivityCompat.requestPermissions(SearchActivity.this, permissions, 1);
            } else {
                Log.e("????????????: ", "????????????????????????");
                showSuggestFriendDialog();
            }
        } else {
            Log.e("????????????: ", "?????????,???????????????,????????????");
            showSuggestFriendDialog();
        }
    }

    //????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("????????????: ", "??????");
        switch (requestCode) {
            //????????????
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("????????????: ", "??????????????????????????????");
                    showSuggestFriendDialog();
                    return;
                }
                //?????????-?????????????????????
                ToastUtils.show("??????????????????????????????????????????????????????!");
                PermissionsUtils.toAppSetting(SearchActivity.this);
                break;
        }
    }

    /*?????????????????????*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    dissmissPop();
                    return true;
                }
            }
            //????????????????????????
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

    //????????????
    public void dissmissPop() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                searchActivitySharePop.backNormalPopBg();
                searchActivitySharePop = null;
                popupWindow.dismiss();
                popupWindow = null;
            }
        }
        //????????????
        if (popSignSuccess != null && popSignSuccess.isShowing()) {
            signInSuccessPop.backNormalPopBg();
            signInSuccessPop = null;
            popSignSuccess.dismiss();
            popSignSuccess = null;
        }
    }
}
