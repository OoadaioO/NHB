package com.nhb.app.custom.viewmodel.interfaces;

import android.databinding.ObservableField;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-08 16:10
 * <p> Version: 1.0
 * <p> Description: 只含有title的顶部栏
 * <p>
 * <p>***********************************************************************
 */

public interface ITitleBarSimple {
	/**
	 * 标题文案
	 * @return
	 */
	ObservableField<String> getTitle();
}
