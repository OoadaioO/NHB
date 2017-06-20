package com.nhb.app.library;

import android.app.Application;

import com.fast.library.FastFrame;
import com.nhb.app.library.config.NHBCrashHandler;

/**
 * 说明：NHBApplication
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 18:25
 * <p/>
 * 版本：verson 1.0
 */
public abstract class NHBApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initFastFrame();
        //错误日志处理
        initCrashHandler();
        onInit();
    }

    public abstract void onInit();

    private void initFastFrame(){
        FastFrame.init(this,true);
    }

    /**
     * 初始化错误日志处理
     */
    private void initCrashHandler(){
        NHBCrashHandler.getInstance().init();
    }

}
