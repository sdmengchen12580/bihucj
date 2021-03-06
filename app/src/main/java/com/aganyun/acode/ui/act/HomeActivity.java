package com.aganyun.acode.ui.act;

import static com.aganyun.acode.R.style.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.aganyun.acode.R;
import com.aganyun.acode.callback.MyOnTabSelectListener;
import com.aganyun.acode.config.Custom;
import com.aganyun.acode.entity.TabEntity;
import com.aganyun.acode.http.suger.SugerLoader;
import com.aganyun.acode.http.suger.bean.GetSignInBaseBean;
import com.aganyun.acode.http.suger.bean.GetSignedInNumBean;
import com.aganyun.acode.ui.act.signin.SignInActivity;
import com.aganyun.acode.ui.base.BaseActivity;
import com.aganyun.acode.ui.fra.home.HomeFragment;
import com.aganyun.acode.ui.fra.myself.MySelfFragment;
import com.aganyun.acode.ui.fra.quicknews.QuickNewsFragment;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.PermissionsUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.WindowParamUtils;
import com.aganyun.acode.utils.mtoast.ToastUtils;
import com.aganyun.acode.utils.stack.ViewManager;
import com.aganyun.acode.utils.user.UserUtils;
import com.aganyun.acode.view.ViewPagerSlide;
import com.aganyun.acode.view.signin.SignInDialog;
import com.aganyun.acode.view.signin.SignInSuccessPop;
import com.aganyun.acode.ymshare.ShareUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.busbean.TabSelectMyBus;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.umeng.socialize.UMShareAPI;
import com.vise.log.ViseLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.lcsyjt.mylibrary.http.callback.HttpListener;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private ViewPagerSlide vpContainer;
    private CommonTabLayout tabBottomTitle;
    private long exitTime = 0;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private String[] mTitles = {"??????", "??????", "??????"};//"????????????",
    private int[] mIconSelectIds = {
            R.drawable.homeact_home_img_selected,
            R.drawable.homeact_quicknews_img_selected,// R.drawable.img_now_details_selected,
            R.drawable.homeact_my_img_selected};
    private int[] mIconUnselectIds = {
            R.drawable.homeact_home_img_unselected,
            R.drawable.homeact_quicknews_img_unselected,//R.drawable.img_now_details,
            R.drawable.homeact_myself_img_unselected};
    //?????????????????????
    private boolean isFirst = true;
    private PopupWindow popSignSuccess;
    private SignInSuccessPop signInSuccessPop;

    @Override
    protected int getViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        vpContainer = findViewById(R.id.vp_container);
        tabBottomTitle = findViewById(R.id.tab_bottom_title);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //??????????????????
//        MobclickAgent.enableEncrypt(true);
        //um????????????????????????
        ShareUtils.newInstance().changeUserWhenShare(HomeActivity.this);
        //eventbus??????????????????
        EventBus.getDefault().register(HomeActivity.this);
        //??????????????????
        WindowParamUtils.saveWindowParams2Sp(HomeActivity.this);
        //??????
        initVpContainer();
    }

    //???????????????
    private void initVpContainer() {
        fragments.add(HomeFragment.newInstance());
        fragments.add(QuickNewsFragment.newInstance());
//        fragments.add(NowDetailsFragment.newInstance());//??????????????????
        fragments.add(MySelfFragment.newInstance());
        vpContainer.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });
        //title
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabBottomTitle.setTabData(mTabEntities);
        //??????????????????????????????
        tabBottomTitle.setBooleanNeedJudge(true);
        //??????
        tabBottomTitle.setOnTabSelectListener(new MyOnTabSelectListener() {
            @Override
            public void whichSelected(int position) {
                vpContainer.setCurrentItem(position);
            }
        });
        vpContainer.setOffscreenPageLimit(fragments.size());
        tabBottomTitle.setCurrentTab(1);
        vpContainer.setCurrentItem(1);
    }

    //?????????????????????
    private boolean judgeIsLoginStatue() {
        if (UserUtils.hasNotLoginIn()) {
            ChangeActUtils.changeActivity(HomeActivity.this, SignInActivity.class);
            return true;
        }
        return false;
    }

    /*?????????????????????*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //?????????????????????????????????
            if (signInSuccessPop != null) {
                if (popSignSuccess != null) {
                    if (popSignSuccess.isShowing()) {
                        signInSuccessPop.dismissPop();
                        signInSuccessPop = null;
                        popSignSuccess = null;
                        return true;
                    }
                }
            }
            if (((QuickNewsFragment) fragments.get(1)).popupWindow != null) {
                if (((QuickNewsFragment) fragments.get(1)).popupWindow.isShowing()) {
                    ((QuickNewsFragment) fragments.get(1)).dissmissPop();
                    return true;
                }
            }
            //????????????????????????????????????
            if (((QuickNewsFragment) fragments.get(1)).popSignSuccess != null) {
                if (((QuickNewsFragment) fragments.get(1)).popSignSuccess.isShowing()) {
                    ((QuickNewsFragment) fragments.get(1)).dissmissPop();
                    return true;
                }
            }
            //??????????????????????????????
            if (((MySelfFragment) fragments.get(2)).popupWindow != null) {
                if (((MySelfFragment) fragments.get(2)).popupWindow.isShowing()) {
                    ((MySelfFragment) fragments.get(2)).dissmissPop();
                    return true;
                }
            }
            //????????????????????????????????????
            if (((MySelfFragment) fragments.get(2)).popSignSuccess != null) {
                if (((MySelfFragment) fragments.get(2)).popSignSuccess.isShowing()) {
                    ((MySelfFragment) fragments.get(2)).dissmissPop();
                    return true;
                }
            }
            //??????????????????????????????
            if (((MySelfFragment) fragments.get(2)).webPopupWindow != null) {
                if (((MySelfFragment) fragments.get(2)).webPopupWindow.isShowing()) {
                    ((MySelfFragment) fragments.get(2)).dissmissPop();
                    return true;
                }
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show("????????????????????????");
                exitTime = System.currentTimeMillis();
                Log.e("????????????: ", "????????????????????????");
                return true;
            }
            ViewManager.getInstance().exitApp(HomeActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //????????????
    public void backToHomePage() {
        vpContainer.setCurrentItem(0);
        tabBottomTitle.setCurrentTab(0);
//        toWhiteStatuesBarColor();
    }

    //??????????????????
    public void backToMyPage() {
        vpContainer.setCurrentItem(2);
        tabBottomTitle.setCurrentTab(2);
//        toWhiteStatuesBarColor();
    }

    //--------------------------------------eventbus------------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabSelectMyEvent(TabSelectMyBus tabSelectMyBus) {
        if (tabSelectMyBus.isSelectMy()) {
            //?????????????????????
            if (judgeIsLoginStatue()) {
                return;
            }
            vpContainer.setCurrentItem(2);
            tabBottomTitle.setCurrentTab(2);
            //??????????????????????????????
            ((MySelfFragment) fragments.get(2)).getMyPearlBean();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(HomeActivity.this);
        //??????UM
        ShareUtils.newInstance().destoryUm(HomeActivity.this);
    }

    //--------------------------------------????????????------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("????????????: ", "??????");
        switch (requestCode) {
            //????????????
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("????????????: ", "??????????????????????????????");
                    ((QuickNewsFragment) fragments.get(1)).showSuggestFriendDialog();
                    return;
                }
                ToastUtils.show("??????????????????????????????????????????????????????!");
                PermissionsUtils.toAppSetting(HomeActivity.this);
                break;
            //????????????
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("????????????: ", "??????????????????????????????");
                    ((MySelfFragment) fragments.get(2)).showSuggestFriendDialog();
                    return;
                }
                ToastUtils.show("??????????????????????????????????????????????????????!");
                PermissionsUtils.toAppSetting(HomeActivity.this);
                break;
        }
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    //-------------------------------------------??????---------------------------------------------
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            //?????????????????????????????????  -> ??????
            if (!UserUtils.hasNotLoginIn()) {
                getSignedInNum();
            }
            //?????????->??????  ????????????????????? ->????????????
            else {
                getSignInBase(false);
            }
        }
    }

    //???????????????????????????
    public void showSignSuccessPop(String sugerNum) {
        signInSuccessPop = new SignInSuccessPop(HomeActivity.this);
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

    //??????????????????
    private void getSignedInNum() {
        String key = (String) SPUtil.get(HomeActivity.this, "key", "");
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
                //?????????????????????
                if (todaySignIn) {
                    return;
                }
                getSignInBase(true);
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignedInNum(Custom.APPID, key, httpListener);
    }

    //?????????????????????????????????
    private void getSignInBase(final boolean isSignIned) {
        final String key = (String) SPUtil.get(HomeActivity.this, "key", "");
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
                        HomeActivity.this, dialog, getSignInBaseBean.getData().getResponse(), key, isSignIned);
                signInDialog.setDialogClickCallback(new SignInDialog.DialogClickCallback() {
                    @Override
                    public void signIn(Dialog dialog, boolean signSuccess, String sugerNum) {
                        dialog.dismiss();
                        //?????????????????????
                        if (signSuccess) {
                            showSignSuccessPop("+" + sugerNum);
                        }
                    }

                    @Override
                    public void changeToSignIn(Dialog dialog) {
                        dialog.dismiss();
                        ChangeActUtils.changeToSignInActivity(HomeActivity.this, SignInActivity.class, true);
                    }
                });
                signInDialog.show();
            }
        };
        SugerLoader sugerLoader = new SugerLoader(SugerLoader.BASEURL);
        sugerLoader.getSignInBase(Custom.APPID, key, httpListener);
    }
}
