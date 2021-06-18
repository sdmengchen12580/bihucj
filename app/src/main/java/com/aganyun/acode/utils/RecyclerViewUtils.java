package com.aganyun.acode.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by 孟晨 on 2018/5/28.
 */

public class RecyclerViewUtils {

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            Log.e("测试刷新: ", "刷新");
            return true;
        } else {
            Log.e("测试刷新: ", "不刷新");
            return false;
        }
    }
}
