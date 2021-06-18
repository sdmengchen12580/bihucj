package com.aganyun.acode.utils.versionupdate;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.utils.AppUtils;
import com.aganyun.acode.utils.stack.ViewManager;

/**
 * Created by 孟晨 on 2018/7/20.
 */

public class VerSionUpDateDialog extends Dialog implements View.OnClickListener {

    public VerSionUpDateDialog(@NonNull Context context) {
        super(context);
    }

    public VerSionUpDateDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected VerSionUpDateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    //控件
    private TextView tv_update_content;
    private Button bt_cancel, bt_true;
    //是否是必须更新
    private boolean isMustUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_update_dialog_layout);
        setCanceledOnTouchOutside(false);


        tv_update_content = findViewById(R.id.tv_update_content);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        bt_true = findViewById(R.id.bt_true);
        bt_true.setOnClickListener(this);
    }


    //---------------------------------------工具类---------------------------------------
    //关闭弹窗
    public void closeDialog() {
        if (isShowing()) {
            VerSionUpDateDialog.this.hide();
            VerSionUpDateDialog.this.dismiss();
        }
    }

    //设置是否是必须更新-更新的内容
    public void setIsMustUpdate(boolean isMustUpdate, String upDateContent) {
        this.isMustUpdate = isMustUpdate;
        tv_update_content.setText(upDateContent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //取消
            case R.id.bt_cancel:
                //必要更新
                if (isMustUpdate) {
                    closeDialog();
                    ViewManager.getInstance().exitApp(AppUtils.getAppContext());
                    return;
                }
                //非必要更新-关闭弹窗
                closeDialog();
                break;


            case R.id.bt_true:
                closeDialog();
                if (trueToUpdateCallback != null) {
                    trueToUpdateCallback.trueToUpdate();
                }
                break;
        }
    }

    //--------------------------------------接口--------------------------------------
    public interface TrueToUpdateCallback {
        void trueToUpdate();
    }

    private TrueToUpdateCallback trueToUpdateCallback;

    public void setTrueToUpdateCallback(TrueToUpdateCallback trueToUpdateCallback) {
        this.trueToUpdateCallback = trueToUpdateCallback;
    }
}
