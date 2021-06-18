package com.aganyun.acode.entity;

/**
 * Created by 孟晨 on 2018/8/17.
 */

public class ShuffingBean {
    private String imgURl;
    private String linkUrl;

    public ShuffingBean(String imgURl, String linkUrl) {
        this.imgURl = imgURl;
        this.linkUrl = linkUrl;
    }

    public String getImgURl() {
        return imgURl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public void setImgURl(String imgURl) {
        this.imgURl = imgURl;
    }
}
