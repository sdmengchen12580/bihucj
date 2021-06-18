package com.aganyun.acode.ymshare;

import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by 孟晨 on 2018/7/31.
 */

public class ShareUtils {

    //分享
    private static ShareAction shareAction;

    //FIXME 单图文到指定平台  withText   withMedia
    //FIXME 多图文withMedias(imagelocal,imagelocal)     withText
    public void share2platform_textImg(Activity activity,
                                       SHARE_MEDIA type,
                                       String content,
                                       String imgUrl,
                                       boolean needScale,
                                       String scaleWebImgUrl) {
        if (shareAction != null) {
            shareAction.setCallback(null);
            shareAction = null;
        }
        shareAction = new ShareAction(activity);
        shareAction.setPlatform(type)
                .withText(content)//文字描述
                .withMedia(getUMImageFromWeb(activity, imgUrl, needScale, scaleWebImgUrl))
                .setCallback(new UMShareCallback())//回调
                .share();
    }


    //FXIME 分享bitmap
    public void share2platform_bitmap(Activity activity,
                                      SHARE_MEDIA type,
                                      String content,
                                      Bitmap bitmap,
                                      boolean needScale,
                                      String scaleWebImgUrl) {
        if (shareAction != null) {
            shareAction.setCallback(null);
            shareAction = null;
        }
        shareAction = new ShareAction(activity);
        shareAction.setPlatform(type)
                .withText(content)//文字描述
                .withMedia(getUMImageFromBitmap(activity, bitmap, needScale, bitmap))
                .setCallback(new UMShareCallback())//回调
                .share();
    }


    //FIXME 分享链接-文字描述-内容-缩略图-h5地址
    public void share2platform_web(Activity activity,
                                   SHARE_MEDIA type,
                                   String h5Url,
                                   String title,
                                   String desception,
                                   String scaleWebImgUrl,
                                   boolean needTitle,
                                   boolean isWX_MustTitle) {
        if (shareAction != null) {
            shareAction.setCallback(null);
            shareAction = null;
        }
        shareAction = new ShareAction(activity);
        shareAction.setPlatform(type)
                .withMedia(getUMWeb(h5Url, title, activity, scaleWebImgUrl, desception, needTitle,isWX_MustTitle))
                .setCallback(new UMShareCallback())
                .share();
    }

    //------------------------------------------创建分享的图片----------------------------------------
    //网络图片(和本地的int的resouse文件)-兼容性更高------部分平台需要缩略图
    //用户设置的图片大小最好不要超过250k，缩略图不要超过18k
    private UMImage getUMImageFromWeb(Activity activity, String webImgUrl, boolean needScale, String scaleWebImgUrl) {
        UMImage image = new UMImage(activity, webImgUrl);
        if (needScale) {
            image.setThumb(new UMImage(activity, scaleWebImgUrl));
        }
        return image;
         /* image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
    image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
    压缩格式设置
    image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色*/
    }


    private UMImage getUMImageFromBitmap(Activity activity, Bitmap notScaleBitmap, boolean needScale, Bitmap bitmap) {
        UMImage image = new UMImage(activity, notScaleBitmap);
        if (needScale) {
            image.setThumb(new UMImage(activity, bitmap));
        }
        return image;
         /* image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
    image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
    压缩格式设置
    image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色*/
    }


    //------------------------------------------创建分享的网页链接----------------------------------
    //网页链接
    private UMWeb getUMWeb(String h5Address,
                           String title,
                           Activity activity,
                           String scaleImgUrl,
                           String desception,
                           boolean needTitle,
                           boolean isWX_MustTitle) {
        UMWeb web = new UMWeb(h5Address);
        if(!needTitle){
            //不需要标题 ->但是微信朋友圈必须要
            if(isWX_MustTitle){
                web.setTitle(title);//标题
            }else{
                web.setTitle("  ");//标题
            }
            web.setDescription(desception);//描述
            web.setThumb(getUMImageFromWeb(activity, scaleImgUrl, false, scaleImgUrl));  //缩略图
            return web;
        }
        web.setTitle(title);//标题
        web.setDescription(desception);//描述
        web.setThumb(getUMImageFromWeb(activity, scaleImgUrl, true, scaleImgUrl));  //缩略图
        return web;
    }


    //------------------------------------------单例------------------------------------------------
    private static class SingletonHolder {
        public static ShareUtils instance = new ShareUtils();
    }

    private ShareUtils() {
    }

    public static ShareUtils newInstance() {
        return SingletonHolder.instance;
    }

    //销毁
    public static void destoryUm(Activity activity) {
        UMShareAPI.get(activity).release();
    }

    //每次分享时候进入切换账户界面
    public static void changeUserWhenShare(Activity activity) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(activity).setShareConfig(config);
    }
}
