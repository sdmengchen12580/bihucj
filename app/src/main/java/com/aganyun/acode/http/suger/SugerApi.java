package com.aganyun.acode.http.suger;

import com.aganyun.acode.http.suger.bean.GetMyPearlBean;
import com.aganyun.acode.http.suger.bean.GetPearlByReadArticleBean;
import com.aganyun.acode.http.suger.bean.GetPearlByShareFlashBean;
import com.aganyun.acode.http.suger.bean.GetPearlBySharegetPearlByShareArticleBean;
import com.aganyun.acode.http.suger.bean.GetPearlsBean;
import com.aganyun.acode.http.suger.bean.GetSignInBaseBean;
import com.aganyun.acode.http.suger.bean.GetSignedInNumBean;
import com.aganyun.acode.http.suger.bean.QiandaoBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2018/8/29.
 */

public interface SugerApi {

    //FIXME 1.每天签到获得的糖果列表
    @Headers("Cache-Control:public,max-age=43200")
    @POST("signIn/getSignInBase")
    Observable<GetSignInBaseBean> getSignInBase(@Query("appId") String appId,
                                                @Query("sessionKey") String sessionKey);

    //FIXME 2.用户已经签到次数
    @Headers("Cache-Control:public,max-age=43200")
    @POST("signIn/getSignedInNum")
    Observable<GetSignedInNumBean> getSignedInNum(@Query("appId") String appId,
                                                  @Query("sessionKey") String sessionKey);

    //FIXME 3.签到
    @Headers("Cache-Control:public,max-age=43200")
    @POST("signIn/qiandao")
    Observable<QiandaoBean> qiandao(@Query("appId") String appId,
                                    @Query("sessionKey") String sessionKey);

    //FIXME 4.分享获得糖果
    @Headers("Cache-Control:public,max-age=43200")
    @POST("pearl/getPearlByShareFlash")
    Observable<GetPearlByShareFlashBean> getPearlByShareFlash(@Query("appId") String appId,
                                                              @Query("sessionKey") String sessionKey);

    //FIXME 5.阅读文章获取糖果
    @Headers("Cache-Control:public,max-age=43200")
    @POST("pearl/getPearlByReadArticle")
    Observable<GetPearlByReadArticleBean> getPearlByReadArticle(@Query("appId") String appId,
                                                                @Query("sessionKey") String sessionKey);

    //FIXME 6.阅读文章获取糖果
    @Headers("Cache-Control:public,max-age=43200")
    @POST("pearl/getPearlByShareArticle")
    Observable<GetPearlBySharegetPearlByShareArticleBean> getPearlByShareArticle(@Query("appId") String appId,
                                                                                 @Query("sessionKey") String sessionKey);

    //FIXME 7.糖果列表
    @Headers("Cache-Control:public,max-age=43200")
    @POST("pearl/getPearls")
    Observable<GetPearlsBean> getPearls(@Query("appId") String appId,
                                        @Query("isToday") int isToday,
                                        @Query("pageNo") int pageNo,
                                        @Query("pageSize") int pageSize,
                                        @Query("sessionKey") String sessionKey);


    //FIXME 8.我的糖果
    @Headers("Cache-Control:public,max-age=43200")
    @POST("pearl/getMyPearl")
    Observable<GetMyPearlBean> getMyPearl(@Query("appId") String appId,
                                          @Query("sessionKey") String sessionKey);


}
