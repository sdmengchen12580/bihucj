package com.aganyun.acode.ui.act.fixinfo;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.aganyun.acode.R.style.translatedialog;

import static com.aganyun.acode.R.style.dialog;

import com.aganyun.acode.R;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.logininbus.LoginInBus;
import com.aganyun.acode.http.person.PersonLoader;
import com.aganyun.acode.http.person.bean.ChangeMobileBean;
import com.aganyun.acode.http.person.bean.SendSmsCodeBean;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.view.dialog.LoadDialog;
import com.aganyun.acode.view.dialog.PowerNumDialog;
//import com.umeng.analytics.MobclickAgent;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.EventBus;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import rx.Subscription;

public class FixPhoneNumActivity extends BaseActivity {

    private EditText etInputName;
    private ImageView imgInputNameDelect;
    private EditText etInputPowerCode;
    private Button btSendPowerCode;
    private MyHandler myHandler;
    //按钮是否可以点击
    private boolean btSendPowerCanClicked = true;
    //账号可用
    private boolean powerNumIsOk = false;
    //验证码可用
    private boolean powerCodeIsOk = false;
    //验证码倒计时
    private Timer timer;
    private int currentTimerNum = 60;
    //edittext的内容
    private String powerNum;
    private String powerCode;
    //图片验证码id
    private String imgId;
    private String imgContent;
    private Subscription sendSmsCode;

    @Override
    protected int getViewId() {
        return R.layout.activity_fix_phone_num;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        myHandler = new MyHandler(FixPhoneNumActivity.this);
        //输入框监听
        initPowerNumListener();
        initPowerCodeListener();
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
    public void onViewClicked()//初始化控件
    {
        etInputName = findViewById(R.id.et_input_name);
        imgInputNameDelect = findViewById(R.id.img_input_name_delect);
        etInputPowerCode = findViewById(R.id.et_input_power_code);
        btSendPowerCode = findViewById(R.id.bt_send_power_code);
        /*返回*/
        findViewById(R.id.ll_fix_phone_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ViewManager.getInstance().finishActivity();

            }
        });
        /*账号删除*/
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
        /*验证码发送*/
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
                if (!powerNumIsOk) {
                    ToastUtils.show("请先输入新手机号!");
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
        /*修改手机号*/
        findViewById(R.id.tv_fix_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (powerNumIsOk && powerCodeIsOk) {
                    changeMobile();
                    return;
                }
                ToastUtils.show(getString(R.string._str_input_all_signup_info));
            }
        });
    }

    private static class MyHandler extends Handler //ui更新
    {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<FixPhoneNumActivity> mActivty;

        public MyHandler(FixPhoneNumActivity activity) {
            mActivty = new WeakReference<FixPhoneNumActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FixPhoneNumActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                if (msg.what == 1) {
                    activity.btSendPowerCode.setText(activity.currentTimerNum + "秒后重新发送");
                }
            }
        }
    }

    private void getPowerCode() //获取验证码
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
                FixPhoneNumActivity.this, dialog);
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
                if (!ObjIsNull.isEmpty(powerNum) && powerNum.length() == 11) {
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

    //-------------------------------------接口--------------------------------------------
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
        sendSmsCode = personSelfLoader.sendSmsCode(Custom.APPID, imgId, imgContent, powerNum, "changeMobile", httpListener);
    }

    private void changeMobile() {
        String password = (String) SPUtil.get(FixPhoneNumActivity.this, "password", "");
        String key = (String) SPUtil.get(FixPhoneNumActivity.this, "key", "");
        final LoadDialog loadDialog = new LoadDialog(FixPhoneNumActivity.this, translatedialog);
        loadDialog.show();
        loadDialog.startDialog("修改中...");
        HttpListener<ChangeMobileBean> httpListener = new HttpListener<ChangeMobileBean>() {
            @Override
            public void onError(String errorMsg) {
                loadDialog.closeDialog();
                ViseLog.e("测试修改手机号失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(ChangeMobileBean changeMobileBean) {
                loadDialog.closeDialog();
                ViseLog.e("测试修改手机号成功:" + changeMobileBean);
                if (changeMobileBean.getCode() == 500) {
                    ToastUtils.show(changeMobileBean.getMessages().get(0).getMessage());
                    return;
                }
                //mobile
                SPUtil.put(FixPhoneNumActivity.this, "mobile", powerNum);
                EventBus.getDefault().post(new LoginInBus(1));
                ViewManager.getInstance().finishActivity();
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        personLoader.changeMobile(Custom.APPID, powerCode, powerNum, password, key, httpListener);
    }
}
