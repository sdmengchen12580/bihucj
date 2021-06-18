package com.aganyun.acode.ymshare;

import android.util.Log;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by 孟晨 on 2018/8/1.
 */

public class UMShareCallback implements UMShareListener {

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("测试分享开始: ", share_media.toString());
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("测试分享成功: ", share_media.toString());
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("测试分享失败: ", "平台:" + share_media.toString() + "\n"
                + "原因:" + throwable);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("测试分享取消: ", share_media.toString());
    }
}
