package com.aganyun.acode.view.signin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.aganyun.acode.R;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.ObjIsNull;

/**
 * Created by 孟晨 on 2018/8/22.
 */

public class CustomViews extends View {

    private Context mContext;
    private RectF oval;
    //圆环色-填充色-字体色
    private Paint mPaintStroke, mPaintSolid, mPaintContent;
    //半径-直径
    private float radius;
    private float radius2;
    //画字的内容
    private String textContent = "";

    public CustomViews(Context context) {
        super(context, null);
        init();
    }

    public CustomViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.signInCircle);
        if (attrArray != null) {
            textContent = attrArray.getString(R.styleable.signInCircle_text);
            attrArray.recycle();
        }
    }

    public CustomViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
     */
    private void init() {
        radius = DensityUtil.dip2px(mContext, 15);
        radius2 = DensityUtil.dip2px(mContext, 30);
        //框画笔
        mPaintStroke = new Paint();
        mPaintStroke.setColor(mContext.getColor(R.color.signin_text_and_strock_no_singned));
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setAntiAlias(true);
        //填充画笔
        mPaintSolid = new Paint();
        mPaintSolid.setStyle(Paint.Style.FILL);
        mPaintSolid.setColor(mContext.getColor(R.color.signin_solid_no_singned));
        mPaintSolid.setAntiAlias(true);
        //文字画笔
        mPaintContent = new Paint();
        mPaintContent.setAntiAlias(true);
        mPaintContent.setColor(mContext.getColor(R.color.signin_text_and_strock_no_singned));
        mPaintContent.setTextSize(DensityUtil.dip2px(mContext, 12));
        mPaintContent.setTextAlign(Paint.Align.CENTER);
        //矩形
        oval = new RectF();
        oval.set(0, 0, radius2, radius2);
    }

    //设置绘画的问题
    public void drawText(String drawText){
        textContent = drawText;
        postInvalidate();
    }

    public void setViewColor(int colorStroke, int colorSolid, int colorContent) {
        mPaintStroke.setColor(colorStroke);
        mPaintSolid.setColor(colorSolid);
        mPaintContent.setColor(colorContent);
        postInvalidate();
    }

    //绘图的方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(radius, radius, radius, mPaintSolid);
        //根据进度画圆弧（圆弧的父容器；圆弧的起始角度；圆弧的角度；半径连线；画笔）
        canvas.drawArc(oval, 0, 360, true, mPaintStroke);
        //居中文字
        if (ObjIsNull.isEmpty(textContent)){
            return;
        }
        canvas.drawText(textContent, oval.centerX(),
                radius + 1.5f * mPaintContent.getFontMetrics().bottom, mPaintContent);
    }
}
