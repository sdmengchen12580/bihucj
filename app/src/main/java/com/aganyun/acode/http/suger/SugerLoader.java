package com.aganyun.acode.http.suger;

import org.lcsyjt.mylibrary.http.RetrofitManager;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2018/8/29.
 */
public class SugerLoader {

    private SugerApi sugerApi;
    public static final String BASEURL = "http://acode.aganyun.com/cx.acode/api/bihucj/";

    public SugerLoader(String baseUrl) {
        sugerApi = RetrofitManager.getApi(SugerApi.class, baseUrl);
    }

    // FIXME 1.每天签到获得的糖果列表
    private Subscription getSignInBase;

    public Subscription getSignInBase(String appId,
                                      String sessionKey,
                                      HttpListener httpListener) {
        getSignInBase = sugerApi.getSignInBase(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getSignInBase;
    }


    //FIXME 2.用户已经签到次数

    private Subscription getSignedInNum;

    public Subscription getSignedInNum(String appId,
                                       String sessionKey,
                                       HttpListener httpListener) {
        getSignedInNum = sugerApi.getSignedInNum(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getSignedInNum;
    }


    //FIXME 3.签到
    private Subscription qiandao;

    public Subscription qiandao(String appId,
                                String sessionKey,
                                HttpListener httpListener) {
        qiandao = sugerApi.qiandao(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return qiandao;
    }


    //FIXME 4.分享获得糖果
    private Subscription getPearlByShareFlash;

    public Subscription getPearlByShareFlash(String appId,
                                             String sessionKey,
                                             HttpListener httpListener) {
        getPearlByShareFlash = sugerApi.getPearlByShareFlash(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getPearlByShareFlash;
    }

    //FIXME 5.阅读文章获取糖果
    private Subscription getPearlByReadArticle;

    public Subscription getPearlByReadArticle(String appId,
                                              String sessionKey,
                                              HttpListener httpListener) {
        getPearlByReadArticle = sugerApi.getPearlByReadArticle(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getPearlByReadArticle;
    }

    //FIXME 6.阅读文章获取糖果
    private Subscription getPearlByShareArticle;

    public Subscription getPearlByShareArticle(String appId,
                                               String sessionKey,
                                               HttpListener httpListener) {
        getPearlByShareArticle = sugerApi.getPearlByShareArticle(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getPearlByShareArticle;
    }

    //FIXME 7.糖果列表
    private Subscription getPearls;

    public Subscription getPearls(String appId,
                                  int isToday,
                                  int pageNo,
                                  int pageSize,
                                  String sessionKey,
                                  HttpListener httpListener) {
        getPearls = sugerApi.getPearls(appId, isToday, pageNo, pageSize, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getPearls;
    }

    //FIXME 8.我的糖果
    private Subscription getMyPearl;

    public Subscription getMyPearl(String appId,
                                   String sessionKey,
                                   HttpListener httpListener) {
        getMyPearl = sugerApi.getMyPearl(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getMyPearl;
    }
}
