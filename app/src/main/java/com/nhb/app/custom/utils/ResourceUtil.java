package com.nhb.app.custom.utils;


import com.nhb.app.custom.base.BaseApplication;

/**
 * Created by pengxiaofang on 15/4/16.
 * 资源工具类：用于获取String、Color等
 */
public class ResourceUtil {

	public static String getString(int resId){
		return BaseApplication.appContext.getString(resId);
	}

	public static String getString(int resId, Object... formatArgs){
		return BaseApplication.appContext.getString(resId, formatArgs);
	}

	public static int getColor(int resId) {
		return BaseApplication.appContext.getResources().getColor(resId);
	}
}
