package com.aganyun.acode.http.content;

import com.aganyun.acode.http.content.bean.AddNumBean;
import com.aganyun.acode.http.content.bean.GetColumnsBean;
import com.aganyun.acode.http.content.bean.GetExtraIdsBean;
import com.aganyun.acode.http.content.bean.GetFlashsBean;
import com.aganyun.acode.http.content.bean.GetNewsBean;
import com.aganyun.acode.http.content.bean.GetPolicysBean;
import com.aganyun.acode.http.content.bean.GetQuotationsBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public interface ContentApi {

    //FIXME 1.新闻列表
    @Headers("Cache-Control:public,max-age=43200")
    @POST("index/getNews")
    Observable<GetNewsBean> getNews(@Query("appId") String appId,
                                    @Query("hasAdv") boolean hasAdv,
                                    @Query("pageNo") int pageNo,
                                    @Query("pageSize") int pageSize,
                                    @Query("tTitleLike") String tTitleLike);


    //FIXME 2.获取快讯列表
    @Headers("Cache-Control:public,max-age=43200")
    @POST("flash/getFlashs")
    Observable<GetFlashsBean> getFlashs(@Query("appId") String appId,
                                        @Query("pageNo") int pageNo,
                                        @Query("pageSize") int pageSize,
                                        @Query("tTitleLike") String tTitleLike);


    //FIXME 3.政策列表
    @Headers("Cache-Control:public,max-age=43200")
    @POST("policy/getPolicys")
    Observable<GetPolicysBean> getPolicys(@Query("appId") String appId,
                                          @Query("pageNo") int pageNo,
                                          @Query("pageSize") int pageSize,
                                          @Query("tTitleLike") String tTitleLike);


    //FIXME 4.点击
    @Headers("Cache-Control:public,max-age=43200")
    @POST("other/addNum")
    Observable<AddNumBean> addNum(@Query("appId") String appId,
                                  @Query("id") long id);


    //FIXME 5.行情分析
    @Headers("Cache-Control:public,max-age=43200")
    @POST("Quotation/getQuotations")
    Observable<GetQuotationsBean> getQuotations(@Query("appId") String appId,
                                                @Query("pageNo") int pageNo,
                                                @Query("pageSize") int pageSize,
                                                @Query("tTitleLike") String tTitleLike);


    //FIXME 6.币虎专栏
    @Headers("Cache-Control:public,max-age=43200")
    @POST("column/getColumns")
    Observable<GetColumnsBean> getColumns(@Query("appId") String appId,
                                          @Query("hasAdv") boolean hasAdv,
                                          @Query("pageNo") int pageNo,
                                          @Query("pageSize") int pageSize,
                                          @Query("tTitleLike") String tTitleLike);

    //FIXME 7.号外
    @Headers("Cache-Control:public,max-age=43200")
    @POST("extra/getExtraIds")
    Observable<GetExtraIdsBean> getExtraIds(@Query("appId") String appId,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize,
                                            @Query("tTitleLike") String tTitleLike);
}
