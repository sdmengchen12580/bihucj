package com.aganyun.acode.http.person;

import org.lcsyjt.mylibrary.http.RetrofitManager;
import org.lcsyjt.mylibrary.http.callback.HttpListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class PersonLoader {

    private PersonApi personApi;
    public static final String BASEURL = "http://acode.aganyun.com/cx.acode/api/";//有几个接口比较特殊，不要要bihucj

    public PersonLoader(String baseUrl) {
        personApi = RetrofitManager.getApi(PersonApi.class, baseUrl);
    }

    // FIXME 1.发送图片验证码
    private Subscription sendImageCode;

    public Subscription sendImageCode(HttpListener httpListener) {
        sendImageCode = personApi.sendImageCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return sendImageCode;
    }


    // FIXME 2.发送短息验证码
    private Subscription sendSmsCode;

    public Subscription sendSmsCode(String appId,
                                    String imgId,
                                    String imgValue,
                                    String mobile,
                                    String type,
                                    HttpListener httpListener) {
        sendSmsCode = personApi.sendSmsCode(appId, imgId, imgValue, mobile, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return sendSmsCode;
    }


    // FIXME 3.验证图片验证码
    private Subscription checkImageCode;

    public Subscription checkImageCode(String appId,
                                       String imgId,
                                       String imgValue,
                                       HttpListener httpListener) {
        checkImageCode = personApi.checkImageCode(appId, imgId, imgValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return checkImageCode;
    }


    //FIXME 4.登录
    private Subscription login;

    public Subscription login(String appId,
                              String mobile,
                              String password,
                              HttpListener httpListener) {
        login = personApi.login(appId, mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return login;
    }


    //FIXME 5.注册
    private Subscription reg;

    public Subscription reg(String appId,
                            String avatar,
                            String code,
                            String mobile,
                            String nickname,
                            String openid,
                            String password,
                            HttpListener httpListener) {
        reg = personApi.reg(appId, avatar, code, mobile, nickname, openid, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return reg;
    }


    //FIXME 6.修改手机号
    private Subscription changeMobile;

    public Subscription changeMobile(String appId,
                                     String code,
                                     String mobile,
                                     String password,
                                     String sessionKey,
                                     HttpListener httpListener) {
        changeMobile = personApi.changeMobile(appId, code, mobile, password, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return changeMobile;
    }


    //FIXME 7.退出
    private Subscription logout;

    public Subscription logout(String appId,
                               String sessionKey,
                               HttpListener httpListener) {
        logout = personApi.logout(appId, sessionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return logout;
    }


    //FIXME 8.找回密码

    private Subscription findPwd;

    public Subscription findPwd(String appId,
                                String code,
                                String mobile,
                                String password,
                                HttpListener httpListener) {
        findPwd = personApi.findPwd(appId, code, mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpListener);
        return findPwd;
    }


}
