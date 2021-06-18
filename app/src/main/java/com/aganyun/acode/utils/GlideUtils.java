package com.aganyun.acode.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by 孟晨 on 2018/4/8.
 */

public class GlideUtils {

    public static void glideLoadImg(Context context, String imgurl, ImageView img){
        Glide.with(context.getApplicationContext())
                .load(imgurl)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .fitCenter()
                .into(img);
    }

    public static void glideLoadImg_DefaultImg(Context context, String imgurl, ImageView img,int defaultImg){
        Glide.with(context)
                .load(imgurl)
                .error(defaultImg)
                .fitCenter()
                .into(img);
    }
}
