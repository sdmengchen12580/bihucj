package com.aganyun.acode.ui.fra.quicknews;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import com.aganyun.acode.R;
import com.aganyun.acode.adapter.QuickNewsInnerAdapter;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.quicknewsbus.ClickShareBus;
import com.aganyun.acode.http.content.ContentLoader;
import com.aganyun.acode.http.content.bean.GetFlashsBean;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetPearlByShareFlashBean;
import com.aganyun.acode.ui.act.other.WebActivity;
import com.aganyun.acode.ui.act.search.SearchActivity;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.ui.base.BaseFragment;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.PermissionsUtils;
import com.aganyun.acode.utils.RecyclerViewUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.view.FragmentSharePop;
import com.aganyun.acode.view.signin.SignInSuccessPop;
import com.aganyun.acode.ymshare.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.lcsyjt.mylibrary.http.callback.HttpListener;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class QuickNewsFragment extends BaseFragment {


    private RecyclerView rvQuicknews;
    private SwipeRefreshLayout refresh;
    private QuickNewsInnerAdapter quickNewsInnerAdapter;
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
    //??????
    private FragmentSharePop fragmentSharePop;
    public PopupWindow popupWindow;
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
    //???????????????
    public String shareContent;
    public String shareTitle;
    public String shareTime;
    //?????????????????????
    public boolean newsHasShared = false;
    //????????????????????????????????????
    public String shareFlash = null;
    //????????????????????????
    public String suger = "0";
    public PopupWindow popSignSuccess;
    public SignInSuccessPop signInSuccessPop;
    private boolean isFirst = true;

    @SuppressLint({"NewApi", "ValidFragment"})
    public QuickNewsFragment() {
        // Required empty public constructor
    }

    public static QuickNewsFragment newInstance() {
        QuickNewsFragment fragment = new QuickNewsFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_quick_nesw;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        onViewClicked(view);
        initEventBus();
        initRv();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (newsHasShared) {
            newsHasShared = false;
            if (!suger.trim().toString().equals("0")) {
                showSignSuccessPop("+" + suger);
            }
        }
    }

    //???????????????????????????
    public void showSignSuccessPop(String sugerNum) {
        signInSuccessPop = new SignInSuccessPop((BaseActivity) getActivity());
        signInSuccessPop.setAutoHidePopCallback(new SignInSuccessPop.AutoHidePopCallback() {
            @Override
            public void autoHidePop() {
                signInSuccessPop.dismissPop();
                signInSuccessPop = null;
                popSignSuccess = null;
            }
        });
        popSignSuccess = signInSuccessPop.showPop(sugerNum,false);
    }

    //---------------------------------------?????????------------------------------------------
    private void initRv() {
        //???????????????
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvQuicknews.setLayoutManager(linearLayoutManager);
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
                getQuickNewsDate(currentPage, false);
            }
        });
        //?????????????????????
        getQuickNewsDate(currentPage, false);
    }

    //??????????????????
    private RecyclerView.OnScrollListener OnLoadMoreListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("????????????", "?????????");
                boolean isBottom = RecyclerViewUtils.isVisBottom(recyclerView);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom && !mIsRefreshing) {
                    // TODO: ????????????????????????   ??????????????????bug-2?????????????????????????????????-
                    mIsRefreshing = true;
                    currentPage++;
                    //?????????????????????????????????
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    getQuickNewsDate(currentPage, true);
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

    //????????????
    public void dissmissPop() {
        if (popupWindow != null && popupWindow.isShowing()) {
            fragmentSharePop.backNormalPopBg();
            fragmentSharePop = null;
            popupWindow.dismiss();
            popupWindow = null;
        }
        if (popSignSuccess != null && popSignSuccess.isShowing()) {
            signInSuccessPop.backNormalPopBg();
            signInSuccessPop = null;
            popSignSuccess.dismiss();
            popSignSuccess = null;
        }
    }

    //?????????????????????
    public void shareToWhichPlat(SHARE_MEDIA type, Bitmap bitmap) {
        ShareUtils.newInstance().share2platform_bitmap(getActivity(),
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
        fragmentSharePop = new FragmentSharePop(QuickNewsFragment.this);
        fragmentSharePop.setClickCallback(new FragmentSharePop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String mShareContent, View view) {
//                Bitmap bitmap =  ViewUtils.drawTextOnBitmap(getActivity(), content, 16, Color.WHITE);
//                Bitmap bitmap = ViewUtils.gitBitmapFromView(view);
                Bitmap bitmap = ViewUtils.getBitmapByView((ScrollView) view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("????????????,?????????!");
                    return;
                }
                shareToWhichPlat(type, bitmap);
            }
        });
        popupWindow = fragmentSharePop.showPop(1, shareContent, shareTime, shareTitle);
    }

    //????????????
    public void judgePermissions(String content) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionsUtils.checkPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.CALL_PHONE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_LOGS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SET_DEBUG_APP)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.GET_ACCOUNTS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SYSTEM_ALERT_WINDOW)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.WRITE_APN_SETTINGS)
                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //??????-?????????????????????
                for (int i = 0; i < permissions.length; i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                        Log.e("????????????: ", "?????????");
                        ToastUtils.show("??????????????????????????????????????????,?????????????????????????????????!");
                        break;
                    }
                }
                Log.e("????????????: ", "??????????????????");
                //????????????????????????
                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            } else {
                Log.e("????????????: ", "????????????????????????");
                showSuggestFriendDialog();
            }
        } else {
            Log.e("????????????: ", "?????????,???????????????,????????????");
            showSuggestFriendDialog();
        }
    }

    public void onViewClicked(View view) {
        rvQuicknews = view.findViewById(R.id.rv_quicknews);
        refresh = view.findViewById(R.id.refresh);
        view.findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), SearchActivity.class);
            }
        });
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), SearchActivity.class);
            }
        });
    }

    //-----------------------------------------??????-----------------------------------------
    //??????????????????
    private void getQuickNewsDate(int page, final boolean isBottom) {
        if(isFirst){
            isFirst = false;
            showLoadingDialog("?????????");
        }
        HttpListener<GetFlashsBean> httpListener = new HttpListener<GetFlashsBean>() {
            @Override
            public void onError(String errorMsg) {
                hideLoadingDialog();
                stopRefresh();
                ViseLog.e("?????????????????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetFlashsBean getFlashsBean) {
                hideLoadingDialog();
                stopRefresh();
                if (getFlashsBean.getCode() == 500) {
                    ToastUtils.show(getFlashsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getFlashsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("???????????????!");
                    return;
                }
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                rvQuicknews.addOnScrollListener(OnLoadMoreListener());
                //??????????????????
                List<GetFlashsBean.DataBean.ResponseBean.RowsBean> list = getFlashsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (quickNewsInnerAdapter == null) {
                    quickNewsInnerAdapter = new QuickNewsInnerAdapter(getContext(), list);
                    rvQuicknews.setAdapter(quickNewsInnerAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    quickNewsInnerAdapter.addBottomData(list);
                } else {
                    quickNewsInnerAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getFlashs(Custom.APPID, page, 50, null, httpListener);
    }

    //???????????????
    private void quickNewsShare() {
        String key = (String) SPUtil.get(getContext(), "key", "");
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
        sugerLoader.getPearlByShareFlash(Custom.APPID, key, httpListener);
    }

    //-----------------------------------------eventbus-----------------------------------------
    private void initEventBus() {
        EventBus.getDefault().register(QuickNewsFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(QuickNewsFragment.this);
    }

    //??????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickShareEvent(ClickShareBus clickShareBus) {
        switch (clickShareBus.getType()) {
           /*????????????*/
            case 1:
                ChangeActUtils.changeActivity_2_webAct(getContext(), WebActivity.class, "????????????", clickShareBus.getWebUrl());
                break;
            case 2:
                shareContent = clickShareBus.getShareContent();
                shareTitle = clickShareBus.getTitle();
                shareTime = clickShareBus.getTime();
                judgePermissions(clickShareBus.getShareContent());
                break;
        }
    }
}
