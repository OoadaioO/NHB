package com.nhb.app.library.http;

import com.fast.library.http.HttpsCerManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 说明：OkhttpClient
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/8 16:25
 * <p/>
 * 版本：verson 1.0
 */
public class OkHttpClientFactory {

    public static OkHttpClient create(RetrofitClient.Builder builder) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (builder == null){
            return okHttpBuilder.build();
        }
        //okhttp3 cookie设置
        if (builder.getCookieJar() != null) {
            okHttpBuilder.cookieJar(builder.getCookieJar());
        }
        //设置网络拦截器
        if (builder.getNetworkInterceptorList() != null && !builder.getNetworkInterceptorList().isEmpty()){
            okHttpBuilder.networkInterceptors().addAll(builder.getNetworkInterceptorList());
        }
        //设置应用拦截器
        if (builder.getInterceptorList() != null && !builder.getInterceptorList().isEmpty()){
            okHttpBuilder.interceptors().addAll(builder.getInterceptorList());
        }
        if (builder.getDispatcher() != null){
            okHttpBuilder.dispatcher(builder.getDispatcher());
        }
        //设置请求时间
        okHttpBuilder.connectTimeout(builder.getTimeout(), TimeUnit.MILLISECONDS);
        okHttpBuilder.writeTimeout(builder.getTimeout(), TimeUnit.MILLISECONDS);
        okHttpBuilder.readTimeout(builder.getTimeout(), TimeUnit.MILLISECONDS);
        //请求支持重定向
        okHttpBuilder.followRedirects(true);
        okHttpBuilder.followSslRedirects(true);
        //设置证书
        if (builder.getCertificateList() != null && !builder.getCertificateList().isEmpty()){
            HttpsCerManager httpsCerManager = new HttpsCerManager(okHttpBuilder);
            httpsCerManager.setCertificates(builder.getCertificateList());
        }
        return okHttpBuilder.build();
    }

}
