package com.aganyun.acode.entity.quicknewsbus;

/**
 * Created by 孟晨 on 2018/7/18.
 */

public class ClickShareBus {

    //1=网页点击  2=分享
    private int type;
    private String webUrl;
    private String code;
    private String shareContent;
    private String time;
    private String title;

    public ClickShareBus(int type, String webUrl, String code, String shareContent, String time, String title) {
        this.type = type;
        this.webUrl = webUrl;
        this.code = code;
        this.shareContent = shareContent;
        this.time = time;
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
