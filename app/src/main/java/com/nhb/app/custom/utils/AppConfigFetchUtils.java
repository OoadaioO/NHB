package com.nhb.app.custom.utils;

import android.content.Context;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.BuildConfig;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.AppConfig;
import com.nhb.app.custom.common.dialog.UpdateDialog;
import com.nhb.app.custom.domain.BusinessCallback;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * Created by pengxiaofang.
 */
public class AppConfigFetchUtils {
    public static final String CLIENT_ANDROID = "2";
    // 请求队列
    private static List<Call> requestArray = new ArrayList<>();

    /**
     * 检测新版本
     */
    public static void fetchAppConfig(final Context context) {
        fetchRemoteData(context, RestClient.getService().toGetAppConfig(CLIENT_ANDROID, String.valueOf(BuildConfig.VERSION_CODE)));
    }

    private static void fetchRemoteData(Context context, Call apiService) {
        requestArray.add(apiService);
        loadData(context, apiService);
    }

    private static void loadData(final Context context, Call apiService) {
        apiService.enqueue(new BusinessCallback(FetchDataViewModel.CODE_INIT) {
            @Override
            public void onSuccess(int requestCode, Object bean, NHBResponse response) {
                if (null != bean) {
                    AppConfig appConfig = (AppConfig) bean;
                    createUpdateDialog(context, appConfig);
                } else {
                    ToastUtil.get().shortToast(response.message);
                }
            }

            @Override
            public void onError(int requestCode, int errorCode, String errorMessage) {
                ToastUtil.get().shortToast(errorMessage);
            }

            @Override
            public void onComplete(int requestCode, retrofit2.Call call) {
                super.onComplete(requestCode, call);
                requestArray.remove(call);
            }
        });
    }

    /**
     * 新版本更新的弹窗
     */
    private static void createUpdateDialog(Context context, AppConfig bean) {
        new UpdateDialog(context, bean).show();
    }
}
