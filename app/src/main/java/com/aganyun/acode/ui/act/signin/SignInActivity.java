package com.aganyun.acode.ui.act.signin;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import static com.aganyun.acode.R.style.translatedialog;

import com.aganyun.acode.R;
import com.aganyun.acode.callback.EdittextWatcherCallback;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.logininbus.LoginInBus;
import com.aganyun.acode.http.person.PersonLoader;
import com.aganyun.acode.http.person.bean.LoginBean;
import com.aganyun.acode.ui.act.fixinfo.FixPasswordActivity;
import com.aganyun.acode.ui.act.signup.SignUpActivity;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.view.dialog.LoadDialog;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.EventBus;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;

public class SignInActivity extends BaseActivity {

    private EditText etInputName;
    private EditText etInputPassword;
    private ImageView imgPasswordDelect;
    private ImageView imgPasswordCansee;
    private ImageView imgInputNameDelect;
    private boolean passWordCanSee = false;
    private boolean powerNumIsOk = false;
    private boolean passWordIsOk = false;
    //是否需要签到
    private boolean needSignIn = false;
    //登录
    private Subscription login;

    @Override
    protected int getViewId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onViewClicked();
        try {
            needSignIn = getIntent().getBooleanExtra("needSignIn", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //账号和密码监听
        powerNumListener();
        passWordListener();
    }

    public void onViewClicked() {
        etInputName = findViewById(R.id.et_input_name);
        etInputPassword = findViewById(R.id.et_input_password);
        imgPasswordDelect = findViewById(R.id.img_password_delect);
        imgPasswordCansee = findViewById(R.id.img_password_cansee);
        imgInputNameDelect = findViewById(R.id.img_input_name_delect);
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
        /*密码设置为可见*/
        findViewById(R.id.img_password_cansee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //不可见--->可见
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
        /*去注册*/
        findViewById(R.id.tv_to_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(SignInActivity.this, SignUpActivity.class);
            }
        });
        /*忘记密码*/
        findViewById(R.id.tv_fogoret_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(SignInActivity.this, FixPasswordActivity.class);
            }
        });
        /*登录*/
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (powerNumIsOk && passWordIsOk) {
                    login();
                    return;
                }
                ToastUtils.show(getString(R.string._str_input_powernum_and_password));
            }
        });
    }

    //-----------------------------------------------工具类-----------------------------------------
    private void powerNumListener() {
        etInputName.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                String powerNum = etInputName.getText().toString().trim();
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

    private void passWordListener() {
        etInputPassword.addTextChangedListener(new EdittextWatcherCallback() {
            @Override
            protected void textChanged() {
                String passWord = etInputPassword.getText().toString().trim();
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

    //-----------------------------------------------接口-----------------------------------------
    private void login() {
        final LoadDialog loadDialog = new LoadDialog(SignInActivity.this, translatedialog);
        loadDialog.show();
        loadDialog.startDialog("登录中...");
        final String mobile = etInputName.getText().toString().trim();
        final String password = etInputPassword.getText().toString().trim();
        HttpListener<LoginBean> httpListener = new HttpListener<LoginBean>() {
            @Override
            public void onError(String errorMsg) {
                loadDialog.closeDialog();
                ViseLog.e("登录错误：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(LoginBean loginBean) {
                loadDialog.closeDialog();
                ViseLog.e("登录成功" + loginBean);
                if (loginBean.getCode() == 500) {
                    ToastUtils.show(loginBean.getMessages().get(0).getMessage());
                    return;
                }
                ToastUtils.show("登录成功!");
                //mobile
                SPUtil.put(SignInActivity.this, "mobile", mobile);
                //用户登录
                userLoginIn(mobile);
                //password
                SPUtil.put(SignInActivity.this, "password", password);
                //key
                SPUtil.put(SignInActivity.this, "key", loginBean.getData().getResponse().getSessionKey());
                //name
                String name = loginBean.getData().getResponse().getNickName();
                SPUtil.put(SignInActivity.this, "name", name);
                //头像
                SPUtil.put(SignInActivity.this, "avatar", loginBean.getData().getResponse().getAvatar());
                //通知我的页面登录成功
                LoginInBus loginInBus = new LoginInBus(0);
                //传入是否需要签到
                loginInBus.setNeedSignIn(needSignIn);
                EventBus.getDefault().post(loginInBus);
                finish();
                ViewManager.getInstance().finishActivity(SignInActivity.this);
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        login = personLoader.login(Custom.APPID, mobile, password, httpListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (login != null) {
            if (login.isUnsubscribed()) {
                login.unsubscribe();
            }
        }
    }

    //-------------------------------------------友盟---------------------------------------------
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    public void userLoginIn(String userName) {
//        MobclickAgent.onProfileSignIn(userName);
    }
}
