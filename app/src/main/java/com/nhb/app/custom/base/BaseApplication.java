package com.nhb.app.custom.base;

import android.content.Context;

import com.nhb.app.custom.utils.UtilsManager;


/**
 * Created by pengxiaofang on 22/3/16.
 */
public class BaseApplication extends NHBApplication {

    public static Context appContext;

    @Override
    public void onInit() {
        appContext = BaseApplication.this;
        UtilsManager.getInstance().setApplicationContext(appContext);
    }

}
