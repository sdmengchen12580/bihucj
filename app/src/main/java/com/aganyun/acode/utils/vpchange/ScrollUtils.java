package com.aganyun.acode.utils.vpchange;

import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;

import java.lang.reflect.Field;

/**
 * @author xuanyouwu
 * @email xuanyouwu@163.com
 * @time 2016-01-12 16:54
 */
public class ScrollUtils {

    /**
     * 例子  如匀加速
     *
     * @param viewPager
     * @param speed
     */
    public static void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            // AccelerateInterpolator 是匀加速插值器
            MyScroller viewPagerScroller = new MyScroller(viewPager.getContext(), new AccelerateInterpolator());
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setmDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
