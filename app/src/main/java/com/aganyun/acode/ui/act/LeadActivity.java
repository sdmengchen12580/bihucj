package com.aganyun.acode.ui.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aganyun.acode.R;
import com.aganyun.acode.adapter.NativeImgPagerAdapter;
import com.aganyun.acode.callback.MyPagerSelectAdapter;
import com.aganyun.acode.utils.AllUtils;
import com.aganyun.acode.utils.ChangeActUtils;
import com.aganyun.acode.utils.SPUtil;
import com.aganyun.acode.utils.ViewUtils;
import com.aganyun.acode.utils.WindowParamUtils;
import com.aganyun.acode.utils.vpchange.MyViewPager;
import com.aganyun.acode.view.SmallCircleView;

import java.lang.ref.WeakReference;

public class LeadActivity extends AppCompatActivity {

    private MyViewPager vpLead;
    private SmallCircleView circle1;
    private SmallCircleView circle2;
    private SmallCircleView circle3;
    private LinearLayout llPoint;
    private Button btJump;
    private int imgUrls[] = {R.drawable.img_lead1, R.drawable.img_lead2, R.drawable.img_lead3};
    private int timerTime = 2000;
    private MyHandler myHandler;
    public static final int msgCode = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllUtils.fullScreen(LeadActivity.this);
        setContentView(R.layout.activity_lead);
        onViewClicked();
        int windowHeight = WindowParamUtils.screenHeight(LeadActivity.this);
        //轮播的点击监听
        vpLead.setAdapter(new NativeImgPagerAdapter(imgUrls,
                LeadActivity.this, windowHeight, new NativeImgPagerAdapter.ClickImgCallback() {
            @Override
            public void clickImg(String webUrl) {

            }

            @Override
            public void clickNowUse() {
                if (myHandler != null) {
                    myHandler.removeMessages(msgCode);
                }
                ChangeActUtils.changeActivity(LeadActivity.this, HomeActivity.class);
                LeadActivity.this.finish();
            }
        }));
        //动态间距
        ViewUtils.setMargins(llPoint, 0, (int) (windowHeight * 0.24), 0, 0);
        //轮播的切换监听
        vpLead.addOnPageChangeListener(new MyPagerSelectAdapter() {
            @Override
            public void whichSelected(int position) {
                if (myHandler != null) {
                    myHandler.removeMessages(msgCode);
                }
                pointChangeWithVp(position);
                myHandler.sendEmptyMessageDelayed(msgCode, timerTime);
            }
        });
        myHandler = new MyHandler(LeadActivity.this);
        myHandler.sendEmptyMessageDelayed(msgCode, timerTime);
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        if (myHandler != null) {
            myHandler.removeMessages(msgCode);
        }
//        MobclickAgent.onPause(this);
    }

    //-------------------------------------------工具类---------------------------------------------
    private static class MyHandler extends Handler//创建静态内部类
    {
        //持有弱引用MainActivity,GC回收时会被回收掉.
        private final WeakReference<LeadActivity> mAct;

        public MyHandler(LeadActivity mainActivity) {
            mAct = new WeakReference<LeadActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            LeadActivity mainAct = mAct.get();
            super.handleMessage(msg);
            if (mainAct != null && msg.what == msgCode) {
                switch (mainAct.vpLead.getCurrentItem()) {
                    case 0:
                    case 1:
                        mainAct.vpLead.setCurrentItem(mainAct.vpLead.getCurrentItem() + 1, true);
                        mainAct.myHandler.sendEmptyMessageDelayed(msgCode, mainAct.timerTime);
                        break;
                    case 2:
                        ChangeActUtils.changeActivity(mainAct, HomeActivity.class);
                        mainAct.finish();
                        break;
                }
            }
        }
    }

    private void pointChangeWithVp(int position) //点随vp的变换
    {
        switch (position) {
            case 0:
                ViewUtils.showView(llPoint);
                ViewUtils.showView(btJump);
                circle1.setPaintColor(1);
                circle2.setPaintColor(2);
                circle3.setPaintColor(2);
                break;
            case 1:
                ViewUtils.showView(llPoint);
                ViewUtils.showView(btJump);
                circle1.setPaintColor(2);
                circle2.setPaintColor(1);
                circle3.setPaintColor(2);
                break;
            case 2:
                ViewUtils.invisibleView(llPoint);
                ViewUtils.invisibleView(btJump);
                circle1.setPaintColor(2);
                circle2.setPaintColor(2);
                circle3.setPaintColor(1);
                break;
        }
    }

    public void onViewClicked() {
        vpLead = findViewById(R.id.vp_lead);
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        llPoint = findViewById(R.id.ll_point);
        btJump = findViewById(R.id.bt_jump);
        findViewById(R.id.bt_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myHandler != null) {
                    myHandler.removeMessages(msgCode);
                }
                ChangeActUtils.changeActivity(LeadActivity.this, HomeActivity.class);
                LeadActivity.this.finish();
            }
        });
    }

    private boolean isFirst() {
        String isFirst = (String) SPUtil.get(LeadActivity.this, "isfirst", "-1");
        if (!isFirst.equals("-1")) {
            ChangeActUtils.changeActivity(LeadActivity.this, HomeActivity.class);
            LeadActivity.this.finish();
            return true;
        }
        return false;
    }
}
