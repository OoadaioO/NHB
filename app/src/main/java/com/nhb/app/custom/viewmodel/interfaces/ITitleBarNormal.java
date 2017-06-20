package com.nhb.app.custom.viewmodel.interfaces;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created by pengxiaofang on 19/5/16.
 * 正常的title bar
 */
public interface ITitleBarNormal {

	/**
	 * 返回按钮点击
	 * @param view
	 */
	void clickFinish(View view);

	/**
	 * 标题文案
	 * @return
	 */
	ObservableField<String> getTitle();
}
