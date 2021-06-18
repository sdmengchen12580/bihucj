package com.aganyun.acode.http.details;

import org.lcsyjt.mylibrary.http.RetrofitManager;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2018/9/13.
 */

public class DetailsLoader {

    private DetailsApi detailsApi;
    public static final String BASEURL = "http://acode.aganyun.com/cx.acode/api/bihucj/digiccy/";

    public DetailsLoader(String baseUrl) {
        detailsApi = RetrofitManager.getApi(DetailsApi.class, baseUrl);
    }

    // FIXME 1.行情初始化
    private Subscription init;

    public Subscription init(HttpListener httpListener) {
        init = detailsApi.init()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return init;
    }

    // FIXME 2.行情详情
    private Subscription getTickerDetails;

    public Subscription getTickerDetails(String appId,
                                         String base,
                                         int pageNo,
                                         int pageSize,
                                         HttpListener httpListener) {
        getTickerDetails = detailsApi.getTickerDetails(appId, base, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return getTickerDetails;
    }


}
