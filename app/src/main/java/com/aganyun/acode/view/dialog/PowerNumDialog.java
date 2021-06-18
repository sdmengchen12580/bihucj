package com.aganyun.acode.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.person.PersonLoader;
import com.aganyun.acode.http.person.bean.CheckImageCodeBean;
import com.aganyun.acode.http.person.bean.SendImageCodeBean;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;

/**
 * Created by 孟晨 on 2018/5/18.
 */

public class PowerNumDialog extends Dialog implements View.OnClickListener {

    public PowerNumDialog(@NonNull Context context) {
        super(context);
    }

    public PowerNumDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PowerNumDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private ImageView imgPower;
    private ImageView imgClose;
    private TextView tvLockNotCleer;
    private EditText etPowerNumber;
    private TextView tvTrue;
    private TextView tvErrorPowerNum;
    private String imgId, imgUrl = null;
    private boolean isCheckImgCodeOk = false;
    private Subscription sendImageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_img_power_number);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        //授权码图片
        imgPower = findViewById(R.id.pop_powernum_img);
        //关闭按钮
        imgClose = findViewById(R.id.pop_close_img);
        imgClose.setOnClickListener(this);
        //刷新授权码
        tvLockNotCleer = findViewById(R.id.tv_look_not_cleer);
        tvLockNotCleer.setOnClickListener(this);
        //输入授权码-输入时候隐藏错误信息
        etPowerNumber = findViewById(R.id.et_input_power_number);
        etPowerNumber.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                tvErrorPowerNum.setVisibility(View.GONE);
            }
        });
        //确认
        tvTrue = findViewById(R.id.bt_pop_true);
        tvTrue.setOnClickListener(this);
        //错误码的隐藏
        // 之前的刷新加载适配器: 2018/5/18 输入错误时候显示，然后重新输入的时候隐藏掉——后期加入
        tvErrorPowerNum = findViewById(R.id.tv_error_powernum);

        getImgCode();
    }

    private void getImgCode() {
        HttpListener<SendImageCodeBean> httpListener = new HttpListener<SendImageCodeBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试获取验证图片出错：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(SendImageCodeBean sendImageCodeBean) {
                if (sendImageCodeBean.getCode() == 500) {
                    ToastUtils.show(sendImageCodeBean.getMessages().get(0).getMessage());
                    return;
                }
                ViseLog.e("测试获取验证图片：" + sendImageCodeBean);
                imgId = sendImageCodeBean.getData().getResponse().getImgId();
                imgUrl = sendImageCodeBean.getData().getResponse().getImgUrl();
                imgPower.setVisibility(View.VISIBLE);
                GlideUtils.glideLoadImg(getContext(), imgUrl, imgPower);
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        sendImageCode = personLoader.sendImageCode(httpListener);
    }


    private void checkImageCodeOk() {
        //需要先检验验证码
        if (ObjIsNull.isEmpty(imgId) || ObjIsNull.isEmpty(imgUrl)) {
            ToastUtils.show("请先验证!");
            return;
        }
        //需要输入验证码
        final String et_contents = etPowerNumber.getText().toString().trim();
        if (ObjIsNull.isEmpty(et_contents)) {
            ToastUtils.show("请输入验证码!");
            return;
        }
        HttpListener<CheckImageCodeBean> httpListener = new HttpListener<CheckImageCodeBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试验证图片出错：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(CheckImageCodeBean checkImageCodeBean) {
                ViseLog.e("测试验证图片：" + checkImageCodeBean);
                // FIXME 提示验证码输入失败
                if (checkImageCodeBean.getCode() == 500) {
                    tvErrorPowerNum.setVisibility(View.VISIBLE);
                    ToastUtils.show(checkImageCodeBean.getMessages().get(0).getMessage());
                    return;
                }
                isCheckImgCodeOk = checkImageCodeBean.getData().getResponse().isFlag();
                //验证成功,接口回调
                if (isCheckImgCodeOk) {
                    dialogClickCallback.clickTrueAndCloseDialog(PowerNumDialog.this, et_contents, imgId);
                }
            }
        };
        PersonLoader personSelfLoader = new PersonLoader(PersonLoader.BASEURL);
        sendImageCode = personSelfLoader.checkImageCode(Custom.APPID, imgId, et_contents, httpListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**关闭*/
            case R.id.pop_close_img:
                //取消上次订阅
                if (sendImageCode != null) {
                    if (sendImageCode.isUnsubscribed()) {
                        sendImageCode.unsubscribe();
                    }
                }

                if (dialogClickCallback != null) {
                    dialogClickCallback.closeDialog(this);
                }
                break;

            /**确认*/
            case R.id.bt_pop_true:
                String powerNum = etPowerNumber.getText().toString().trim();
                if (ObjIsNull.isEmpty(powerNum)) {
                    ToastUtils.show("请输入验证码");
                } else {
                    checkImageCodeOk();
                }
                break;

            /**看不清*/
            case R.id.tv_look_not_cleer:
                //取消上次订阅
                if (sendImageCode != null) {
                    if (sendImageCode.isUnsubscribed()) {
                        sendImageCode.unsubscribe();
                    }
                }
                getImgCode();
                break;

            default:
                break;
        }
    }


    /*************************************************数量修改的监听************************************************************/
    public DialogClickCallback dialogClickCallback;

    public void setDialogClickCallback(DialogClickCallback dialogClickCallback) {
        this.dialogClickCallback = dialogClickCallback;
    }

    public interface DialogClickCallback {
        //关闭弹窗
        void closeDialog(Dialog dialog);

        //确认关闭弹窗
        void clickTrueAndCloseDialog(Dialog dialog, String powerStr, String imgId);
    }
}

