package com.aganyun.acode.http.other;

import org.lcsyjt.mylibrary.http.RetrofitManager;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class OtherLoader {

    private OtherApi otherApi;
    public static final String BASEURL = "http://acode.aganyun.com/cx.acode/api/bihucj/other/";

    public OtherLoader(String baseUrl) {
        otherApi = RetrofitManager.getApi(OtherApi.class, baseUrl);
    }

    // FIXME 1.关于我们
    private Subscription about;

    public Subscription about(String appId,
                              HttpListener httpListener) {
        about = otherApi.about(appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return about;
    }


    //FIXME 2.推荐好友
    private Subscription getShareUrl;

    public Subscription getShareUrl(String appId,
                                    String sessionKey,
                                    HttpListener httpListener) {
        getShareUrl = otherApi.getShareUrl(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getShareUrl;
    }

    //FIXME 3.邀请好友APP
    private Subscription invateUser;

    public Subscription invateUser(String appId,
                                    String sessionKey,
                                    HttpListener httpListener) {
        invateUser = otherApi.invateUser(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return invateUser;
    }

}
