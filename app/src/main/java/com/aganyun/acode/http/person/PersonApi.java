package com.aganyun.acode.http.person;

import com.aganyun.acode.http.person.bean.ChangeMobileBean;
import com.aganyun.acode.http.person.bean.CheckImageCodeBean;
import com.aganyun.acode.http.person.bean.FindPwdBean;
import com.aganyun.acode.http.person.bean.LoginBean;
import com.aganyun.acode.http.person.bean.LogoutBean;
import com.aganyun.acode.http.person.bean.RegBean;
import com.aganyun.acode.http.person.bean.SendImageCodeBean;
import com.aganyun.acode.http.person.bean.SendSmsCodeBean;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public interface PersonApi {

    //FIXME 1.发送图片验证码
    @Headers("Cache-Control:public,max-age=43200")
    @POST("otenda/uc/sendImageCode")
    Observable<SendImageCodeBean> sendImageCode();

    //FIXME 2.发送短息验证码
    @Headers("Cache-Control:public,max-age=43200")
    @POST("otenda/uc/sendSmsCode")
    Observable<SendSmsCodeBean> sendSmsCode(@Query("appId") String appId,
                                            @Query("imgId") String imgId,
                                            @Query("imgValue") String imgValue,
                                            @Query("mobile") String mobile,
                                            @Query("type") String type);

    //FIXME 3.验证图片验证码
    @Headers("Cache-Control:public,max-age=43200")
    @POST("otenda/uc/checkImageCode")
    Observable<CheckImageCodeBean> checkImageCode(@Query("appId") String appId,
                                                  @Query("imgId") String imgId,
                                                  @Query("imgValue") String imgValue);

    //FIXME 4.登录
    @Headers("Cache-Control:public,max-age=43200")
    @POST("bihucj/uc/login")
    Observable<LoginBean> login(@Query("appId") String appId,
                                @Query("mobile") String mobile,
                                @Query("password") String password);


    //FIXME 5.注册
    @Headers("Cache-Control:public,max-age=43200")
    @POST("bihucj/uc/reg")
    Observable<RegBean> reg(@Query("appId") String appId,
                            @Query("avatar") String avatar,
                            @Query("code") String code,
                            @Query("mobile") String mobile,
                            @Query("nickname") String nickname,
                            @Query("openid") String openid,
                            @Query("password") String password);

    //FIXME 6.修改手机号
    @Headers("Cache-Control:public,max-age=43200")
    @POST("bihucj/uc/changeMobile")
    Observable<ChangeMobileBean> changeMobile(@Query("appId") String appId,
                                              @Query("code") String code,
                                              @Query("mobile") String mobile,
                                              @Query("password") String password,
                                              @Query("sessionKey") String sessionKey);


    //FIXME 7.退出
    @Headers("Cache-Control:public,max-age=43200")
    @POST("bihucj/uc/logout")
    Observable<LogoutBean> logout(@Query("appId") String appId,
                                  @Query("sessionKey") String sessionKey);


    //FIXME 8.找回密码
    @Headers("Cache-Control:public,max-age=43200")
    @POST("bihucj/uc/findPwd")
    Observable<FindPwdBean> findPwd(@Query("appId") String appId,
                                    @Query("code") String code,
                                    @Query("mobile") String mobile,
                                    @Query("password") String password);
}
