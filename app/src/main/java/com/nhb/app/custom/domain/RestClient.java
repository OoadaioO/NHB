package com.nhb.app.custom.domain;


import com.fast.library.utils.UIUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.library.config.Constant;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <br>***********************************************************************
 * <br> Author:pengxiaofang
 * <br> CreateData:2016-03-10 11:48
 * <br> Version:1.0
 * <br> Description:HttpManager
 * <br>***********************************************************************
 */
public class RestClient {

    public static RestClient instance;
    private Api api;
    private CookieManager cookieManager;

    private RestClient() {
        init();
    }

    public static RestClient getInstance() {
        if (null == instance) {
            instance = new RestClient();
        }
        return instance;
    }

    public static Api getService() {
        return RestClient.getInstance().api;
    }

    private void init() {
        // init cookie manager
        PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.appContext);
//		cookieManager = new CookieManager(syncCookies(cookieStore), CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NHBDoctorIntercept())
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UIUtils.getString(R.string.base_url))
//				.client(client)
                // Json解析
                .addConverterFactory(FastJsonConverterFactory.create())
                // RXJava
//				 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public List<HttpCookie> getCookies() {
        return cookieManager.getCookieStore().getCookies();
    }

    public void cleanCookies() {
        cookieManager.getCookieStore().removeAll();
    }

    /**
     * 将老的网络库的Cookie同步到新的网络库中
     */
//	public PersistentCookieStore syncCookies(PersistentCookieStore cookieStore) {
//
//		final String SYNC_LOOPJ_COOKIES = "sync_loopj_cookies";
//		// 如果已经同步过loopj的cookie则不需要再同步
//		if (SpCustom.get().readBoolean(SYNC_LOOPJ_COOKIES, false)) {
//			return cookieStore;
//		}
//		//获取老的网络库里的Cookie
//		HttpContext httpContext = HttpClientManager.getInstance().getClient().getHttpContext();
//		org.apache.http.client.CookieStore store = (org.apache.http.client.CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
//		if (store == null) {
//			store = new com.loopj.android.http.PersistentCookieStore(BaseApp.appContext);
//		}
//		List<org.apache.http.cookie.Cookie> cookies = store.getCookies();
//		URI uri = URI.create(Constants.getAppHost());
//		if (null != cookies) {
//			for (int i = 0; i < cookies.size(); i++) {
//				org.apache.http.cookie.Cookie cookie = cookies.get(i);
//				//将新域名与cookie对应
//				HttpCookie syncCookie = new HttpCookie(cookie.getName(), cookie.getValue());
//				syncCookie.setDomain(cookie.getDomain());
//				syncCookie.setPath(cookie.getPath());
//				syncCookie.setMaxAge((cookie.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
//				cookieStore.add(uri, syncCookie);
//			}
//		}
//		SpCustom.get().write(SYNC_LOOPJ_COOKIES, true);
//		return cookieStore;
//	}
}
