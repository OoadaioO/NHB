package com.nhb.app.library.http;

import com.fast.library.utils.UIUtils;
import com.nhb.app.library.R;
import com.nhb.app.library.config.Constant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 说明：服务端
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 10:13
 * <p/>
 * 版本：verson 1.0
 */
public class ServerClient {

    private final static String baseUrl = UIUtils.getString(R.string.url);
    private final static String TAG = "ServerClient";

    private static Retrofit retrofit = null;

    public static <T> T getAPI(Class<T> t){
        if (retrofit == null){

            List<Interceptor> interceptorList = new ArrayList<>();
            interceptorList.add(new HttpLoggingInterceptor(TAG, HttpLoggingInterceptor.Level.BODY));

            retrofit = new RetrofitClient.Builder().baseUrl(baseUrl)
                    .setTimeout(Constant.TIME_OUT)
                    .setInterceptorList(interceptorList)
                    .build()
                    .retrofitBuilder()
                    .addConverterFactory(StringConverterFactroy.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(t);
    }

}