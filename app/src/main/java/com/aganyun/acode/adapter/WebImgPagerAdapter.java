package com.aganyun.acode.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aganyun.acode.entity.ShuffingBean;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.ObjIsNull;

import java.util.List;

/**
 * Created by 孟晨 on 2018/6/12.
 */

public class WebImgPagerAdapter extends PagerAdapter {


    private List<ShuffingBean> list;
    private Context context;

    public WebImgPagerAdapter(List<ShuffingBean> list, Context context, ClickImgCallback clickImgCallback) {
        this.list = list;
        this.context = context;
        this.clickImgCallback = clickImgCallback;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView img = new ImageView(context);
        img.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickImgCallback != null) {
                    if (!ObjIsNull.isEmpty(list.get(position).getLinkUrl())) {
                        clickImgCallback.clickImg(list.get(position).getLinkUrl());
                    }
                }
            }
        });
        GlideUtils.glideLoadImg(context, list.get(position).getImgURl(), img);
        container.addView(img);
        return img;
    }

    //---------------------------------------接口--------------------------------------------
    public interface ClickImgCallback {
        void clickImg(String webUrl);
    }

    private ClickImgCallback clickImgCallback;

    public void setCallBack(ClickImgCallback clickImgCallback) {
        this.clickImgCallback = clickImgCallback;
    }


}
