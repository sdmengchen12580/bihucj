package com.aganyun.acode.ui.fra.myself;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import static com.aganyun.acode.R.style.dialog;
import static com.aganyun.acode.R.style.translatedialog;

import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.logininbus.LoginInBus;
import com.aganyun.acode.http.other.OtherLoader;
import com.aganyun.acode.http.other.bean.InvateUserBean;
import com.aganyun.acode.http.person.PersonLoader;
import com.aganyun.acode.http.person.bean.LogoutBean;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetMyPearlBean;
import com.aganyun.acode.http.suger.bean.GetPearlsBean;
import com.aganyun.acode.http.suger.bean.GetSignInBaseBean;
import com.aganyun.acode.http.suger.bean.GetSignedInNumBean;
import com.aganyun.acode.http.suger.bean.QiandaoBean;
import com.aganyun.acode.ui.act.HomeActivity;
import com.aganyun.acode.ui.act.fixinfo.FixPhoneNumActivity;
import com.aganyun.acode.ui.act.other.AboutUsActivity;
import com.aganyun.acode.ui.act.other.AllSugerActivity;
import com.aganyun.acode.ui.act.other.TodaySugerActivity;
import com.aganyun.acode.ui.act.other.WebActivity;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.ui.base.BaseFragment;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.OtherUtils;
import com.aganyun.acode.utils.PermissionsUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.user.UserUtils;
import com.aganyun.acode.view.FragmentSharePop;
import com.aganyun.acode.view.WebShareActivityPop;
import com.aganyun.acode.view.dialog.LoadDialog;
import com.aganyun.acode.view.signin.SignInDialog;
import com.aganyun.acode.view.signin.SignInSuccessPop;
import com.aganyun.acode.ymshare.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.log.ViseLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MySelfFragment extends BaseFragment {

    //今日糖果个数
    private TextView tvTodaySuger;
    //我的糖果
    private TextView tv_my_suger;
    private TextView tvName;
    private TextView tvFixMobile;
    //展开和收缩变化的图片
    private ImageView imgMore1;
    private ImageView imgMore2;
    private ImageView imgMore3;
    private ImageView imgMore4;
    //显示和隐藏的4个邀请展示内容
    private TextView tvMore1;
    private TextView tvMore2;
    private TextView tvMore3;
    private TextView tvMore4;
    //4个是否都展开
    private boolean oneTextIsExtanded = false;
    private boolean twoTextIsExtanded = false;
    private boolean threeTextIsExtanded = false;
    private boolean fourTextIsExtanded = false;
    //分享
    private FragmentSharePop fragmentSharePop;
    public PopupWindow popupWindow;
    //分享
    public WebShareActivityPop webShareActivityPop;
    public PopupWindow webPopupWindow;
    //分享权限
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.READ_LOGS,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.SET_DEBUG_APP,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.GET_ACCOUNTS,
//            Manifest.permission.WRITE_APN_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE,};
    public PopupWindow popSignSuccess;
    public SignInSuccessPop signInSuccessPop;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_self;
    }

    @Override
    public void initViews(Bundle savedInstanceState, View view) {
        onViewClicked(view);
        //登录后的信息获取
        if (!ObjIsNull.isEmpty((String) SPUtil.get(getContext(), "key", ""))) {
            tvName.setText((String) SPUtil.get(getContext(), "mobile", ""));
            //获取今日糖果数量
//            getPearls();
            //获取我的糖果
            getMyPearlBean();
        }
    }

    public void onViewClicked(View view) {
        //今日糖果个数
        tvTodaySuger = view.findViewById(R.id.tv_today_suger);
        //我的糖果
        tv_my_suger = view.findViewById(R.id.tv_my_suger);
        tvName = view.findViewById(R.id.tv_name);
        tvFixMobile = view.findViewById(R.id.tv_fix_mobile);
        //展开和收缩变化的图片
        imgMore1 = view.findViewById(R.id.img_more1);
        imgMore2 = view.findViewById(R.id.img_more2);
        imgMore3 = view.findViewById(R.id.img_more3);
        imgMore4 = view.findViewById(R.id.img_more4);
        //显示和隐藏的4个邀请展示内容
        tvMore1 = view.findViewById(R.id.tv_more1);
        tvMore2 = view.findViewById(R.id.tv_more2);
        tvMore3 = view.findViewById(R.id.tv_more3);
        tvMore4 = view.findViewById(R.id.tv_more4);
        /*我的糖果记录*/
        view.findViewById(R.id.ll_my_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), AllSugerActivity.class);
            }
        });
        /*今日糖果数量*/
        view.findViewById(R.id.ll_today_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), TodaySugerActivity.class);
            }
        });
        /*修改手机号*/
        view.findViewById(R.id.rv_fix_phone_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), FixPhoneNumActivity.class);
            }
        });
        /*关于我们*/
        view.findViewById(R.id.rv_about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), AboutUsActivity.class);
            }
        });
        /*推荐好友*/
        view.findViewById(R.id.rv_suggest_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                judgePermissions();
            }
        });
        /*签到*/
        view.findViewById(R.id.bt_signin_get_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //登录状态下的签到流程
                getSignInBase(true);
            }
        });
        /*第1个邀请的缩放*/
        view.findViewById(R.id.ll_top1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (!oneTextIsExtanded) {
                    oneTextIsExtanded = true;
                    imgMore1.setImageResource(R.drawable.img_top);
                    ViewUtils.showView(tvMore1);
                    return;
                }
                oneTextIsExtanded = false;
                imgMore1.setImageResource(R.drawable.img_bottom);
                ViewUtils.GoneView(tvMore1);
            }
        });
        /*第2个邀请的缩放*/
        view.findViewById(R.id.ll_top2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (!twoTextIsExtanded) {
                    twoTextIsExtanded = true;
                    imgMore2.setImageResource(R.drawable.img_top);
                    ViewUtils.showView(tvMore2);
                    return;
                }
                twoTextIsExtanded = false;
                imgMore2.setImageResource(R.drawable.img_bottom);
                ViewUtils.GoneView(tvMore2);
            }
        });
        /*第3个邀请的缩放*/
        view.findViewById(R.id.ll_top3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (!threeTextIsExtanded) {
                    threeTextIsExtanded = true;
                    imgMore3.setImageResource(R.drawable.img_top);
                    ViewUtils.showView(tvMore3);
                    return;
                }
                threeTextIsExtanded = false;
                imgMore3.setImageResource(R.drawable.img_bottom);
                ViewUtils.GoneView(tvMore3);
            }
        });
        /*第4个邀请的缩放*/
        view.findViewById(R.id.ll_top4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                if (!fourTextIsExtanded) {
                    fourTextIsExtanded = true;
                    imgMore4.setImageResource(R.drawable.img_top);
                    ViewUtils.showView(tvMore4);
                    return;
                }
                fourTextIsExtanded = false;
                imgMore4.setImageResource(R.drawable.img_bottom);
                ViewUtils.GoneView(tvMore4);
            }
        });
        /*邀请界面*/
        view.findViewById(R.id.bt_friend1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
//                invateUser();
                shareUrlToPlat();
            }
        });
        view.findViewById(R.id.bt_friend2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
//                invateUser();
                shareUrlToPlat();
            }
        });
        /*立即分享*/
        view.findViewById(R.id.bt_friend3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ((HomeActivity) getActivity()).backToHomePage();
            }
        });
        /*立即阅读*/
        view.findViewById(R.id.bt_friend4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ((HomeActivity) getActivity()).backToHomePage();
            }
        });
        /*退出登录*/
        view.findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                logout();
            }
        });
    }

    //-----------------------------------工具类-----------------------------------
    //权限判断
    public void judgePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionsUtils.checkPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.CALL_PHONE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_LOGS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SET_DEBUG_APP)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.GET_ACCOUNTS)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.SYSTEM_ALERT_WINDOW)
//                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.WRITE_APN_SETTINGS)
                    || !PermissionsUtils.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //拒绝-点击了不在询问
                for (int i = 0; i < permissions.length; i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                        Log.e("测试分享: ", "拒绝过");
                        ToastUtils.show("您已拒绝分享所用到的相关权限,可到应用管理中打开权限!");
                        break;
                    }
                }
                Log.e("测试分享: ", "开始请求权限");
                //没拒绝去请求权限
                ActivityCompat.requestPermissions(getActivity(), permissions, 2);
            } else {
                Log.e("测试分享: ", "有权限，直接弹窗");
                showSuggestFriendDialog();
            }
        } else {
            Log.e("测试分享: ", "小版本,不申请权限,直接弹窗");
            showSuggestFriendDialog();
        }
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public MySelfFragment() {
        EventBus.getDefault().register(MySelfFragment.this);
    }

    public static MySelfFragment newInstance() {
        MySelfFragment fragment = new MySelfFragment();
        return fragment;
    }

    //分享到各个平台
    public void shareToWhichPlat(SHARE_MEDIA type, Bitmap mShareContent) {
        ShareUtils.newInstance().share2platform_bitmap(getActivity(),
                type,
                "",
                mShareContent, false, null);
//        Log.e("测试分享: ", "分享内容:" + mShareContent);
//        ShareUtils.newInstance().share2platform_web(getActivity(),
//                type,
//                mShareContent,
//                "我正在使用【壹码】，推荐给你",
//                "币虎在手，资讯、快讯、行情、糖果应有尽有",
//                Custom.LOGO_URL,
//                true);
    }

    //推荐好友弹窗
    public void showSuggestFriendDialog() {
        fragmentSharePop = new FragmentSharePop(MySelfFragment.this);
        fragmentSharePop.setClickCallback(new FragmentSharePop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String shareContent, View view) {
                Bitmap bitmap = ViewUtils.gitBitmapFromView(view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("分享失败,请重试!");
                    return;
                }
                ShareUtils.newInstance().share2platform_bitmap(getActivity(),
                        type,
                        "壹码",
                        bitmap, false, null);
            }
        });
        popupWindow = fragmentSharePop.showPop(2, null, null, null);
    }

    //关闭弹窗
    public void dissmissPop() {
        if (popupWindow != null && popupWindow.isShowing()) {
            //取消订阅
            OtherUtils.cancelSub(fragmentSharePop.getZxing);
            fragmentSharePop.backNormalPopBg();
            fragmentSharePop = null;
            popupWindow.dismiss();
            popupWindow = null;
        }
        if (popSignSuccess != null && popSignSuccess.isShowing()) {
            signInSuccessPop.backNormalPopBg();
            signInSuccessPop = null;
            popSignSuccess.dismiss();
            popSignSuccess = null;
        }
        if (webPopupWindow != null && webPopupWindow.isShowing()) {
            webShareActivityPop.backNormalPopBg();
            webShareActivityPop = null;
            webPopupWindow.dismiss();
            webPopupWindow = null;
        }
    }

    //邀请好友的分享
    public void shareUrlToPlat() {
        webShareActivityPop = new WebShareActivityPop((BaseActivity) getActivity());
        webShareActivityPop.setClickCallback(new WebShareActivityPop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String shareContent, View view) {
                Bitmap bitmap = ViewUtils.getBitmapByView((ScrollView) view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("分享失败,请重试!");
                    return;
                }
                shareToWhichPlat(type, bitmap);
            }
        });
        webPopupWindow = webShareActivityPop.showPop("myself", true);
    }

    //--------------------------------------eventbus---------------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginChangeUi(LoginInBus loginInBus) {
        if (loginInBus != null) {
            switch (loginInBus.getLoginCode()) {
                // FIXME 0为登录成功
                case 0:
                    tvName.setText((String) SPUtil.get(getContext(), "mobile", ""));
                    tvFixMobile.setText((String) SPUtil.get(getContext(), "mobile", ""));
                    //回到我的页面
                    ((HomeActivity) getActivity()).backToHomePage();
                    //需要签到
                    if (loginInBus.isNeedSignIn()) {
                        getSignedInNum();
                    }
                    break;

                // FIXME 1为修改手机号
                case 1:
                    //回到首页
                    tvName.setText((String) SPUtil.get(getContext(), "mobile", ""));
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MySelfFragment.this);
    }

    //-------------------------------------接口-------------------------------------------
    private void logout() {
        final LoadDialog loadDialog = new LoadDialog(getContext(), translatedialog);
        loadDialog.show();
        loadDialog.startDialog("修改中...");
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<LogoutBean> httpListener = new HttpListener<LogoutBean>() {
            @Override
            public void onError(String errorMsg) {
                loadDialog.closeDialog();
                ViseLog.e("测试退出登录失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(LogoutBean logoutBean) {
                loadDialog.closeDialog();
                ViseLog.e("测试退出登录成功：" + logoutBean);
                // FIXME 提示验证码输入失败
                if (logoutBean.getCode() == 500) {
                    ToastUtils.show(logoutBean.getMessages().get(0).getMessage());
                    return;
                }
                tvName.setText("");
                //清空sp
                UserUtils.userSpClear();
                //回到首页
                ((HomeActivity) getActivity()).backToHomePage();
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        personLoader.logout(Custom.APPID, key, httpListener);
    }

    //每天签到获得的糖果列表
    private void getSignInBase(final boolean isSignIned) {
        final String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<GetSignInBaseBean> httpListener = new HttpListener<GetSignInBaseBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试获取每天签到获得的糖果列表失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetSignInBaseBean getSignInBaseBean) {
                ViseLog.e("测试获取每天签到获得的糖果列表成功:" + getSignInBaseBean);
                if (getSignInBaseBean.getCode() == 500) {
                    ToastUtils.show(getSignInBaseBean.getMessages().get(0).getMessage());
                    return;
                }
                //显示积分弹窗
                SignInDialog signInDialog = new SignInDialog(
                        getContext(), dialog, getSignInBaseBean.getData().getResponse(), key, isSignIned);
                signInDialog.setDialogClickCallback(new SignInDialog.DialogClickCallback() {
                    @Override
                    public void signIn(Dialog dialog, boolean signSuccess, String sugerNum) {
                        dialog.dismiss();
                        //签到成功的弹窗
                        if (signSuccess) {
                            showSignSuccessPop("+" + sugerNum);
                            //更新糖果数量
                            getMyPearlBean();
                        }
                    }

                    @Override
                    public void changeToSignIn(Dialog dialog) {
                        //我的页面一定签到过的
                    }
                });
                signInDialog.show();
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignInBase(Custom.APPID, key, httpListener);
    }

    //获取我的糖果
    public void getMyPearlBean() {
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<GetMyPearlBean> httpListener = new HttpListener<GetMyPearlBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试我的糖果失败：" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetMyPearlBean getMyPearlBean) {
                ViseLog.e("测试我的糖果成功：" + getMyPearlBean);
                if (getMyPearlBean.getCode() == 500) {
                    ToastUtils.show(getMyPearlBean.getMessages().get(0).getMessage());
                    return;
                }
                tv_my_suger.setText(getMyPearlBean.getData().getResponse().getTotal());
                tvTodaySuger.setText(getMyPearlBean.getData().getResponse().getTodayTotal());
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getMyPearl(Custom.APPID, key, httpListener);
    }

    //邀请好友的连接地址
    private void invateUser() {
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<InvateUserBean> httpListener = new HttpListener<InvateUserBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试邀请好友的连接地址失败:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(InvateUserBean invateUserBean) {
                ViseLog.e("测试邀请好友的连接地址成功:" + invateUserBean);
                if (invateUserBean.getCode() == 500) {
                    ToastUtils.show(invateUserBean.getMessages().get(0).getMessage());
                    return;
                }
                String webUrl = invateUserBean.getData().getResponse().getInvateUrl();
                if (!ObjIsNull.isEmpty(webUrl)) {
//                    shareUrlToPlat(webUrl);
                }
            }
        };
        OtherLoader otherLoader = new OtherLoader(OtherLoader.BASEURL);
        otherLoader.invateUser(Custom.APPID, key, httpListener);
    }

    //----------------------------------------------签到相关----------------------------------------
    //获取签到天数
    private void getSignedInNum() {
        final String key = (String) SPUtil.get(getContext(), "key", "");
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
                //今日是否已经签到
                boolean todaySignIn = getSignedInNumBean.getData().getResponse().isTodaySignIn();
                //没签到去签到
                if (!todaySignIn) {
                    qiandao(key);
                }
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignedInNum(Custom.APPID, key, httpListener);
    }

    //签到
    private void qiandao(String key) {
        HttpListener<QiandaoBean> httpListener = new HttpListener<QiandaoBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("测试签到失败:" + errorMsg);
            }

            @Override
            public void onSuccess(QiandaoBean qiandaoBean) {
                ViseLog.e("测试签到成功:" + qiandaoBean);
                if (qiandaoBean.getCode() == 500) {
                    ToastUtils.show(qiandaoBean.getMessages().get(0).getMessage());
                    return;
                }
                showSignSuccessPop(qiandaoBean.getData().getResponse().getPearl());
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.qiandao(Custom.APPID, key, httpListener);
    }

    //显示登录成功的弹窗
    public void showSignSuccessPop(String sugerNum) {
        signInSuccessPop = new SignInSuccessPop((BaseActivity) getActivity());
        signInSuccessPop.setAutoHidePopCallback(new SignInSuccessPop.AutoHidePopCallback() {
            @Override
            public void autoHidePop() {
                signInSuccessPop.dismissPop();
                signInSuccessPop = null;
                popSignSuccess = null;
            }
        });
        popSignSuccess = signInSuccessPop.showPop(sugerNum, true);
    }
}
