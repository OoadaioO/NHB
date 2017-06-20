package com.nhb.app.custom.domain;

import android.content.Intent;

import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.ui.personal.LoginActivity;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-07-13 11:16
 * <p> Version: 1.0
 * <p> Description: 全局http error拦截器
 * <p>
 * <p>***********************************************************************
 */

public class HttpErrorInterceptor {

	/**
	 * http error
	 * @param requestCode 请求的标识码
	 * @param errorCode	错误码
	 * @param errorMessage 错误信息
	 */
	public static void onError(int requestCode, int errorCode, String errorMessage){
		switch (errorCode) {
			case 403: {
				// 未授权,需要登录
				BaseApplication.appContext.startActivity(new Intent(BaseApplication.appContext, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			} break;
		}
	}
}
