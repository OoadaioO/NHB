package com.nhb.app.custom.viewmodel.interfaces;

import android.view.View;

/**
 * Created by pengxiaofang on 19/5/16.
 * 带有搜索框的title bar
 */
public interface ITitleBarSearchSimple {

    /**
     * 点击搜索位置
     *
     * @param view
     */
    void clickSearch(View view);

    /**
     * 返回按钮点击
     * @param view
     */
    void clickFinish(View view);
}
