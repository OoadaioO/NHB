package com.nhb.app.custom.domain;


import com.fast.library.utils.AndroidInfoUtils;
import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.location.LocationServiceutils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-05 14:24
 * <p> Version: 1.0.0
 * <p> Description: 请求拦截器,在这里修改通用参数和UA
 * <p>
 * <p>***********************************************************************
 */

public class NHBDoctorIntercept implements Interceptor {

    final String TAG = "HTTP_REQUEST";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                // =========================统计信息=========================
                // 当前APP版本
                .addQueryParameter("version", AndroidInfoUtils.versionName())
                // 平台标示（android/ios）
                .addQueryParameter("platform", "android")
                // 经纬度
                .addQueryParameter("lat", String.valueOf(LocationServiceutils.getInstance().lat))
                .addQueryParameter("lng", String.valueOf(LocationServiceutils.getInstance().lng))
                .build();

        LogUtils.d(TAG, "Method:【" + request.method() + "】" + url);

        request = request.newBuilder()
                .url(url)
                .addHeader("User-Agent", "com.nianhuibao.customer/" + AndroidInfoUtils.versionName() + "/Android/" + AndroidInfoUtils.versionCode())
                .build();
        return chain.proceed(request);
    }
}
