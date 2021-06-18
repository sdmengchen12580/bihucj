package com.aganyun.acode;

import android.app.Application;
import com.aganyun.acode.config.Custom;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import cn.jpush.android.api.JPushInterface;

public class AppC extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //友盟分享
        UMConfigure.init(this, Custom.YM_SHARE_APPKEY, "yingyongbao", UMConfigure.DEVICE_TYPE_PHONE, "");
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        //qq
        PlatformConfig.setQQZone(Custom.QQ_APPKEY, Custom.QQ_APPSECRET);
        //微信平台的key
        PlatformConfig.setWeixin(Custom.WX_APPKEY, Custom.WX_APPSECRET);
        //新浪微博
        PlatformConfig.setSinaWeibo(Custom.XL_APPKEY, Custom.XL_APPSECRET, Custom.XL_URL);
        //友盟统计,
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        String channelId = null;
//        try { V
//                    getPackageManager().
//                    getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//            if (applicationInfo != null) {
//                if (applicationInfo.metaData != null) {
//                    channelId = applicationInfo.metaData.getString("UMENG_CHANNEL");
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),"appkey",channelId));
    }
}
