package com.aganyun.acode.utils.versionupdate;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.aganyun.acode.ui.act.signup.SignUpActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 孟晨 on 2018/7/19.
 */

public class VersionLoadAsyncTask extends AsyncTask<String, Integer, File> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //第二阶段
    @Override
    protected File doInBackground(String... params) {
        //从可变参数的数组中拿到图片地址
        String newurl = params[0];
        final File file = new File(Environment.getExternalStorageDirectory() + "/Download/updata.apk");
        Log.e("测试更新: ", "开始下载，路径:" + file.getAbsolutePath().toString());
        try {
            URL url = new URL(newurl);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            //获取最大值
            int contentLen = conn.getContentLength();
            Log.e("测试更新: ", "下载总大小:" + contentLen);
            //流
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len = -1;
            long currentLen = -1;
            while ((len = is.read(buff)) != -1) {
                currentLen += len;
                fos.write(buff, 0, len);
                publishProgress((int) (currentLen * 100 / contentLen));
            }
            fos.flush();
            fos.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("测试更新: ", "下载异常:" + e.toString());
        }
        return file;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e("测试更新: ", "下载进度为:" + values[0]);
        if (downLoadCallBack != null) {
            downLoadCallBack.currentNum(values[0]);
        }
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        Log.e("测试更新: ", "下载完成,路径为:" + file.getAbsolutePath().toString());
        if (downLoadCallBack != null) {
            downLoadCallBack.apkLocation(file);
        }
    }


    //---------------------------------------进度回调---------------------------------------
    public interface DownLoadCallBack {
        void currentNum(int num);

        void apkLocation(File file);
    }

    private DownLoadCallBack downLoadCallBack;

    public void setDownLoadCallBack(DownLoadCallBack downLoadCallBack) {
        this.downLoadCallBack = downLoadCallBack;
    }
}
