package com.aganyun.acode.ui.act.signup;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.aganyun.acode.R.style.translatedialog;

import com.aganyun.acode.R;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.http.person.PersonLoader;
import com.aganyun.acode.http.person.bean.RegBean;
import com.aganyun.acode.http.person.bean.SendSmsCodeBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.view.dialog.LoadDialog;
import com.aganyun.acode.view.dialog.PowerNumDialog;
import com.vise.log.ViseLog;

import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;

import static com.aganyun.acode.R.style.dialog;

public class SignUpActivity extends BaseActivity {

    private EditText etInputName;
    private ImageView imgInputNameDelect;
    private EditText etInputPowerCode;
    private Button btSendPowerCode;
    private EditText etInputPassword;
    private ImageView imgPasswordDelect;
    private ImageView imgPasswordCansee;
    private MyHandler myHandler;
    //按钮是否可以点击
    private boolean btSendPowerCanClicked = true;
    //密码可见
    private boolean passWordCanSee = false;
    //账号可用
    private boolean powerNumIsOk = false;
    //验证码可用
    private boolean powerCodeIsOk = false;
    //密码可用
    private boolean passWordIsOk = false;
    //验证码倒计时
    private Timer timer;
    private int currentTimerNum = 60;
    //edittext的内容
    private String powerNum;
    private String powerCode;
    private String passWord;
    //图片验证码id
    private String imgId;
    private String imgContent;
    private Subscription sendSmsCode;

    @Override
    protected int getViewId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        myHandler = new MyHandler(SignUpActivity.this);
        //输入框监听
        initPowerNumListener();
        initPowerCodeListener();
        initPassWordListener();
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (sendSmsCode != null) {
            if (sendSmsCode.isUnsubscribed()) {
                sendSmsCode.unsubscribe();
            }
        }
    }

    //---------------------------------------工具类---------------------------------------
    public void onViewClicked() {
        etInputName = findViewById(R.id.et_input_name);
        imgInputNameDelect = findViewById(R.id.img_input_name_delect);
        etInputPowerCode = findViewById(R.id.et_input_power_code);
        btSendPowerCode = findViewById(R.id.bt_send_power_code);
        etInputPassword = findViewById(R.id.et_input_password);
        imgPasswordDelect = findViewById(R.id.img_password_delect);
        imgPasswordCansee = findViewById(R.id.img_password_cansee);
        /*返回*/
        findViewById(R.id.ll_login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        });
        /*删除账号*/
        findViewById(R.id.img_input_name_delect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                etInputName.setText("");
                imgInputNameDelect.setVisibility(View.GONE);
                powerNumIsOk = false;
            }
        });
        /*发送验证码*/
        findViewById(R.id.bt_send_power_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (powerNum == null) {
                    ToastUtils.show("请先填写手机号!");
                    return;
                }
                if (powerNum.length() != 11) {
                    ToastUtils.show("请先填写正确的手机号!");
                    return;
                }
                //可点击
                if (btSendPowerCanClicked) {
                    btSendPowerCanClicked = false;
                    btSendPowerCode.setTextColor(getResources().getColor(R.color.tx_color_999999));
                    btSendPowerCode.setBackground(getResources().getDrawable(R.drawable.bt_xml_15radiues_white_solid));
                    getPowerCode();
                    return;
                } else {
                    ToastUtils.show(getString(R.string._str_wait_send_power_code));
                }
            }
        });
        /*密码可见*/
        findViewById(R.id.img_password_cansee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //不可见--->可见
                findViewById(R.id.bt_jump).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isFastDoubleClick()) {
                            return;
                        }
                    }
                });
                if (!passWordCanSee) {
                    passWordCanSee = true;
                    imgPasswordCansee.setImageResource(R.drawable.img_password_cansee);
                    etInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                //可见--->不可见
                else {
                    passWordCanSee = false;
                    imgPasswordCansee.setImageResource(R.drawable.img_password_not_cansee);
                    etInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        /*注册*/
        findViewById(R.id.bt_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (!ObjIsNull.isEmpty(passWord)) {
                    if (!(passWord.length() < 20 && passWord.length() >= 6)) {
                        ToastUtils.show("请输入6-20位数的密码!");
                        return;
                    }
                }
                if (powerNumIsOk && powerCodeIsOk && passWordIsOk) {
                    reg();
                    return;
                }
                ToastUtils.show(getString(R.string._str_input_all_signup_info));
            }
        });
        /*密码删除*/
        findViewById(R.id.img_password_delect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                etInputPassword.setText("");
                imgPasswordDelect.setVisibility(View.GONE);
                passWordIsOk = false;
            }
        });
    }

    private static class MyHandler extends Handler//ui更新
    {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<SignUpActivity> mActivty;

        public MyHandler(SignUpActivity activity) {
            mActivty = new WeakReference<SignUpActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SignUpActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                if (msg.what == 1) {
                    activity.btSendPowerCode.setText(activity.currentTimerNum + "秒后重新发送");
                }
            }
        }
    }

    private void getPowerCode()//获取验证码
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTimerNum--;
                if (currentTimerNum == 0) {
                    //取消定时
                    timer.cancel();
                    timer = null;
                    currentTimerNum = 60;
                    //可重发送验证码
                    btSendPowerCanClicked = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btSendPowerCode.setText(R.string._str_send_power_code);
                            btSendPowerCode.setTextColor(getResources().getColor(R.color.theme_color));
                            btSendPowerCode.setBackground(getResources().getDrawable(R.drawable.bt_xml_15radiues_orange_solid));
                        }
                    });
                    return;
                }
                myHandler.sendEmptyMessage(1);
            }
        }, 0, 1000);

        //弹窗
        PowerNumDialog powerNumDialog = new PowerNumDialog(
                SignUpActivity.this, dialog);
        powerNumDialog.setDialogClickCallback(new PowerNumDialog.DialogClickCallback() {
            @Override
            public void closeDialog(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void clickTrueAndCloseDialog(Dialog dialog, String mpowerStr, String mimgId) {
                imgContent = mpowerStr;
                imgId = mimgId;
                sendSmsCode();
                dialog.dismiss();
            }
        });
        powerNumDialog.show();
    }

    private void initPowerNumListener()//账号监听
    {
        etInputName.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                powerNum = etInputName.getText().toString().trim();
                if (!ObjIsNull.isEmpty(powerNum)) {
                    imgInputNameDelect.setVisibility(View.VISIBLE);
                    powerNumIsOk = true;
                } else {
                    imgInputNameDelect.setVisibility(View.GONE);
                    powerNumIsOk = false;
                }
            }
        });
    }

    private void initPowerCodeListener()//验证码监听
    {
        etInputPowerCode.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                powerCode = etInputPowerCode.getText().toString().trim();
                if (!ObjIsNull.isEmpty(powerCode)) {
                    powerCodeIsOk = true;
                } else {
                    powerCodeIsOk = false;
                }
            }
        });
    }

    private void initPassWordListener()//密码监听
    {
        etInputPassword.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                passWord = etInputPassword.getText().toString().trim();
                if (!ObjIsNull.isEmpty(passWord)) {
                    imgPasswordDelect.setVisibility(View.VISIBLE);
                    passWordIsOk = true;
                } else {
                    imgPasswordDelect.setVisibility(View.GONE);
                    passWordIsOk = false;
                }
            }
        });
    }

    //---------------------------------------工具类---------------------------------------
    private void sendSmsCode() {
        HttpListener<SendSmsCodeBean> httpListener = new HttpListener<SendSmsCodeBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试获取手机验证码出错：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(SendSmsCodeBean sendSmsCodeBean) {
                ViseLog.e("测试获取手机验证码成功：" + sendSmsCodeBean);
                if (sendSmsCodeBean.getCode() == 500) {
                    ToastUtils.show(sendSmsCodeBean.getMessages().get(0).getMessage());
                    return;
                }
                ToastUtils.show("已发送验证码至您的手机!");
            }
        };
        PersonLoader personSelfLoader = new PersonLoader(PersonLoader.BASEURL);
        sendSmsCode = personSelfLoader.sendSmsCode(Custom.APPID, imgId, imgContent, powerNum, "reg", httpListener);
    }

    private void reg() {
        final LoadDialog loadDialog = new LoadDialog(SignUpActivity.this, translatedialog);
        loadDialog.show();
        loadDialog.startDialog("注册中...");
        HttpListener<RegBean> httpListener = new HttpListener<RegBean>() {
            @Override
            public void onError(String errorMsg) {
                loadDialog.closeDialog();
                ViseLog.e("测试注册成功：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(RegBean regBean) {
                loadDialog.closeDialog();
                ViseLog.e("测试注册成功：" + regBean);
                if (regBean.getCode() == 500) {
                    ToastUtils.show(regBean.getMessages().get(0).getMessage());
                    return;
                }
                ViewManager.getInstance().finishActivity();
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        personLoader.reg(Custom.APPID, "", powerCode, powerNum, "", "", passWord, httpListener);
    }
}
