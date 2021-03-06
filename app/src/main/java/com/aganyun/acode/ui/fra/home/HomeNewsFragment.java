package com.aganyun.acode.ui.fra.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.adapter.HomeAnlyseAdapter;
import com.aganyun.acode.adapter.HomeBihuAdapter;
import com.aganyun.acode.adapter.HomeExtralAdapter;
import com.aganyun.acode.adapter.HomeNewsAdapter;
import com.aganyun.acode.adapter.HomePolicysAdapter;
import com.aganyun.acode.adapter.WebImgPagerAdapter;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.ShuffingBean;
import com.aganyun.acode.http.content.ContentLoader;
import com.aganyun.acode.http.content.bean.AddNumBean;
import com.aganyun.acode.http.content.bean.GetColumnsBean;
import com.aganyun.acode.http.content.bean.GetExtraIdsBean;
import com.aganyun.acode.http.content.bean.GetNewsBean;
import com.aganyun.acode.http.content.bean.GetPolicysBean;
import com.aganyun.acode.http.content.bean.GetQuotationsBean;
import com.aganyun.acode.ui.act.other.WebActivity;
import com.aganyun.acode.ui.act.other.WebShareActivity;
import com.aganyun.acode.ui.base.BaseLazyLoadFragment;
import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ResourceUtils;
import com.aganyun.acode.utils.TypeTransform;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class HomeNewsFragment extends BaseLazyLoadFragment implements HomeNewsAdapter.ClickItemCallback,
        HomePolicysAdapter.ClickItemCallback,
        WebImgPagerAdapter.ClickImgCallback,
        HomeAnlyseAdapter.ClickItemCallback,
        HomeBihuAdapter.ClickItemCallback,
        HomeExtralAdapter.ClickItemCallback {

    private RollPagerView homenewfraShuffinf;
    private NestedScrollView nestedScrollView;
    private FrameLayout shuffingLayout;
    private TextView tvLine;
    private RecyclerView rvHomenewfra;
    private SwipeRefreshLayout refresh;
    private HomeNewsAdapter homeNewsAdapter;
    private HomePolicysAdapter homePolicysAdapter;
    private HomeAnlyseAdapter homeAnlyseAdapter;
    private HomeBihuAdapter homeBihuAdapter;
    private HomeExtralAdapter homeExtralAdapter;
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
    //??????????????????
    private boolean needShuffing = false;
    //4?????????
    private int whichType = -1;
    //???????????????????????????
    private boolean isFirstLoadShuffing = true;
    //????????????
    private String searchContent = null;

    @SuppressLint({"NewApi", "ValidFragment"})
    public HomeNewsFragment() {
    }

    @Override
    public void loadData() {
        //?????????????????????
        getDate(currentPage, false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_news;
    }

    public static Fragment newInstance(boolean needShuffing, int whichType) {
        HomeNewsFragment fragment = new HomeNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("needShuffing", needShuffing);
        bundle.putInt("type", whichType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        homenewfraShuffinf = view.findViewById(R.id.homenewfra_shuffinf);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        shuffingLayout = view.findViewById(R.id.shuffing_layout);
        tvLine = view.findViewById(R.id.tv_line);
        rvHomenewfra = view.findViewById(R.id.rv_homenewfra);
        refresh = view.findViewById(R.id.refresh);
        if (getArguments() != null) {
            needShuffing = getArguments().getBoolean("needShuffing");
            whichType = getArguments().getInt("type");
        }
        if (!needShuffing) {
            shuffingLayout.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
        }
        initRecycler();
    }

    //----------------------------------------?????????------------------------------------------------
    //??????????????????
    private void initRecycler() {
        //???????????????
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvHomenewfra.setLayoutManager(linearLayoutManager);
        rvHomenewfra.setNestedScrollingEnabled(false);
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
                getDate(currentPage, false);
            }
        });
    }

    //????????????
    private void initShuffing(List<ShuffingBean> list) {
        homenewfraShuffinf.setPlayDelay(3000);
        homenewfraShuffinf.setAnimationDurtion(500);
        homenewfraShuffinf.setHintPadding(-1, -1, -1, DensityUtil.dip2px(AppUtils.getAppContext(), 10F));
        homenewfraShuffinf.setHintView(
                new ColorPointHintView(getContext(),
                        ResourceUtils.getResourceColor(getActivity(), R.color.indicator_color),
                        ResourceUtils.getResourceColor(getActivity(), R.color.white)));
        WebImgPagerAdapter webImgPagerAdapter = new WebImgPagerAdapter(list, getContext(), null);
        webImgPagerAdapter.setCallBack(HomeNewsFragment.this);
        homenewfraShuffinf.setAdapter(webImgPagerAdapter);
    }

    //???????????????????????????
    private void newsloadMoreCallback() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) && !mIsRefreshing) {
                    // TODO: ????????????????????????   ??????????????????bug-2?????????????????????????????????-
                    mIsRefreshing = true;
                    currentPage++;
                    //?????????????????????????????????
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    getDate(currentPage, true);
                }
            }
        });
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

    //??????????????????
    public void UpDateInterface(String content) {
        searchContent = content;
        currentPage = 1;
        isLoadAll = false;
        getDate(currentPage, false);
    }

    //??????4????????????????????????
    public void getDate(int page, boolean isBottom) {
        switch (whichType) {
            case 0:
                getNewsDate(page, isBottom);
                break;
            case 1:
                getExtraIds(page, isBottom);
                break;
            case 2:
                getAnalyseDate(page, isBottom);
                break;
            case 3:
                getBihuDate(page, isBottom);
                break;
            case 4:
                getPolicyDate(page, isBottom);
                break;
            case 5:
                break;
        }
    }

    //-----------------------------------------??????-----------------------------------------
    //??????????????????
    private void getNewsDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetNewsBean> httpListener = new HttpListener<GetNewsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("?????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetNewsBean getNewsBean) {
                stopRefresh();
                if (getNewsBean.getCode() == 500) {
                    ToastUtils.show(getNewsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getNewsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("???????????????!");
                    if (homeNewsAdapter.getListSize() != 0) {
                        homeNewsAdapter.clearData();
                    }
                    return;
                }
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("????????????: ", "???????????????????????????");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //FIXME ????????????
                if (needShuffing) {
                    if (isFirstLoadShuffing) {
                        isFirstLoadShuffing = false;
                        //?????????
                        List<ShuffingBean> list = new ArrayList<>();
                        for (int i = 0; i < getNewsBean.getData().getResponse().getAdvRows().size(); i++) {
                            ShuffingBean shuffingBean = new ShuffingBean(
                                    getNewsBean.getData().getResponse().getAdvRows().get(i).getImgUrl(),
                                    getNewsBean.getData().getResponse().getAdvRows().get(i).getLinkUrl());
                            list.add(shuffingBean);
                        }
                        initShuffing(list);
                    }
                }
                //??????????????????
                List<GetNewsBean.DataBean.ResponseBean.RowsBean> list = getNewsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (homeNewsAdapter == null) {
                    homeNewsAdapter = new HomeNewsAdapter(getContext(), list);
                    homeNewsAdapter.setNewsOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeNewsAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    homeNewsAdapter.addBottomData(list);
                } else {
                    homeNewsAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getNews(Custom.APPID, true, page, 10, searchContent, httpListener);
    }

    //??????????????????
    private void getExtraIds(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetExtraIdsBean> httpListener = new HttpListener<GetExtraIdsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("?????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetExtraIdsBean getExtraIdsBean) {
                stopRefresh();
                if (getExtraIdsBean.getCode() == 500) {
                    ToastUtils.show(getExtraIdsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getExtraIdsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
//                    ToastUtils.show("???????????????!");
                    if (homeExtralAdapter.getListSize() != 0) {
                        homeExtralAdapter.clearData();
                    }
                    return;
                }
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("????????????: ", "???????????????????????????");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }

                //??????????????????
                List<GetExtraIdsBean.DataBean.ResponseBean.RowsBean> list = getExtraIdsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (homeExtralAdapter == null) {
                    homeExtralAdapter = new HomeExtralAdapter(getContext(), list);
                    homeExtralAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeExtralAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    homeExtralAdapter.addBottomData(list);
                } else {
                    homeExtralAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getExtraIds(Custom.APPID, page, 10, searchContent, httpListener);
    }

    //??????????????????
    private void getAnalyseDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetQuotationsBean> httpListener = new HttpListener<GetQuotationsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetQuotationsBean getQuotationsBean) {
                stopRefresh();
                ViseLog.e("????????????????????????:" + getQuotationsBean);
                if (getQuotationsBean.getCode() == 500) {
                    ToastUtils.show(getQuotationsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getQuotationsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("???????????????!");
                    if (homeAnlyseAdapter.getListSize() != 0) {
                        homeAnlyseAdapter.clearData();
                    }
                    return;
                }
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("????????????: ", "??????????????????");
                    newsloadMoreCallback();
//                    rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //??????????????????
                List<GetQuotationsBean.DataBean.ResponseBean.RowsBean> list = getQuotationsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (homeAnlyseAdapter == null) {
                    homeAnlyseAdapter = new HomeAnlyseAdapter(getContext(), list);
                    homeAnlyseAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeAnlyseAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    homeAnlyseAdapter.addBottomData(list);
                } else {
                    homeAnlyseAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getQuotations(Custom.APPID, page, 10, searchContent, httpListener);
    }

    //??????????????????
    private void getBihuDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetColumnsBean> httpListener = new HttpListener<GetColumnsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetColumnsBean getColumnsBean) {
                stopRefresh();
                if (getColumnsBean.getCode() == 500) {
                    ToastUtils.show(getColumnsBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getColumnsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("???????????????!");
                    if (homeBihuAdapter.getListSize() != 0) {
                        homeBihuAdapter.clearData();
                    }
                    return;
                }
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("????????????: ", "???????????????????????????");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //FIXME ????????????
                if (needShuffing) {
                    if (isFirstLoadShuffing) {
                        isFirstLoadShuffing = false;
                        //?????????
                        List<ShuffingBean> list = new ArrayList<>();
                        for (int i = 0; i < getColumnsBean.getData().getResponse().getAdvRows().size(); i++) {
                            ShuffingBean shuffingBean = new ShuffingBean(
                                    getColumnsBean.getData().getResponse().getAdvRows().get(i).getImgUrl(),
                                    getColumnsBean.getData().getResponse().getAdvRows().get(i).getLinkUrl());
                            list.add(shuffingBean);
                        }
                        initShuffing(list);
                    }
                }
                //??????????????????
                List<GetColumnsBean.DataBean.ResponseBean.RowsBean> list = getColumnsBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (homeBihuAdapter == null) {
                    homeBihuAdapter = new HomeBihuAdapter(getContext(), list);
                    homeBihuAdapter.setNewsOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeBihuAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    homeBihuAdapter.addBottomData(list);
                } else {
                    homeBihuAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getColumns(Custom.APPID, true, page, 10, searchContent, httpListener);
    }

    //??????????????????
    private void getPolicyDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("???????????????????????????!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetPolicysBean> httpListener = new HttpListener<GetPolicysBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("?????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetPolicysBean getPolicysBean) {
                stopRefresh();
                if (getPolicysBean.getCode() == 500) {
                    ToastUtils.show(getPolicysBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????.?????????return
                allDataNum = getPolicysBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("???????????????!");
                    if (homePolicysAdapter.getListSize() != 0) {
                        homePolicysAdapter.clearData();
                    }
                    return;
                }
                //??????????????????
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME ????????????????????????,??????1??????????????????????????????????????????
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("????????????: ", "??????????????????");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //??????????????????
                List<GetPolicysBean.DataBean.ResponseBean.RowsBean> list = getPolicysBean.getData().getResponse().getRows();
                //?????????????????????????????????????????????????????????????????????????????????
                if (homePolicysAdapter == null) {
                    homePolicysAdapter = new HomePolicysAdapter(getContext(), list);
                    homePolicysAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homePolicysAdapter);
                    return;
                }
                //????????????????????????????????????
                if (isBottom) {
                    homePolicysAdapter.addBottomData(list);
                } else {
                    homePolicysAdapter.refreshData(list);
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.getPolicys(Custom.APPID, page, 10, searchContent, httpListener);
    }

    //?????????????????????
    private void addNum(String id) {
        HttpListener<AddNumBean> httpListener = new HttpListener<AddNumBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(AddNumBean addNumBean) {
                ViseLog.e("????????????????????????:" + addNumBean);
                if (addNumBean.getCode() == 500) {
                    ToastUtils.show(addNumBean.getMessages().get(0).getMessage());
                    return;
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.addNum(Custom.APPID, TypeTransform.strToLong(id), httpListener);
    }

    //----------------------------------------------??????--------------------------------------------
    /*????????????*/
    @Override
    public void clickNewsItem(String content, String webUrl, String id, int po, String title) {
        Log.e("clickNewsItem: ", "webUrl=" + webUrl);
        addNum(id);
        homeNewsAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, content, webUrl, title);
    }

    /*????????????*/
    @Override
    public void clickAnalyse(String webTitle, String webUrl, String id, int position, String content) {
        addNum(id);
        homeAnlyseAdapter.lookNumUpOne(position);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, content);
    }

    /*????????????*/
    @Override
    public void clickBihuItem(String webTitle, String webUrl, String id, int po, String content) {
        addNum(id);
        homeBihuAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, content);
    }

    /*????????????*/
    @Override
    public void clickPolicy(String webTitle, String webUrl, String id, int po, String title) {
        addNum(id);
        homePolicysAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, title);
    }

    /*????????????*/
    @Override
    public void clickExtral(String webTitle, String webUrl, String id, int position, String title) {
        addNum(id);
        homeExtralAdapter.lookNumUpOne(position);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, title);
    }

    /*????????????*/
    @Override
    public void clickImg(String webUrl) {
        ChangeActUtils.changeActivity_2_webAct(getContext(), WebActivity.class, "????????????", webUrl);
    }
}
