package com.nhb.app.custom.viewmodel;

import android.databinding.ObservableBoolean;

import com.nhb.app.custom.base.NHBViewModel;

/**
 * ***********************************************************************
 * Author:pxfile
 * CreateData:2016-06-06 14:31
 * Version:1.0
 * Description:搜索结果ViewModel
 * ***********************************************************************
 */
public class CommonSearchResultVM extends NHBViewModel {

    //搜索的返回咨询,案例类型决定多类型TAB是否显示
    public ObservableBoolean multipleResultType = new ObservableBoolean();

}
