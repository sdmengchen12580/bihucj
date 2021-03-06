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

    //??????????????????
    private TextView tvTodaySuger;
    //????????????
    private TextView tv_my_suger;
    private TextView tvName;
    private TextView tvFixMobile;
    //??????????????????????????????
    private ImageView imgMore1;
    private ImageView imgMore2;
    private ImageView imgMore3;
    private ImageView imgMore4;
    //??????????????????4?????????????????????
    private TextView tvMore1;
    private TextView tvMore2;
    private TextView tvMore3;
    private TextView tvMore4;
    //4??????????????????
    private boolean oneTextIsExtanded = false;
    private boolean twoTextIsExtanded = false;
    private boolean threeTextIsExtanded = false;
    private boolean fourTextIsExtanded = false;
    //??????
    private FragmentSharePop fragmentSharePop;
    public PopupWindow popupWindow;
    //??????
    public WebShareActivityPop webShareActivityPop;
    public PopupWindow webPopupWindow;
    //????????????
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
        //????????????????????????
        if (!ObjIsNull.isEmpty((String) SPUtil.get(getContext(), "key", ""))) {
            tvName.setText((String) SPUtil.get(getContext(), "mobile", ""));
            //????????????????????????
//            getPearls();
            //??????????????????
            getMyPearlBean();
        }
    }

    public void onViewClicked(View view) {
        //??????????????????
        tvTodaySuger = view.findViewById(R.id.tv_today_suger);
        //????????????
        tv_my_suger = view.findViewById(R.id.tv_my_suger);
        tvName = view.findViewById(R.id.tv_name);
        tvFixMobile = view.findViewById(R.id.tv_fix_mobile);
        //??????????????????????????????
        imgMore1 = view.findViewById(R.id.img_more1);
        imgMore2 = view.findViewById(R.id.img_more2);
        imgMore3 = view.findViewById(R.id.img_more3);
        imgMore4 = view.findViewById(R.id.img_more4);
        //??????????????????4?????????????????????
        tvMore1 = view.findViewById(R.id.tv_more1);
        tvMore2 = view.findViewById(R.id.tv_more2);
        tvMore3 = view.findViewById(R.id.tv_more3);
        tvMore4 = view.findViewById(R.id.tv_more4);
        /*??????????????????*/
        view.findViewById(R.id.ll_my_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), AllSugerActivity.class);
            }
        });
        /*??????????????????*/
        view.findViewById(R.id.ll_today_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), TodaySugerActivity.class);
            }
        });
        /*???????????????*/
        view.findViewById(R.id.rv_fix_phone_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), FixPhoneNumActivity.class);
            }
        });
        /*????????????*/
        view.findViewById(R.id.rv_about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ChangeActUtils.changeActivity(getContext(), AboutUsActivity.class);
            }
        });
        /*????????????*/
        view.findViewById(R.id.rv_suggest_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                judgePermissions();
            }
        });
        /*??????*/
        view.findViewById(R.id.bt_signin_get_suger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                //??????????????????????????????
                getSignInBase(true);
            }
        });
        /*???1??????????????????*/
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
        /*???2??????????????????*/
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
        /*???3??????????????????*/
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
        /*???4??????????????????*/
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
        /*????????????*/
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
        /*????????????*/
        view.findViewById(R.id.bt_friend3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ((HomeActivity) getActivity()).backToHomePage();
            }
        });
        /*????????????*/
        view.findViewById(R.id.bt_friend4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFastDoubleClick()) {
                    return;
                }
                ((HomeActivity) getActivity()).backToHomePage();
            }
        });
        /*????????????*/
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

    //-----------------------------------?????????-----------------------------------
    //????????????
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
                //??????-?????????????????????
                for (int i = 0; i < permissions.length; i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                        Log.e("????????????: ", "?????????");
                        ToastUtils.show("??????????????????????????????????????????,?????????????????????????????????!");
                        break;
                    }
                }
                Log.e("????????????: ", "??????????????????");
                //????????????????????????
                ActivityCompat.requestPermissions(getActivity(), permissions, 2);
            } else {
                Log.e("????????????: ", "????????????????????????");
                showSuggestFriendDialog();
            }
        } else {
            Log.e("????????????: ", "?????????,???????????????,????????????");
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

    //?????????????????????
    public void shareToWhichPlat(SHARE_MEDIA type, Bitmap mShareContent) {
        ShareUtils.newInstance().share2platform_bitmap(getActivity(),
                type,
                "",
                mShareContent, false, null);
//        Log.e("????????????: ", "????????????:" + mShareContent);
//        ShareUtils.newInstance().share2platform_web(getActivity(),
//                type,
//                mShareContent,
//                "??????????????????????????????????????????",
//                "????????????????????????????????????????????????????????????",
//                Custom.LOGO_URL,
//                true);
    }

    //??????????????????
    public void showSuggestFriendDialog() {
        fragmentSharePop = new FragmentSharePop(MySelfFragment.this);
        fragmentSharePop.setClickCallback(new FragmentSharePop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String shareContent, View view) {
                Bitmap bitmap = ViewUtils.gitBitmapFromView(view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("????????????,?????????!");
                    return;
                }
                ShareUtils.newInstance().share2platform_bitmap(getActivity(),
                        type,
                        "??????",
                        bitmap, false, null);
            }
        });
        popupWindow = fragmentSharePop.showPop(2, null, null, null);
    }

    //????????????
    public void dissmissPop() {
        if (popupWindow != null && popupWindow.isShowing()) {
            //????????????
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

    //?????????????????????
    public void shareUrlToPlat() {
        webShareActivityPop = new WebShareActivityPop((BaseActivity) getActivity());
        webShareActivityPop.setClickCallback(new WebShareActivityPop.ClickCallback() {
            @Override
            public void clickShared(SHARE_MEDIA type, String shareContent, View view) {
                Bitmap bitmap = ViewUtils.getBitmapByView((ScrollView) view);
                if (ObjIsNull.ObjIsNull(bitmap)) {
                    ToastUtils.show("????????????,?????????!");
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
                // FIXME 0???????????????
                case 0:
                    tvName.setText((String) SPUtil.get(getContext(), "mobile", ""));
                    tvFixMobile.setText((String) SPUtil.get(getContext(), "mobile", ""));
                    //??????????????????
                    ((HomeActivity) getActivity()).backToHomePage();
                    //????????????
                    if (loginInBus.isNeedSignIn()) {
                        getSignedInNum();
                    }
                    break;

                // FIXME 1??????????????????
                case 1:
                    //????????????
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

    //-------------------------------------??????-------------------------------------------
    private void logout() {
        final LoadDialog loadDialog = new LoadDialog(getContext(), translatedialog);
        loadDialog.show();
        loadDialog.startDialog("?????????...");
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<LogoutBean> httpListener = new HttpListener<LogoutBean>() {
            @Override
            public void onError(String errorMsg) {
                loadDialog.closeDialog();
                ViseLog.e("???????????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(LogoutBean logoutBean) {
                loadDialog.closeDialog();
                ViseLog.e("???????????????????????????" + logoutBean);
                // FIXME ???????????????????????????
                if (logoutBean.getCode() == 500) {
                    ToastUtils.show(logoutBean.getMessages().get(0).getMessage());
                    return;
                }
                tvName.setText("");
                //??????sp
                UserUtils.userSpClear();
                //????????????
                ((HomeActivity) getActivity()).backToHomePage();
            }
        };
        PersonLoader personLoader = new PersonLoader(PersonLoader.BASEURL);
        personLoader.logout(Custom.APPID, key, httpListener);
    }

    //?????????????????????????????????
    private void getSignInBase(final boolean isSignIned) {
        final String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<GetSignInBaseBean> httpListener = new HttpListener<GetSignInBaseBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("???????????????????????????????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetSignInBaseBean getSignInBaseBean) {
                ViseLog.e("???????????????????????????????????????????????????:" + getSignInBaseBean);
                if (getSignInBaseBean.getCode() == 500) {
                    ToastUtils.show(getSignInBaseBean.getMessages().get(0).getMessage());
                    return;
                }
                //??????????????????
                SignInDialog signInDialog = new SignInDialog(
                        getContext(), dialog, getSignInBaseBean.getData().getResponse(), key, isSignIned);
                signInDialog.setDialogClickCallback(new SignInDialog.DialogClickCallback() {
                    @Override
                    public void signIn(Dialog dialog, boolean signSuccess, String sugerNum) {
                        dialog.dismiss();
                        //?????????????????????
                        if (signSuccess) {
                            showSignSuccessPop("+" + sugerNum);
                            //??????????????????
                            getMyPearlBean();
                        }
                    }

                    @Override
                    public void changeToSignIn(Dialog dialog) {
                        //??????????????????????????????
                    }
                });
                signInDialog.show();
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignInBase(Custom.APPID, key, httpListener);
    }

    //??????????????????
    public void getMyPearlBean() {
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<GetMyPearlBean> httpListener = new HttpListener<GetMyPearlBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("???????????????????????????" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetMyPearlBean getMyPearlBean) {
                ViseLog.e("???????????????????????????" + getMyPearlBean);
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

    //???????????????????????????
    private void invateUser() {
        String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<InvateUserBean> httpListener = new HttpListener<InvateUserBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("???????????????????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(InvateUserBean invateUserBean) {
                ViseLog.e("???????????????????????????????????????:" + invateUserBean);
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

    //----------------------------------------------????????????----------------------------------------
    //??????????????????
    private void getSignedInNum() {
        final String key = (String) SPUtil.get(getContext(), "key", "");
        HttpListener<GetSignedInNumBean> httpListener = new HttpListener<GetSignedInNumBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("????????????????????????:" + errorMsg.toString());
            }

            @Override
            public void onSuccess(GetSignedInNumBean getSignedInNumBean) {
                ViseLog.e("????????????????????????:" + getSignedInNumBean);
                if (getSignedInNumBean.getCode() == 500) {
                    ToastUtils.show(getSignedInNumBean.getMessages().get(0).getMessage());
                    return;
                }
                //????????????????????????
                boolean todaySignIn = getSignedInNumBean.getData().getResponse().isTodaySignIn();
                //??????????????????
                if (!todaySignIn) {
                    qiandao(key);
                }
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignedInNum(Custom.APPID, key, httpListener);
    }

    //??????
    private void qiandao(String key) {
        HttpListener<QiandaoBean> httpListener = new HttpListener<QiandaoBean>() {
            @Override
            public void onError(String errorMsg) {
                ViseLog.e("??????????????????:" + errorMsg);
            }

            @Override
            public void onSuccess(QiandaoBean qiandaoBean) {
                ViseLog.e("??????????????????:" + qiandaoBean);
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

    //???????????????????????????
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
