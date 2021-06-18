package com.aganyun.acode.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.aganyun.acode.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 孟晨 on 2018/8/1.
 */

public class ViewUtils {

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static void invisibleView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void GoneView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    public static void showView(View view) {
        if (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static Bitmap gitBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static Bitmap drawTextOnBitmap(Activity activity,
                                          String shareContent,
                                          int shareContentTextSize,
                                          int shareContentColor,
                                          String shareTitle,
                                          int shareTitleTextSize,
                                          int shareTitleColor) {
        return drawTextToCenter(activity,
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.img_suggest_friend_zxing_bg),
                shareContent,
                shareContentTextSize,
                shareContentColor,
                shareTitle,
                shareTitleTextSize,
                shareTitleColor);
    }

    /**
     * 绘制文字到中部局下
     * StaticLayout
     * // 0.StaticLayout位置及属性设置-textview也是用的它
     * // 1.CharSequence source 需要分行的字符串
     * // 2.int bufstart 需要分行的字符串从第几的位置开始
     * // 3.int bufend 需要分行的字符串到哪里结束
     * // 4.TextPaint paint 画笔对象
     * // 5.int outerwidth :layout的宽度，字符串超出宽度时自动换行
     * // 6.Alignment align :layout的对其方式，有ALIGN_CENTER， ALIGN_NORMAL， ALIGN_OPPOSITE 三种
     * // 7.float spacingmult 相对行间距，相对字体大小，1.5f表示行间距为1.5倍的字体高度
     * // 8.float spacingadd  在基础行距上添加多少--实际行间距等于这两者的和。
     * // 9.float  boolean includepad  参数未知
     * // 10.TextUtils.TruncateAt ellipsize:   从什么位置开始省略
     * // 11.int ellipsizedWidth  超过多少开始省略--
     * // 需要指出的是这个layout是默认画在Canvas的(0,0)点的，如果需要调整位置只能在draw之前移Canvas的起始坐标
     */
    public static Bitmap drawTextToCenter(Activity context,
                                          Bitmap bitmap,
                                          String shareContent,
                                          int shareContentTextSize,
                                          int shareContentColor,
                                          String shareTitle,
                                          int shareTitleTextSize,
                                          int shareTitleColor) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_4444;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        //左右各边距10dp
        Canvas canvas = new Canvas(bitmap);
        //文字属性
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
//        textPaint.setColor(color);
//        textPaint.setTextSize(dip2px(context, size));
//        //文字居中
////        textPaint.setTextAlign(Paint.Align.CENTER);
//        String content = text;
//        if (!ObjIsNull.ObjIsNull(text)) {
//            if (text.length() > 180) {
//                content = text.substring(0, 180);
//                content += "...";
//            }
//        }
//        StaticLayout sl = new StaticLayout(content,
//                0,
//                content.length(),
//                textPaint,
//                canvas.getWidth() - dip2px(context, 140),
//                Layout.Alignment.ALIGN_NORMAL,
//                1.5f,
//                1f,
//                false,
//                TextUtils.TruncateAt.END,
//                canvas.getWidth() - dip2px(context, 50));
//        canvas.translate(dip2px(context, 70), (float) (bitmap.getHeight() * 0.55));
//        sl.draw(canvas);
        return bitmap;
    }

    public static int dip2px(final Context context, final float n) {
        return (int) (n * context.getResources().getDisplayMetrics().density + 0.5f);
    }



    //--------------------------------------截取scrollview------------------------------------------
    /**
     * 截取scrollview的屏幕
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
//            scrollView.getChildAt(i).setBackgroundColor(
//                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 1500) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


}
