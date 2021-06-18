package com.aganyun.acode.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.aganyun.acode.R;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ViewUtils;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/1.
 */

public class NativeImgPagerAdapter extends PagerAdapter {

    private int imgUrls[];
    private Context context;
    private int windowHeight;

    public NativeImgPagerAdapter(int imgUrls[],
                                 Context context,
                                 int windowHeight,
                                 ClickImgCallback clickImgCallback) {
        this.imgUrls = imgUrls;
        this.context = context;
        this.windowHeight = windowHeight;
        this.clickImgCallback = clickImgCallback;
    }

    @Override
    public int getCount() {
        return imgUrls.length;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.singleitem_leadact_img, null);
        ((ImageView) view.findViewById(R.id.id_item_img)).setImageResource(imgUrls[position]);
        ((ImageView) view.findViewById(R.id.id_item_img)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickImgCallback != null) {
//                    clickImgCallback.clickImg();
                }
            }
        });

        if (position == (imgUrls.length - 1)) {
            Button button = (Button) view.findViewById(R.id.bt_now_use);
            button.setVisibility(android.view.View.VISIBLE);
            ViewUtils.setMargins(button, 0, (int) (windowHeight * 0.24), 0, 0);
            //通用做法
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickImgCallback != null) {
                        clickImgCallback.clickNowUse();
                    }
                }
            });
        }
        container.addView(view);
        return view;
    }

    //---------------------------------------接口--------------------------------------------
    public interface ClickImgCallback {
        void clickImg(String webUrl);

        void clickNowUse();
    }

    private ClickImgCallback clickImgCallback;

    public void setCallBack(ClickImgCallback clickImgCallback) {
        this.clickImgCallback = clickImgCallback;
    }


}

