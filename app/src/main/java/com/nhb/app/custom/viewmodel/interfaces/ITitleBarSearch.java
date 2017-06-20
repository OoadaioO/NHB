package com.nhb.app.custom.viewmodel.interfaces;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created by pengxiaofang on 19/5/16.
 * 带有搜索框的title bar
 */
public interface ITitleBarSearch {

    /**
     * 点击搜索位置
     *
     * @param view
     */
    void clickSearch(View view);

    /**
     * 点击消息位置
     *
     * @param view
     */
    void clickMsg(View view);

    /**
     * 点击选择城市
     *
     * @param view
     */
    void clickSelectCity(View view);

    /**
     * 地址文案
     *
     * @return
     */
    ObservableField<String> getLocationArea();

    /**
     * 搜索文案
     *
     * @return
     */
    ObservableField<String> getSearchContent();
}
