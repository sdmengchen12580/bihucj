package com.aganyun.acode.utils.vpchange;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author xuanyouwu
 * @email xuanyouwu@163.com
 * @time 2016-01-12 15:17
 */
public class MyScroller extends Scroller {

    private long mDuration;

    public MyScroller(Context context) {
        super(context);
    }

    public MyScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public MyScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public long getmDuration() {
        return mDuration;
    }

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        //super.startScroll(startX, startY, dx, dy);
        super.startScroll(startX, startY, dx, dy, this.getDuration());
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // super.startScroll(startX, startY, dx, dy, duration);
        super.startScroll(startX, startY, dx, dy, this.getDuration());
    }
}
