package com.nhb.app.custom.domain;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by pengxiaofang on 15/4/16.
 * 封装的业务回调
 */
public abstract class BusinessCallback implements retrofit2.Callback<Object> {

	private int requestCode;

	public BusinessCallback(int requestCode) {
		this.requestCode = requestCode;
	}

	@Override
	public void onResponse(Call<Object> call, Response<Object> response) {
		onComplete(requestCode, call);
		if (response.isSuccessful()) {
			Object businessResponse = response.body();
			if (businessResponse instanceof NHBResponse) {
				NHBResponse NHBResponse = (NHBResponse) businessResponse;
				if (NHBResponse.code == 0) {
					try {
						onSuccess(requestCode, NHBResponse.data, NHBResponse);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					onError(requestCode, NHBResponse.error_code, NHBResponse.message);
				}
			} else {
				onError(requestCode, NHBResponse.ERR_RESPONSE_FORMAT, "网络或服务器开小差,请稍后再试~");
			}
		} else {
			// 拦截器处理
			HttpErrorInterceptor.onError(requestCode, response.code(), null == response.message() ? "" : response.message());
			onError(requestCode, response.code(), null == response.message() ? "" : response.message());
		}
	}

	@Override
	public void onFailure(Call<Object> call, Throwable t) {
		onComplete(requestCode, call);
		onError(requestCode, NHBResponse.ERR_NETWORK, "网络或服务器开小差,请稍后再试~");
		t.printStackTrace();
	}

	public abstract void onSuccess(int requestCode, Object bean, NHBResponse response);

	/**
	 * 包含Http error和业务error
	 *
	 * @param errorCode
	 * @param errorMessage
	 */
	public abstract void onError(int requestCode, int errorCode, String errorMessage);

	public void onComplete(int requestCode, Call call) {

	}
}
