package com.aganyun.acode.ui.fra.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidkun.xtablayout.XTabLayout;
import com.aganyun.acode.R;
import com.aganyun.acode.adapter.MyAdapter;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.callback.HomeInnerTabListener;
import com.aganyun.acode.ui.base.BaseFragment;
import com.aganyun.acode.utils.ObjIsNull;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class HomeFragment extends BaseFragment {

    private XTabLayout tablayout;
    private ViewPager homefraVp;
    private EditText etSearch;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private MyAdapter adapter;
    //搜索标志
    private int type = 0;
    private String content;

    @SuppressLint({"NewApi", "ValidFragment"})
    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        onViewClicked(view);
        titles.add("资讯");
        titles.add("号外");
        titles.add("行情");
        titles.add("专栏");
        titles.add("政策");
        tablayout.addTab(tablayout.newTab().setText("资讯"));
        tablayout.addTab(tablayout.newTab().setText("号外"));
        tablayout.addTab(tablayout.newTab().setText("行情"));
        tablayout.addTab(tablayout.newTab().setText("专栏"));
        tablayout.addTab(tablayout.newTab().setText("政策"));
        fragmentList.add(HomeNewsFragment.newInstance(true, 0));
        fragmentList.add(HomeNewsFragment.newInstance(false, 1));
        fragmentList.add(HomeNewsFragment.newInstance(false, 2));
        fragmentList.add(HomeNewsFragment.newInstance(true, 3));
        fragmentList.add(HomeNewsFragment.newInstance(false, 4));
        adapter = new MyAdapter(getChildFragmentManager(), fragmentList, titles);
        homefraVp.setAdapter(adapter);
        //关联-缓存
        tablayout.setxTabDisplayNum(fragmentList.size());
        tablayout.setupWithViewPager(homefraVp);
        homefraVp.setOffscreenPageLimit(fragmentList.size());
        homefraVp.setCurrentItem(0);
        tablayout.setOnTabSelectedListener(new HomeInnerTabListener() {
            @Override
            public void mOnTabSelected(int position) {
                clearSearchContent();
                type = position;
                homefraVp.setCurrentItem(position);
            }
        });
        etSearch.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                content = etSearch.getText().toString().trim();
            }
        });
    }

    public void onViewClicked(View view) {
        tablayout = view.findViewById(R.id.tablayout);
        homefraVp = view.findViewById(R.id.homefra_vp);
        etSearch = view.findViewById(R.id.et_search);
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                content = etSearch.getText().toString().trim();
                Log.e("测试搜索: ", "搜索:" + type);
                ((HomeNewsFragment) fragmentList.get(type)).UpDateInterface(content);
            }
        });
    }

    //清空标题文字
    public void clearSearchContent() {
        String content = etSearch.getText().toString().trim();
        if (!ObjIsNull.isEmpty(content)) {
            etSearch.setText("");
        }
    }
}
