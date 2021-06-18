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
    //是否需要轮播
    private boolean needShuffing = false;
    //4个页面
    private int whichType = -1;
    //只加载一次轮播数据
    private boolean isFirstLoadShuffing = true;
    //搜索内容
    private String searchContent = null;

    @SuppressLint({"NewApi", "ValidFragment"})
    public HomeNewsFragment() {
    }

    @Override
    public void loadData() {
        //是否是底部加载
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

    //----------------------------------------工具类------------------------------------------------
    //初始新闻列表
    private void initRecycler() {
        //刷新的设置
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

    //初始轮播
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

    //新闻加载更多的监听
    private void newsloadMoreCallback() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) && !mIsRefreshing) {
                    // TODO: 考虑底部添加布局   可能此处存在bug-2次触发滑动到底部的监听-
                    mIsRefreshing = true;
                    currentPage++;
                    //当前页数大于所有的页数
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    getDate(currentPage, true);
                }
            }
        });
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

    //重新请求接口
    public void UpDateInterface(String content) {
        searchContent = content;
        currentPage = 1;
        isLoadAll = false;
        getDate(currentPage, false);
    }

    //获取4个界面的接口数据
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

    //-----------------------------------------接口-----------------------------------------
    //获取新闻数据
    private void getNewsDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetNewsBean> httpListener = new HttpListener<GetNewsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试新闻失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetNewsBean getNewsBean) {
                stopRefresh();
                if (getNewsBean.getCode() == 500) {
                    ToastUtils.show(getNewsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getNewsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有数据哦!");
                    if (homeNewsAdapter.getListSize() != 0) {
                        homeNewsAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("测试加载: ", "新闻加上加载的监听");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //FIXME 加载轮播
                if (needShuffing) {
                    if (isFirstLoadShuffing) {
                        isFirstLoadShuffing = false;
                        //轮播图
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
                //当前页的数据
                List<GetNewsBean.DataBean.ResponseBean.RowsBean> list = getNewsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (homeNewsAdapter == null) {
                    homeNewsAdapter = new HomeNewsAdapter(getContext(), list);
                    homeNewsAdapter.setNewsOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeNewsAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
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

    //获取号外数据
    private void getExtraIds(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetExtraIdsBean> httpListener = new HttpListener<GetExtraIdsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试号外失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetExtraIdsBean getExtraIdsBean) {
                stopRefresh();
                if (getExtraIdsBean.getCode() == 500) {
                    ToastUtils.show(getExtraIdsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getExtraIdsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
//                    ToastUtils.show("没有数据哦!");
                    if (homeExtralAdapter.getListSize() != 0) {
                        homeExtralAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("测试加载: ", "新闻加上加载的监听");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }

                //当前页的数据
                List<GetExtraIdsBean.DataBean.ResponseBean.RowsBean> list = getExtraIdsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (homeExtralAdapter == null) {
                    homeExtralAdapter = new HomeExtralAdapter(getContext(), list);
                    homeExtralAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeExtralAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
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

    //获取行情分析
    private void getAnalyseDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetQuotationsBean> httpListener = new HttpListener<GetQuotationsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试行情分析失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetQuotationsBean getQuotationsBean) {
                stopRefresh();
                ViseLog.e("测试行情分析成功:" + getQuotationsBean);
                if (getQuotationsBean.getCode() == 500) {
                    ToastUtils.show(getQuotationsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getQuotationsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有数据哦!");
                    if (homeAnlyseAdapter.getListSize() != 0) {
                        homeAnlyseAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("测试加载: ", "政治加上加载");
                    newsloadMoreCallback();
//                    rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //当前页的数据
                List<GetQuotationsBean.DataBean.ResponseBean.RowsBean> list = getQuotationsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (homeAnlyseAdapter == null) {
                    homeAnlyseAdapter = new HomeAnlyseAdapter(getContext(), list);
                    homeAnlyseAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeAnlyseAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
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

    //获取币虎专栏
    private void getBihuDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetColumnsBean> httpListener = new HttpListener<GetColumnsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试行情分析失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetColumnsBean getColumnsBean) {
                stopRefresh();
                if (getColumnsBean.getCode() == 500) {
                    ToastUtils.show(getColumnsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getColumnsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有数据哦!");
                    if (homeBihuAdapter.getListSize() != 0) {
                        homeBihuAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("测试加载: ", "新闻加上加载的监听");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //FIXME 加载轮播
                if (needShuffing) {
                    if (isFirstLoadShuffing) {
                        isFirstLoadShuffing = false;
                        //轮播图
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
                //当前页的数据
                List<GetColumnsBean.DataBean.ResponseBean.RowsBean> list = getColumnsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (homeBihuAdapter == null) {
                    homeBihuAdapter = new HomeBihuAdapter(getContext(), list);
                    homeBihuAdapter.setNewsOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homeBihuAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
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

    //获取政策数据
    private void getPolicyDate(int page, final boolean isBottom) {
        if (isLoadAll) {
            ToastUtils.show("已为您展示所有数据!");
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetPolicysBean> httpListener = new HttpListener<GetPolicysBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试新闻失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetPolicysBean getPolicysBean) {
                stopRefresh();
                if (getPolicysBean.getCode() == 500) {
                    ToastUtils.show(getPolicysBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getPolicysBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有数据哦!");
                    if (homePolicysAdapter.getListSize() != 0) {
                        homePolicysAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 10 == 0) ? allDataNum / 10 : (allDataNum - allDataNum % 10) / 10 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    Log.e("测试加载: ", "政治加上加载");
                    newsloadMoreCallback();
//                        rvHomenewfra.addOnScrollListener(OnLoadMoreListener());
                }
                //当前页的数据
                List<GetPolicysBean.DataBean.ResponseBean.RowsBean> list = getPolicysBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (homePolicysAdapter == null) {
                    homePolicysAdapter = new HomePolicysAdapter(getContext(), list);
                    homePolicysAdapter.setOnItenClickCallBack(HomeNewsFragment.this);
                    rvHomenewfra.setAdapter(homePolicysAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
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

    //点击数量的增加
    private void addNum(String id) {
        HttpListener<AddNumBean> httpListener = new HttpListener<AddNumBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试增加数量失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(AddNumBean addNumBean) {
                ViseLog.e("测试增加数量成功:" + addNumBean);
                if (addNumBean.getCode() == 500) {
                    ToastUtils.show(addNumBean.getMessages().get(0).getMessage());
                    return;
                }
            }
        };
        ContentLoader contentLoader = new ContentLoader(ContentLoader.BASEURL);
        contentLoader.addNum(Custom.APPID, TypeTransform.strToLong(id), httpListener);
    }

    //----------------------------------------------回调--------------------------------------------
    /*新闻详情*/
    @Override
    public void clickNewsItem(String content, String webUrl, String id, int po, String title) {
        Log.e("clickNewsItem: ", "webUrl=" + webUrl);
        addNum(id);
        homeNewsAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, content, webUrl, title);
    }

    /*分析详情*/
    @Override
    public void clickAnalyse(String webTitle, String webUrl, String id, int position, String content) {
        addNum(id);
        homeAnlyseAdapter.lookNumUpOne(position);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, content);
    }

    /*币虎详情*/
    @Override
    public void clickBihuItem(String webTitle, String webUrl, String id, int po, String content) {
        addNum(id);
        homeBihuAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, content);
    }

    /*政策详情*/
    @Override
    public void clickPolicy(String webTitle, String webUrl, String id, int po, String title) {
        addNum(id);
        homePolicysAdapter.lookNumUpOne(po);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, title);
    }

    /*号外点击*/
    @Override
    public void clickExtral(String webTitle, String webUrl, String id, int position, String title) {
        addNum(id);
        homeExtralAdapter.lookNumUpOne(position);
        ChangeActUtils.changeActivity_2_webShareAct(getContext(), WebShareActivity.class, webTitle, webUrl, title);
    }

    /*点击轮播*/
    @Override
    public void clickImg(String webUrl) {
        ChangeActUtils.changeActivity_2_webAct(getContext(), WebActivity.class, "图片详情", webUrl);
    }
}
