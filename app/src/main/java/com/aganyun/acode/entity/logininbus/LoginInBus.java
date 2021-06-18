package com.aganyun.acode.entity.logininbus;

/**
 * Created by 孟晨 on 2018/7/29.
 */

public class LoginInBus {
    // FIXME 0为登录成功  1为退出登录 2更新用户头像 3.更新余额和点的数字
    private int loginCode = -1;

    private boolean needSignIn = false;

    public boolean isNeedSignIn() {
        return needSignIn;
    }

    public void setNeedSignIn(boolean needSignIn) {
        this.needSignIn = needSignIn;
    }

    public LoginInBus(int loginCode) {
        this.loginCode = loginCode;
    }

    public int getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(int loginCode) {
        this.loginCode = loginCode;
    }
}
