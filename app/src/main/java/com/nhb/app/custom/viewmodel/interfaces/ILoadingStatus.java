package com.nhb.app.custom.viewmodel.interfaces;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by pengxiaofang on 20/5/16.
 * {@link# R.layout.layout_loading_status}
 */
public interface ILoadingStatus {

	void onClickReload(View view);

	/*
	 * 加载数据为空的文案
	 */
	ObservableField<String> getEmptyText();

	/*
	 * 加载数据失败的文案
	 */
	ObservableField<String> getFailureText();

	/*
	 * 数据请求状态
	 * 0 加载中
	 * 1 加载成功
	 * 2 加载数据为空
	 * 3 加载失败
	 */
	ObservableInt getResultCode();

}
