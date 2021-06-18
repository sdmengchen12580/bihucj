package com.aganyun.acode.http.content;

import org.lcsyjt.mylibrary.http.RetrofitManager;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class ContentLoader {
    private ContentApi contentApi;
    public static final String BASEURL = "http://acode.aganyun.com/cx.acode/api/bihucj/";

    public ContentLoader(String baseUrl) {
        contentApi = RetrofitManager.getApi(ContentApi.class, baseUrl);
    }

    // FIXME 1.新闻列表
    private Subscription getNews;

    public Subscription getNews(String appId,
                                boolean hasAdv,
                                int pageNo,
                                int pageSize,
                                String tTitleLike,
                                HttpListener httpListener) {
        getNews = contentApi.getNews(appId, hasAdv, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getNews;
    }

    // FIXME 2.获取快讯列表
    private Subscription getFlashs;

    public Subscription getFlashs(String appId,
                                  int pageNo,
                                  int pageSize,
                                  String tTitleLike,
                                  HttpListener httpListener) {
        getFlashs = contentApi.getFlashs(appId, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getFlashs;
    }

    // FIXME 3.政策列表
    private Subscription getPolicys;

    public Subscription getPolicys(String appId,
                                   int pageNo,
                                   int pageSize,
                                   String tTitleLike,
                                   HttpListener httpListener) {
        getPolicys = contentApi.getPolicys(appId, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getPolicys;
    }


    //FIXME 4.点击
    private Subscription addNum;

    public Subscription addNum(String appId,
                               long id,
                               HttpListener httpListener) {
        addNum = contentApi.addNum(appId, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return addNum;
    }


    //FIXME 5.行情分析
    private Subscription getQuotations;

    public Subscription getQuotations(String appId,
                                      int pageNo,
                                      int pageSize,
                                      String tTitleLike,
                                      HttpListener httpListener) {
        getQuotations = contentApi.getQuotations(appId, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getQuotations;
    }


    //FIXME 6.币虎专栏
    private Subscription getColumns;

    public Subscription getColumns(String appId,
                                   boolean hasAdv,
                                   int pageNo,
                                   int pageSize,
                                   String tTitleLike,
                                   HttpListener httpListener) {
        getColumns = contentApi.getColumns(appId, hasAdv, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getColumns;
    }

    //FIXME 7.号外
    private Subscription getExtraIds;

    public Subscription getExtraIds(String appId,
                                    int pageNo,
                                    int pageSize,
                                    String tTitleLike,
                                    HttpListener httpListener) {
        getExtraIds = contentApi.getExtraIds(appId, pageNo, pageSize, tTitleLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getExtraIds;
    }

}
