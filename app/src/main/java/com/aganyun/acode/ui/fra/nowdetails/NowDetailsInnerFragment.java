package com.aganyun.acode.ui.fra.nowdetails;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.aganyun.acode.R;
import com.aganyun.acode.adapter.DetailsInnerAdapter;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.details.DetailsLoader;
import com.aganyun.acode.http.details.bean.GetTickerDetailsBean;
import com.aganyun.acode.ui.base.BaseLazyLoadFragment;
import com.aganyun.acode.utils.RecyclerViewUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.vise.log.ViseLog;
import org.lcsyjt.mylibrary.http.callback.HttpListener;
import java.util.List;

public class NowDetailsInnerFragment extends BaseLazyLoadFragment {

    private RecyclerView rvDetailsInner;
    private SwipeRefreshLayout refreshLayout;
    private String title;
    private DetailsInnerAdapter detailsInnerAdapter;
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

    public NowDetailsInnerFragment() {
    }

    public static NowDetailsInnerFragment newInstance(String title) {
        NowDetailsInnerFragment fragment = new NowDetailsInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_now_details_inner;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        rvDetailsInner = view.findViewById(R.id.rv_details_inner);
        refreshLayout = view.findViewById(R.id.refresh);
        //刷新的设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvDetailsInner.setLayoutManager(linearLayoutManager);
        rvDetailsInner.setNestedScrollingEnabled(false);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadAll = false;
                if (mIsRefreshing) {
                    return;
                }
                mIsRefreshing = true;
                currentPage = 1;
                getTickerDetails(currentPage, false);
            }
        });
    }

    @Override
    public void loadData() {
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        getTickerDetails(currentPage, false);
    }

    //政策加载更多监听
    private RecyclerView.OnScrollListener OnLoadMoreListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = RecyclerViewUtils.isVisBottom(recyclerView);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isBottom && !mIsRefreshing) {
                    mIsRefreshing = true;
                    currentPage++;
                    //当前页数大于所有的页数
                    if (currentPage > allPage) {
                        isLoadAll = true;
                    }
                    getTickerDetails(currentPage, true);
                }
            }
        };
    }

    //停止刷新
    private void stopRefresh() {
        mIsRefreshing = false;
        if (refreshLayout != null) {
            //关闭掉刷新的ui
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        }
    }

    //----------------------------------------接口------------------------------------------------
    private void getTickerDetails(int page, final boolean isBottom) {
        if (isLoadAll) {
            mIsRefreshing = false;
            return;
        }
        HttpListener<GetTickerDetailsBean> httpListener = new HttpListener<GetTickerDetailsBean>() {
            @Override
            public void onError(String errorMsg) {
                stopRefresh();
                ViseLog.e("测试行情详情失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetTickerDetailsBean getTickerDetailsBean) {
                stopRefresh();
                ViseLog.e("测试行情详情成功：" + getTickerDetailsBean.toString());
                if (getTickerDetailsBean.getCode() == 500) {
                    ToastUtils.show(getTickerDetailsBean.getMessages().get(0).getMessage());
                    return;
                }
                //拿到数据总数.没数据return
                allDataNum = getTickerDetailsBean.getData().getResponse().getCount();
                if (!(allDataNum > 0)) {
                    ToastUtils.show("没有数据哦!");
                    if (detailsInnerAdapter.getListSize() != 0) {
                        detailsInnerAdapter.clearData();
                    }
                    return;
                }
                //获取总的页数
                allPage = (allDataNum % 15 == 0) ? allDataNum / 15 : (allDataNum - allDataNum % 15) / 15 + 1;
                // FIXME 只有一页加载完毕,大于1页时候，才添加上上拉加载动作
                if (allPage == 1) {
                    isLoadAll = true;
                } else {
                    rvDetailsInner.addOnScrollListener(OnLoadMoreListener());
                }
                //当前页的数据
                List<GetTickerDetailsBean.DataBean.ResponseBean.RowsBean> list = getTickerDetailsBean.getData().getResponse().getRows();
                //第一次直接加载当前页的数据，不去管是刷新还是加载的数据
                if (detailsInnerAdapter == null) {
                    detailsInnerAdapter = new DetailsInnerAdapter(getContext(), list);
                    rvDetailsInner.setAdapter(detailsInnerAdapter);
                    return;
                }
                //刷新和加载的数据添加方式
                if (isBottom) {
                    detailsInnerAdapter.addBottomData(list);
                } else {
                    detailsInnerAdapter.refreshData(list);
                }
            }
        };
        DetailsLoader detailsLoader = new DetailsLoader(DetailsLoader.BASEURL);
        detailsLoader.getTickerDetails(Custom.APPID, title, page, 15, httpListener);
    }

}
