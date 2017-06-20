package com.nhb.app.manager.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fast.library.utils.StatusBarUtils;
import com.fast.mvp.presenter.MvpPresenter;
import com.nhb.app.library.base.NHBActivity;
import com.nhb.app.library.utils.TitleBarUtils;
import com.nhb.app.manager.R;
import com.nhb.app.manager.dialog.LoadingDialog;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.push.PushUtils;
import com.nhb.app.manager.ui.activity.LoginActivity;

import de.greenrobot.event.EventBus;

/**
 * 说明：Activity基类
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 16:21
 * <p/>
 * 版本：verson 1.0
 */
public abstract class CommonActivity<Presenter extends MvpPresenter> extends NHBActivity<Presenter> {

    private TitleBarUtils mTitleBarUtils;
    private View titleView, userView;
    private LoadingDialog mLoadingDialog = null;

    public final static int NONE_TITLE = -1;

    @Override
    public int createLoaderID() {
        return hashCode();
    }

    @Override
    public void onInitCreate(Bundle bundle) {
        super.onInitCreate(bundle);
        //初始化个推
        PushUtils.init(this);
        if (setStatusBarColor() > 0) {
            StatusBarUtils.setColor(this, getResources().getColor(setStatusBarColor()),0);
        }
        if (isRegisterEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onInitStart() {
        if (setTitleBarResID() == 0) {
            TextView tvTitle = (TextView) titleView.findViewById(R.id.tv_title);
            initTitleBar(titleView,tvTitle);
        }else if (setTitleBarResID() > 0){
            initTitleBar(titleView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SpManager.isLogin()){
            if (!LoginActivity.class.getName().equals(this.getClass().getName())){
                showActivity(LoginActivity.class);
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @return
     */
    protected int setStatusBarColor() {
        return R.color.appColor;
    }

    /**
     * 设置顶部title的资源文件ID
     * 0：使用默认
     * setTitleBarResID() > 0：使用自定义
     * setTitleBarResID() < 0：不使用
     *
     * @return
     */
    protected int setTitleBarResID() {
        return 0;
    }

    /**
     * 设置标题栏
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        int titleBarResID = setTitleBarResID();
        if (titleBarResID == 0) {
            mTitleBarUtils = new TitleBarUtils(this, layoutResID, R.layout.default_title);
            userView = mTitleBarUtils.getUserView();
            titleView = mTitleBarUtils.getTitleBar();
            setContentView(userView);
        } else if (titleBarResID > 0) {
            mTitleBarUtils = new TitleBarUtils(this, layoutResID, titleBarResID);
            userView = mTitleBarUtils.getUserView();
            titleView = mTitleBarUtils.getTitleBar();
            setContentView(userView);
        } else {
            super.setContentView(layoutResID);
        }
    }

    /**
     * 默认标题栏
     *
     * @param titleView
     * @param tvTitle
     */
    protected void initTitleBar(View titleView,TextView tvTitle) {
        if (isShowBackIcon()){
            ImageView iv_back = bind(R.id.iv_back);
            iv_back.setVisibility(View.VISIBLE);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected boolean isShowBackIcon(){
        return false;
    }

    /**
     * 自定义标题栏
     *
     * @param titleView
     */
    protected void initTitleBar(View titleView) {}

    /****************************等待对话框**********************************/

    public void showLoading(){
        dismissLoading();
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }
        mLoadingDialog.show();
    }

    public void dismissLoading(){
        if (mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    /****************************等待对话框**********************************/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 注册EventBus
     * @return
     */
    protected boolean isRegisterEventBus(){
        return false;
    }

}
