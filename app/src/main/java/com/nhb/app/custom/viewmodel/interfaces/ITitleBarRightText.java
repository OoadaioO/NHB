package com.nhb.app.custom.viewmodel.interfaces;

import android.databinding.ObservableField;
import android.view.View;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-08 16:28
 * <p> Version: 1.0
 * <p> Description: 右上角带文字按钮的顶部栏(含返回按钮)
 * <p>
 * <p>***********************************************************************
 */
public interface ITitleBarRightText {

	/**
	 * 标题文案
	 * @return
	 */
	ObservableField<String> getTitle();

	/**
	 * 返回按钮点击
	 * @param view
	 */
	void clickFinish(View view);

	/**
	 * 返回右上角 文字按钮 的文字
	 * 若return null, 隐藏右上角文字按钮
	 * @return
	 */
	ObservableField<String> getRightText();

	/**
	 * 右上角 文字按钮点击
	 * @param view
	 */
	void clickRightTv(View view);
}
