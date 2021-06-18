package com.aganyun.acode.http.other;

import com.aganyun.acode.http.other.bean.AboutBean;
import com.aganyun.acode.http.other.bean.GetShareUrlBean;
import com.aganyun.acode.http.other.bean.InvateUserBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public interface OtherApi {

    //FIXME 1.关于我们
    @Headers("Cache-Control:public,max-age=43200")
    @POST("about")
    Observable<AboutBean> about(@Query("appId") String appId);

    //FIXME 2.推荐好友
    @Headers("Cache-Control:public,max-age=43200")
    @POST("getShareUrl")
    Observable<GetShareUrlBean> getShareUrl(@Query("appId") String appId,
                                            @Query("sessionKey") String sessionKey);

    //FIXME 3.邀请好友APP
    @Headers("Cache-Control:public,max-age=43200")
    @POST("invateUser")
    Observable<InvateUserBean> invateUser(@Query("appId") String appId,
                                          @Query("sessionKey") String sessionKey);
}
