package com.nhb.app.custom.utils;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.fast.library.utils.DeviceUtils;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.location.LocationServiceutils;
import com.pgyersdk.crash.PgyCrashManager;

import cn.smssdk.SMSSDK;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-24 18:00
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class UtilsManager {
    private Context mContext;

    private static UtilsManager mInstance = new UtilsManager();

    public static UtilsManager getInstance() {
        return mInstance;
    }

    private UtilsManager() {
    }

    public void setApplicationContext(Context context) {
        mContext = context.getApplicationContext();
        SMSSDK.initSDK(mContext, Constants.LOGIN.APPKEY,Constants.LOGIN.APPSECRET,false);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(mContext);
        LocationServiceutils.getInstance().startBaiduLocationService(mContext);
        DeviceUtils.init(mContext);
        PgyCrashManager.register(this.getContext());
    }

    public Context getContext() {
        return mContext;
    }
}
