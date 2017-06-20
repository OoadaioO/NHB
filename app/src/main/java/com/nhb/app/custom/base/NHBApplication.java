package com.nhb.app.custom.base;

import android.app.Application;

import com.fast.library.FastFrame;
import com.nhb.app.library.config.NHBCrashHandler;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-24 18:16
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public abstract class NHBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFastFrame();
        //错误日志处理
        initCrashHandler();
        onInit();
    }

    public abstract void onInit();

    private void initFastFrame() {
        FastFrame.init(this);
    }

    /**
     * 初始化错误日志处理
     */
    private void initCrashHandler() {
        NHBCrashHandler.getInstance().init();
    }

}
