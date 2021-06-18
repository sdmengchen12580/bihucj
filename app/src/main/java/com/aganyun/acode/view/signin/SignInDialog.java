package com.aganyun.acode.view.signin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetSignInBaseBean.DataBean.ResponseBean;
import com.aganyun.acode.http.suger.bean.GetSignedInNumBean;
import com.aganyun.acode.http.suger.bean.QiandaoBean;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.util.List;

import rx.Subscription;

/**
 * Created by 孟晨 on 2018/8/22.
 */

public class SignInDialog extends Dialog {


    public SignInDialog(@NonNull Context context) {
        super(context);
    }

    public SignInDialog(@NonNull Context context,
                        @StyleRes int themeResId,
                        List<ResponseBean> list,
                        String key,
                        boolean isSignIned) {
        super(context, themeResId);
        this.isSignIned = isSignIned;
        this.list = list;
        this.key = key;
        //签到糖果
        for (int i = 0; i < list.size(); i++) {
            suger[i] = list.get(i).getPearlNum();
        }
    }

    protected SignInDialog(@NonNull Context context,
                           boolean cancelable,
                           @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    //是否签到过
    private boolean isSignIned;
    private String key;
    //签到糖果积分
    private List<ResponseBean> list;
    //是否可以点击签到按钮
    private boolean buttonCanSignIn = false;
    //今日是否可以签到
    private boolean todaySignIn = false;

    //签到天数
    private CustomViews[] customViewses = new CustomViews[7];
    private int[] customViewIds = {R.id.circle1,
            R.id.circle2, R.id.circle3, R.id.circle4,
            R.id.circle5, R.id.circle6, R.id.circle7};
    //签到的中间线
    private TextView[] textViews = new TextView[6];
    private int[] textViewsIds = {R.id.tv_line1, R.id.tv_line2, R.id.tv_line3,
            R.id.tv_line4, R.id.tv_line5, R.id.tv_line6};
    //签到送的糖果
    private String[] suger = new String[7];
    //当前的签到次数
    private int leftSignNum = -1;
    //context
    private Context mContext;
    protected long lastClickTime;//按钮短时多触

    protected boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //背景透明+父布局的shape的圆角和slide的背景色-->背景色+圆角
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //外部不可被点击
//        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_signin_layout);
        mContext = getContext();
        initView();
    }

    private void initView() {
        for (int i = 0; i < customViewses.length; i++) {
            customViewses[i] = findViewById(customViewIds[i]);
            customViewses[i].drawText("+" + suger[i]);
        }
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = findViewById(textViewsIds[i]);
        }
        //小于7才可以签到
        findViewById(R.id.bt_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFastDoubleClick()){
                    return;
                }
                //优先级
                //1.判断登录 ->没有就登录->直接签到
                //2.判断签到信息是否获取完
                //3.判断今日是否可以签到 ->走不到这里，不可签到是不会弹窗的
                if (!isSignIned) {
                    if (dialogClickCallback != null) {
                        dialogClickCallback.changeToSignIn(SignInDialog.this);
                        return;
                    }
                }
                if (!buttonCanSignIn) {
                    ToastUtils.show("正在获取签到信息,请稍等!");
                    return;
                }
                if (todaySignIn) {
                    ToastUtils.show("抱歉,今日不可再签到!");
                    //FIXME 签到过直接退出
                    dismiss();
                    return;
                }
                qiandao();
            }
        });
        //已经登录，获取签到次数
        if (isSignIned) {
            //之前签到次数的获取
            getSignedInNum();
        }
    }

    //签到
    private void leftSignUp(int mCurrentSignNum) {
        //数字为0-7之间才去执行绘图
        if (mCurrentSignNum >= 0 && mCurrentSignNum <= 7) {
            for (int i = 0; i < mCurrentSignNum; i++) {
                //前面园变色+后面线变色
                setDaySignUped(i);
                //i最高等于6，即第7天
                if (i == mCurrentSignNum - 1) {
                    setDayWaitSignUped(i);
                }
            }
        }
    }

    //改变为签到样式
    private void setDaySignUped(int mCurrentSignNum) {
        //圆变橙色
        customViewses[mCurrentSignNum].setViewColor(
                mContext.getResources().getColor(R.color.indicator_color),
                mContext.getResources().getColor(R.color.indicator_color),
                mContext.getResources().getColor(R.color.white));
        //圆后面的线变成橙色-做第7天的判断
        if (mCurrentSignNum < 6) {
            textViews[mCurrentSignNum].setBackgroundColor(
                    mContext.getResources().getColor(R.color.indicator_color));
        }
    }

    //改变为待签到样式
    private void setDayWaitSignUped(int mCurrentSignNum) {
        if (mCurrentSignNum < 6) {
            //第7个之前将下一个天数颜色修改为待改变
            customViewses[mCurrentSignNum + 1].setViewColor(
                    mContext.getResources().getColor(R.color.indicator_color),
                    mContext.getResources().getColor(R.color.white),
                    mContext.getResources().getColor(R.color.indicator_color));
        }
    }


    //------------------------------------数量修改的监听---------------------------------------
    public DialogClickCallback dialogClickCallback;

    public void setDialogClickCallback(DialogClickCallback dialogClickCallback) {
        this.dialogClickCallback = dialogClickCallback;
    }

    public interface DialogClickCallback {
        //关闭弹窗-送的糖果数量
        void signIn(Dialog dialog, boolean signSuccess, String sugerNum);

        //没签到，跳转到签到页面
        void changeToSignIn(Dialog dialog);
    }

    //-----------------------------------------接口--------------------------------------------------
    private Subscription getSignedInNum;
    private Subscription qiandao;

    //获取签到天数
    private void getSignedInNum() {
        HttpListener<GetSignedInNumBean> httpListener = new HttpListener<GetSignedInNumBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("获取签到天数失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetSignedInNumBean getSignedInNumBean) {
                ViseLog.e("获取签到天数成功:" + getSignedInNumBean);
                if (getSignedInNumBean.getCode() == 500) {
                    ToastUtils.show(getSignedInNumBean.getMessages().get(0).getMessage());
                    return;
                }
                buttonCanSignIn = true;
                //签到次数获取
                leftSignNum = getSignedInNumBean.getData().getResponse().getSginNum();
                //今日是否已经签到
                todaySignIn = getSignedInNumBean.getData().getResponse().isTodaySignIn();
                //签到天数的显示
                leftSignUp(leftSignNum);
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        getSignedInNum = sugerLoader.getSignedInNum(Custom.APPID, key, httpListener);
    }

    //签到
    private void qiandao() {
        HttpListener<QiandaoBean> httpListener = new HttpListener<QiandaoBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试签到失败:" + errorMsg);
                signInEnd(false, "");
            }

            @Override
            public void onSuccess(QiandaoBean qiandaoBean) {
                ViseLog.e("测试签到成功:" + qiandaoBean);
                if (qiandaoBean.getCode() == 500) {
                    ToastUtils.show(qiandaoBean.getMessages().get(0).getMessage());
                    signInEnd(false, "");
                    return;
                }
                //定量被定义才回调
                if (leftSignNum != -1) {
                    //已签到天数小于7天
                    if (leftSignNum < 7) {
                        signInEnd(true, suger[leftSignNum]);
                    }
                    //已签到7天，属于签到失败
                    signInEnd(false, "");
                }
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        qiandao = sugerLoader.qiandao(Custom.APPID, key, httpListener);
    }

    private void signInEnd(boolean signSuccess, String value) {
        if (dialogClickCallback != null) {
            dialogClickCallback.signIn(SignInDialog.this, signSuccess, value + "");
        }
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //签到天数取消订阅
        OtherUtils.cancelSub(getSignedInNum);
        //签到天数取消订阅
        OtherUtils.cancelSub(qiandao);
    }
}
