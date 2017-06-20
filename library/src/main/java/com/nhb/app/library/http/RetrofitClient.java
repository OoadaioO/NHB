package com.nhb.app.library.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 说明：RetrofitClient
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/8 17:03
 * <p/>
 * 版本：verson 1.0
 */
public class RetrofitClient {

    private Builder mBuilder;
    private Retrofit.Builder mRetrofitBuilder;

    public RetrofitClient(Builder builder){
        this.mBuilder = builder;
    }

    public static class Builder{
        //配置自定义的OkHttpClient
        private OkHttpClient mOkHttpClient;
        private String baseUrl;
        private Dispatcher dispatcher;
        private CookieJar cookieJar;
        //网络拦截器
        private List<Interceptor> networkInterceptorList;
        //拦截器
        private List<Interceptor> interceptorList;
        //证书列表
        private List<InputStream> mCertificateList;
        private HostnameVerifier mHostnameVerifier;
        //设置超时时间
        private int mTimeout;
        //设置日志输出（调试模式）
        private boolean mDebug;

        public Builder(){
            this.mCertificateList = new ArrayList<>();
            this.networkInterceptorList = new ArrayList<>();
            this.interceptorList = new ArrayList<>();
            this.mTimeout = 10000;
            this.mDebug = true;
        }

        public String baseUrl() {
            return baseUrl;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public OkHttpClient getOkHttpClient() {
            return mOkHttpClient;
        }

        public Builder setOkHttpClient(OkHttpClient mOkHttpClient) {
            this.mOkHttpClient = mOkHttpClient;
            return this;
        }

        public Dispatcher getDispatcher() {
            return dispatcher;
        }

        public Builder setDispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public CookieJar getCookieJar() {
            return cookieJar;
        }

        public Builder setCookieJar(CookieJar cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public List<Interceptor> getNetworkInterceptorList() {
            return networkInterceptorList;
        }

        public Builder setNetworkInterceptorList(List<Interceptor> networkInterceptorList) {
            this.networkInterceptorList = networkInterceptorList;
            return this;
        }

        public List<Interceptor> getInterceptorList() {
            return interceptorList;
        }

        public Builder setInterceptorList(List<Interceptor> interceptorList) {
            this.interceptorList = interceptorList;
            return this;
        }

        public List<InputStream> getCertificateList() {
            return mCertificateList;
        }

        public Builder setCertificateList(List<InputStream> mCertificateList) {
            this.mCertificateList = mCertificateList;
            return this;
        }

        public HostnameVerifier getHostnameVerifier() {
            return mHostnameVerifier;
        }

        public Builder setHostnameVerifier(HostnameVerifier mHostnameVerifier) {
            this.mHostnameVerifier = mHostnameVerifier;
            return this;
        }

        public int getTimeout() {
            return mTimeout;
        }

        public Builder setTimeout(int mTimeout) {
            this.mTimeout = mTimeout;
            return this;
        }

        public boolean isDebug() {
            return mDebug;
        }

        public Builder setDebug(boolean mDebug) {
            this.mDebug = mDebug;
            return this;
        }

        public RetrofitClient build(){
            return new RetrofitClient(this).init();
        }
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public void setBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }

    public Retrofit.Builder retrofitBuilder(){
        return mRetrofitBuilder;
    }

    private RetrofitClient init(){
        if (mRetrofitBuilder == null){
            mRetrofitBuilder = new Retrofit.Builder();
            if (mBuilder.getOkHttpClient() != null){
                mRetrofitBuilder.client(mBuilder.getOkHttpClient());
            }else {
                mRetrofitBuilder.client(OkHttpClientFactory.create(mBuilder));
            }
            mRetrofitBuilder.baseUrl(mBuilder.baseUrl());
        }
        return this;
    }
}
