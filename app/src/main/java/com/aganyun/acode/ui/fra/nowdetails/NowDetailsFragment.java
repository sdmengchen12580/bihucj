package com.aganyun.acode.ui.fra.nowdetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.androidkun.xtablayout.XTabLayout;
import com.aganyun.acode.R;
import com.aganyun.acode.adapter.MyAdapter;
import com.aganyun.acode.callback.HomeInnerTabListener;
import com.aganyun.acode.http.details.DetailsLoader;
import com.aganyun.acode.http.details.bean.InitBean;
import com.aganyun.acode.ui.base.BaseLazyLoadFragment;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.vise.log.ViseLog;
import org.lcsyjt.mylibrary.http.callback.HttpListener;
import java.util.ArrayList;
import java.util.List;

public class NowDetailsFragment extends BaseLazyLoadFragment {

    private XTabLayout tabTitle;
    private ViewPager vpNowDetails;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private MyAdapter adapter;

    public NowDetailsFragment() {
    }

    public static NowDetailsFragment newInstance() {
        NowDetailsFragment fragment = new NowDetailsFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_now_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        tabTitle = view.findViewById(R.id.tab_title);
        vpNowDetails = view.findViewById(R.id.vp_now_details);
    }

    @Override
    public void loadData() {
        init();
    }

    //初始化内部的fragment
    private void initFragment(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            titles.add(list.get(i));
            tabTitle.addTab(tabTitle.newTab().setText(list.get(i)));
            fragmentList.add(NowDetailsInnerFragment.newInstance(list.get(i)));
        }
        adapter = new MyAdapter(getChildFragmentManager(), fragmentList, titles);
        vpNowDetails.setAdapter(adapter);
        //关联-缓存
        tabTitle.setxTabDisplayNum(fragmentList.size());
        tabTitle.setupWithViewPager(vpNowDetails);
        vpNowDetails.setOffscreenPageLimit(fragmentList.size());
        vpNowDetails.setCurrentItem(0);
        tabTitle.setOnTabSelectedListener(new HomeInnerTabListener() {
            @Override
            public void mOnTabSelected(int position) {
                vpNowDetails.setCurrentItem(position);
            }
        });
    }

    //-----------------------------------------接口-------------------------------------------------
    private void init() {
        HttpListener<InitBean> httpListener = new HttpListener<InitBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试行情初始化失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(InitBean initBean) {
                ViseLog.e("测试行情初始化成功:" + initBean.toString());
                if (initBean.getCode() == 500) {
                    ToastUtils.show(initBean.getMessages().get(0).getMessage());
                    return;
                }
                initFragment(initBean.getData().getResponse());
            }
        };
        DetailsLoader detailsLoader = new DetailsLoader(DetailsLoader.BASEURL);
        detailsLoader.init(httpListener);
    }

}
