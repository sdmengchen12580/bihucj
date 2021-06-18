package com.aganyun.acode.http.details;

import com.aganyun.acode.http.details.bean.GetTickerDetailsBean;
import com.aganyun.acode.http.details.bean.InitBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2018/9/13.
 */

public interface DetailsApi {

    //FIXME 1.行情初始化
    @Headers("Cache-Control:public,max-age=43200")
    @POST("init")
    Observable<InitBean> init();


    //FIXME 2.行情详情
    @Headers("Cache-Control:public,max-age=43200")
    @POST("getTickerDetails")
    Observable<GetTickerDetailsBean> getTickerDetails(@Query("appId") String appId,
                                                      @Query("base") String base,
                                                      @Query("pageNo") int pageNo,
                                                      @Query("pageSize") int pageSize);
}
