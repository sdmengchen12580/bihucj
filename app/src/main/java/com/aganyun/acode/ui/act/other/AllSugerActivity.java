package com.aganyun.acode.ui.act.other;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aganyun.acode.R;
import com.aganyun.acode.adapter.GetPearlsAdapter;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetPearlsBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.RecyclerViewUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.util.List;

import rx.Subscription;

public class AllSugerActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private SwipeRefreshLayout refresh;
    private GetPearlsAdapter getPearlsAdapter;
    private Subscription getPearls;
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

    @Override
    protected int getViewId() {
        return R.layout.activity_all_suger;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        //刷新的设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllSugerActivity.this);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(AllSugerActivity.this,
                linearLayoutManager.getOrientation()));
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
                getPearls(currentPage, false);
            }
        });
        //是否是底部加载
        getPearls(currentPage, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OtherUtils.cancelSub(getPearls);
    }

    //-----------------------------------------工具类---------------------------------------------
    public void onViewClicked() {
        recyclerview = findViewById(R.id.recyclerview);
        refresh = findViewById(R.id.refresh);
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

    private RecyclerView.OnScrollListener OnLoadMoreListener()  //政策加载更多监听
    {
        Log.e("测试加载: ", "加上了加载的监听");
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = RecyclerViewUtils.isVisBottom(recyclerView);
                Log.e("测试加载: ", "判断是否到达底部:" + isBottom);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom && !mIsRefreshing) {
                    Log.e("测试加载: ", "条件满足开始加载");
                    // TODO: 考虑底部添加布局   可能此处存在bug-2次触发滑动到底部的监听-
                    mIsRefreshing = true;
                    currentPage++;
                    //当前页数大于所有的页数
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    getPearls(currentPage, true);
                }
            }
        };
    }

    private void stopRefresh()  //停止刷新
    {
        mIsRefreshing = false;
        if (refresh != null) {
            //关闭掉刷新的ui
            if (refresh.isRefreshing()) {
                refresh.setRefreshing(false);
            }
        }
    }

    //-----------------------------------------接口---------------------------------------------
    private void getPearls(int page, final boolean isBottom) //今日获取糖果
    {
        if (isLoadAll) {
            mIsRefreshing = false;
            return;
        }
        String key = (String) SPUtil.get(AllSugerActivity.this, "key", "");
        HttpListener<GetPearlsBean> httpListener = new HttpListener<GetPearlsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试今日获取糖果失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetPearlsBean getPearlsBean) {
                stopRefresh();
                ViseLog.e("测试今日获取糖果成功:" + getPearlsBean);
                if (getPearlsBean.getCode() == 500) {
                    ToastUtils.show(getPearlsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getPearlsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有糖果记录哦!");
                    if (getPearlsAdapter.getListSize() != 0) {
                        getPearlsAdapter.clearData();
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
                    recyclerview.addOnScrollListener(OnLoadMoreListener());
                }
                //当前页的数据
                List<GetPearlsBean.DataBean.ResponseBean.RowsBean> list = getPearlsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (getPearlsAdapter == null) {
                    getPearlsAdapter = new GetPearlsAdapter(AllSugerActivity.this, list);
                    recyclerview.setAdapter(getPearlsAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
                if (isBottom) {
                    getPearlsAdapter.addBottomData(list);
                } else {
                    getPearlsAdapter.refreshData(list);
                }
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        getPearls = sugerLoader.getPearls(Custom.APPID, 0, page, 10, key, httpListener);
    }
}
