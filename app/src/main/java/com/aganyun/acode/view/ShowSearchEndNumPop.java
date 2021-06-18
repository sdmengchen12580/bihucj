package com.aganyun.acode.view;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.ui.act.search.SearchActivity;
import com.aganyun.acode.utils.DensityUtil;

import java.lang.ref.WeakReference;

/**
 * Created by 孟晨 on 2018/7/19.
 */

public class ShowSearchEndNumPop {

    public ShowSearchEndNumPop(SearchActivity mactivity, LinearLayout viewAchor) {
        weakReference = new WeakReference<SearchActivity>(mactivity);
        this.viewAchor = viewAchor;
    }

    private LinearLayout viewAchor;
    private View popView;
    private PopupWindow popWindow;
    private WeakReference<SearchActivity> weakReference;

    public PopupWindow showPop(String num) {
        SearchActivity activity = weakReference.get();
        if (activity != null) {
            //布局和点击事件
            popView = LayoutInflater.from(activity)
                    .inflate(R.layout.pop_search_end_num_layout, null, false);

            //数量
            TextView tv_num = popView.findViewById(R.id.tv_num);
            tv_num.setText(num);

            popWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(activity,34),
                    true);
            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //点击外部，popupwindow会消失
            popWindow.setFocusable(false);
            popWindow.setOutsideTouchable(false);
            popWindow.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
//            popWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            //显示在其下方
            popWindow.showAsDropDown(viewAchor);
        }
        return popWindow;
    }

    public void dismissPop(){
        if(popWindow!=null){
            if(popWindow.isShowing()){
                popWindow.dismiss();
                popWindow = null;
            }
        }
    }
}
