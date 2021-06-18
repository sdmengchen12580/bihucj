package com.aganyun.acode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.aganyun.acode.R;


public class SmallCircleView extends View {

    //画笔
    private Paint mPaint;
    //父容器的矩形
    private RectF oval;
    private int paintColor;
    //用哪种颜色的画笔
    private int type = 0;

    public SmallCircleView(Context context) {
        super(context);
        init();
    }

    public SmallCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySmallCircleColor);
        if (typedArray != null) {
            paintColor = typedArray.getColor(R.styleable.MySmallCircleColor_mSolidColor, Color.WHITE);
            typedArray.recycle();
        }
    }

    public SmallCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔，矩形等
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval = new RectF();
    }


    //测量大小的方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    //设置画笔的颜色
    public void setPaintColor(int type) {
        this.type = type;
        postInvalidate();
    }


    //绘图的方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (type) {
            //初始
            case 0:
                mPaint.setColor(paintColor);
                break;
            //选中
            case 1:
                mPaint.setColor(getResources().getColor(R.color.leadact_img_now_user_bg));
                break;
            //白色
            case 2:
                mPaint.setColor(getResources().getColor(R.color.white));
                break;
        }
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //获取高
        int with = getWidth();
        //半径
        float radius = with / 2;
        //宽；高；半径；画笔
        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
    }

}
