package com.nhb.app.custom.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Keep;
import android.view.WindowManager;

import com.nhb.app.custom.R;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-24 17:34
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
@Keep
public class LoadingDialog extends Dialog {


    public LoadingDialog(Context context) {
        this(context, R.style.dialog_share);
        initialize();
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    private void initialize() {
        setContentView(R.layout.nhb_dialog_loading);
    }
    /**
     * 设置Loading为系统级别的
     * add by pengxiaofang since 3.9.7
     * 解决微信sdk无loading导致页面假死现象
     * 需要注册<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />权限
     */
    public void setWindowTypeSystem(){
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }

}

