package com.nhb.app.custom.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.LogUtils;
import com.fast.library.utils.StatusBarUtils;
import com.fast.library.utils.UIUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.common.dialog.LoadingDialog;
import com.nhb.app.custom.constant.NHBConstant;

import butterknife.ButterKnife;

/**
 * @author pengxiaofang
 * @since 2015-12-24
 * <p/>
 * 说明：模板执行顺序setScreenOrientation、loadLayoutId、intentWithNormal/intentWithActionView、initialize
 * <ul>
 * <li>	setScreenOrientation():设置屏幕的方向，默认为垂直，子类可以重写此方法设置屏幕方向
 * <li> loadLayoutId():抽象方法，获取contentView，无需再写setContentView(int layoutResID)方法
 * <li>	intentWithNormal(Intent):主要用于解析显式Intent跳转传值
 * <li>	intentWithActionView(Uri):主要用于解析隐式Intent跳转传值，多用于h5协议跳转
 * <li>	initialize():抽象方法，用于数据、UI等初始化
 * <li>	toGetPageData(boolean):获取当前页面所需的数据，需要指定是否显示loading框，最好使用此方法获取页面数据
 * <li>	pendingTransitionEnter():startActivity时的动画，默认为右侧进入，子类可以重写此方法设置Activity进入动画
 * <li>	pendingTransitionExit():finish时的动画，默认为右侧退出，子类可以重写此方法设置Activity退出动画
 * <ul/>
 */
public abstract class NHBActivity extends FragmentActivity implements View.OnClickListener{

    private static long startedActivityCount = 0l;
    //Loading框
    public LoadingDialog mLoadingDialog = null;
    public Context mContext = null;
    //上游页面（Activity或Fragment）
    public String mReferrerPageClass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gmInit();
        //设置状态栏颜色
        StatusBarUtils.setColor(this, UIUtils.getColor(R.color.white),0);
    }


    /**
     * 初始化
     */
    protected void gmInit() {
        //设置当前Activity的屏幕方向
        setScreenOrientation();
        int layoutId = loadLayoutId();
        if (0 != layoutId) {
            setContentView(layoutId);
        }
        //加入注解
        ButterKnife.bind(this);
        //成员变量context
        mContext = this;
        //初始化Loading
        mLoadingDialog = new LoadingDialog(this);
        //输出当前Activity名称
        LogUtils.d("currentClass ===>> " + getClass().getSimpleName());
        //解析Intent
        parseIntent(getIntent());
        //初始化
        initialize();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivity(intent);
        pendingTransitionEnter();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivity(intent, options);
        pendingTransitionEnter();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivityForResult(intent, requestCode);
        pendingTransitionEnter();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivityForResult(intent, requestCode, options);
        pendingTransitionEnter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startedActivityCount++;
        if (1 == startedActivityCount) {
            appDidEnterForeground();
        }
    }

    /**
     * 应用回到前台
     */
    protected void appDidEnterForeground() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        startedActivityCount--;
        if (0 == startedActivityCount) {
            appDidEnterBackground();
        }
    }

    /**
     * 应用进入后台
     */
    protected void appDidEnterBackground() {

    }

    @Override
    public void finish() {
        super.finish();
        pendingTransitionExit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            return super.dispatchTouchEvent(event);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            //do nothing
        }
        return false;
    }

    /**
     * 解析Intent跳转方式
     *
     * @param intent
     */
    protected void parseIntent(Intent intent) {
        mReferrerPageClass = intent.getStringExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS);
        LogUtils.d(NHBConstant.LOG_TAG, "the referrer_page_class is: " + mReferrerPageClass);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri == null) {
                finish();
            } else {
                intentWithActionView(uri);
            }
        } else {
            intentWithNormal(intent);
        }
    }

    /**
     * 设置屏幕方向，默认为垂直
     * 子类可以重写此方法设置屏幕方向
     */
    protected void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 加载Layout，代替setContentView(int)
     *
     * @return
     * @see #setContentView(int)
     */
    @LayoutRes
    protected abstract int loadLayoutId();

    /**
     * 解析隐式Intent跳转传递的参数
     *
     * @param uri
     */
    protected void intentWithActionView(Uri uri) {
    }

    /**
     * 解析显式Intent跳转传递的参数
     *
     * @param intent
     */
    protected void intentWithNormal(Intent intent) {
    }

    /**
     * 初始化
     */
    protected abstract void initialize();

    /**
     * 获取数据
     */
    protected void toGetPageData(boolean showLoading) {
    }

    /**
     * 默认入栈Activity从右侧进入
     * 子类可以重写此方法改变补间动画
     */
    protected void pendingTransitionEnter() {
        transitionWithRightEnter();
    }

    /**
     * 默认出栈Activity从右侧退出
     * 子类可以重写此方法改变补间动画
     */
    protected void pendingTransitionExit() {
        transitionWithRightExit();
    }

    /**
     * enter
     * 从右侧进入
     * exit
     * 静止不动
     */
    protected void transitionWithRightEnter() {
        overridePendingTransition(R.anim.activity_enter_right, R.anim.activity_standby);
    }

    /**
     * enter
     * 静止不动
     * exit
     * 从右侧退出
     */
    protected void transitionWithRightExit() {
        overridePendingTransition(R.anim.activity_standby, R.anim.activity_exit_right);
    }

    /**
     * enter
     * 从底部进入
     * exit
     * 静止不动
     */
    protected void transitionWithBottomEnter() {
        overridePendingTransition(R.anim.activity_enter_bottom, R.anim.activity_standby);
    }

    /**
     * enter
     * 静止不动
     * exit
     * 从底部退出
     */
    protected void transitionWithBottomExit() {
        overridePendingTransition(R.anim.activity_standby, R.anim.activity_exit_bottom);
    }

    /**
     * 延迟关闭
     */
    public void finishDelayed() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 500);
    }

    /**
     * 延迟关闭
     *
     * @param timeMillis 延迟时间，单位为毫秒
     */
    public void finishDelayed(long timeMillis) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, timeMillis);
    }

    /**
     * replaceFragment
     *
     * @param layoutId
     * @param fragment
     * @param tag
     */
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag) {
        //增加页面来源
        try {
            Bundle bundle = fragment.getArguments();
            if (null == bundle) {
                bundle = new Bundle();
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
                fragment.setArguments(bundle);
            } else {
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, tag);
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * replaceFragment，可加入回退栈中
     *
     * @param layoutId
     * @param fragment
     * @param tag
     */
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag, String backStackName) {
        //增加页面来源
        try {
            Bundle bundle = fragment.getArguments();
            if (null == bundle) {
                bundle = new Bundle();
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
                fragment.setArguments(bundle);
            } else {
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, tag);
        transaction.addToBackStack(backStackName);
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * 显示Loading框
     */
    public void showLD() {
        try {
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭Loading框
     */
    public void dismissLD() {
        try {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading框是否显示
     *
     * @return
     */
    public boolean isDialogLoading() {
        if (null == mLoadingDialog) {
            return false;
        } else {
            return mLoadingDialog.isShowing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.get().cancelToast();
    }

    /**
     * 统一为各种view添加点击事件
     */
    protected void setOnClickListener(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
    }

    public void showToast(String msg){
        ToastUtil.get().shortToast(msg);
    }

    public void showToast(int msg){
        ToastUtil.get().shortToast(msg);
    }


}
