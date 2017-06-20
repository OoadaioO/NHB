package com.nhb.app.custom.ui.launch;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.base.NHBViewModel;
import com.nhb.app.custom.databinding.ActivitySplashBinding;
import com.nhb.app.custom.utils.SpCustom;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-25 15:18
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class SplashActivity extends BaseActivity<NHBViewModel, ActivitySplashBinding> {

    //启动页背景持续时间
    private final static int NHB_BACKGROUND_DELAY = 2000;
    //“广告图”持续时间
    private final static int AD_BACKGROUND_DELAY = 3000;

    //广告图转圈动画加载显示的定时器
    private TimeCount mTimeCounter;


    private static final int SleepTime = 2000;
    private Class<?> mShowClass;
    private int mDelayedTime;
    // 是否展示引导页
    private boolean mShowGuidePage;

    @Override
    protected NHBViewModel loadViewModel() {
        return new NHBViewModel();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialize() {
        setOnClickListener(viewDataBinding.splashBtPass, viewDataBinding.splashIvAd);
        //延迟处理
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpAd();
            }
        }, NHB_BACKGROUND_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 设置“广告”图
     */
    private void setUpAd() {
        //加载广告图
        viewDataBinding.splashBtPass.setVisibility(View.VISIBLE);
        viewDataBinding.splashIvAd.setVisibility(View.VISIBLE);

        //开始广告加载转圈定时器启动
        mTimeCounter = new TimeCount(AD_BACKGROUND_DELAY, 40);
        mTimeCounter.start();

        mShowGuidePage = SpCustom.isShowGuidePage();
        mShowClass = mShowGuidePage ? GuidePageActivity.class : MainActivity.class;
        mDelayedTime = SleepTime;

        gotoMainActivity();
    }

    private void gotoMainActivity() {
        new Handler().postDelayed(new AutoStart(new Intent().setClass(mContext, mShowClass)), mDelayedTime);
    }

    /**
     * 禁止返回键
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * 启动activity
     */
    private class AutoStart implements Runnable {
        private Intent intent;

        private AutoStart(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void run() {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            SpCustom.setShowGuidePage(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.splash_bt_pass:
                mDelayedTime = 0;
                gotoMainActivity();
                break;
            case R.id.splash_iv_ad:
                //TODO 点击广告图的跳转
//                mDelayedTime = 0;
//                gotoMainActivity();
                break;
            default:
                break;
        }
    }

    private class TimeCount extends CountDownTimer {


        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            viewDataBinding.splashBtPass.setProgress(viewDataBinding.splashBtPass.getProgress() + 1);
        }

        @Override
        public void onFinish() {
            viewDataBinding.splashBtPass.setProgress(viewDataBinding.splashBtPass.getMax());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeCounter != null) {
            mTimeCounter.cancel();
        }
    }
}
