package com.nhb.app.custom.base;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.nhb.app.custom.domain.BusinessCallback;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.viewmodel.interfaces.ILoadingStatus;

import retrofit2.Call;

/**
 * <br>***********************************************************************
 * <br> Author: pengxiaofang
 * <br> CreateData: 2016-06-02 11:48
 * <br> Version: 1.0
 * <br> Description: 继承自 {@link NHBViewModel} ,当页面初始化需要数据请求的可以继承此类。
 * <br>				 继承此类后,需要在对应的View中include {@link# com.gengmei.doctor.R.layout.layout_loading_status}
 * <br>				 LoadingStatusView的状态会自动改变
 * <br>***********************************************************************
 */
public abstract class FetchDataViewModel extends NHBViewModel implements ILoadingStatus {

	/**
	 * 初始化数据请求状态
	 */
	public static final int                     CODE_INIT    = -1;
	/**
	 * 数据加载中
	 */
	public static final int                     CODE_LOADING = 0;
	/**
	 * 数据加载成功
	 */
	public static final int                     CODE_SUCCESS = 1;
	/**
	 * 数据加载返回值为空
	 */
	public static final int                     CODE_EMPTY   = 2;
	/**
	 * 数据加载失败
	 */
	public static final int                     CODE_ERROR   = 3;
	/**
	 * 数据加载为空的显示文案
	 */
	public final ObservableField<String> emptyText    = new ObservableField<>("");
	/**
	 * 数据加载失败的文案
	 */
	public final ObservableField<String> failureText  = new ObservableField<>("");
	/**
	 * 数据请求状态码
	 */
	public final ObservableInt resultCode   = new ObservableInt(CODE_INIT);

	/**
	 * 获取数据
	 */
	public void fetchRemoteData() {
		fetchRemoteData(true);
	}

	/**
	 * 获取数据
	 */
	public void fetchRemoteData(boolean isSilence) {
		Call call = loadApiService();
		if (call != null) {
			requestArray.add(call);
			loadData(-1, call, isSilence);
		}
	}

	/**
	 * 开始加载数据
	 *
	 * @param apiService
	 */
	private final void loadData(int requestCode, Call apiService, final boolean isSilence) {
		if (resultCode.get() != CODE_SUCCESS) {
			// 如果已经获取数据成功了，则此时应该是下拉刷新或加载更多，不需要显示遮罩
			resultCode.set(CODE_LOADING);
		} else {
			// 重置状态
			resultCode.set(CODE_INIT);
		}
		if (!isSilence){
			dataLoading.set(true);
		}
		apiService.enqueue(new BusinessCallback(requestCode) {

			@Override
			public void onSuccess(int requestCode, Object bean, NHBResponse response) {
				resultCode.set(CODE_SUCCESS);
				if(!TextUtils.isEmpty(response.message)){
					emptyText.set(response.message);
				}
				handleData(-1, bean, response);
			}

			@Override
			public void onError(int requestCode, int errorCode, String errorMessage) {
				resultCode.set(CODE_ERROR);
				if (!TextUtils.isEmpty(errorMessage)) {
					failureText.set(errorMessage);
				}
                handleFailedData(requestCode,errorMessage);
			}

			@Override
			public void onComplete(int requestCode, Call call) {
				super.onComplete(requestCode, call);
				requestArray.remove(call);
				if (!isSilence){
					dataLoading.set(false);
				}
			}
		});
	}

	/**
	 * 当请求服务器端数据返回值为空时的文案
	 * @return
	 */
	@Override
	public ObservableField<String> getEmptyText() {
		return emptyText;
	}

	/**
	 * 当请求服务器端数据返回值为失败时的文案
	 * @return
	 */
	@Override
	public ObservableField<String> getFailureText() {
		return failureText;
	}

	/**
	 * 数据请求结果码
	 * @see #CODE_INIT
	 * @return
	 */
	@Override
	public ObservableInt getResultCode() {
		return resultCode;
	}

	/**
	 * 重新请求数据(当数据加载失败或为空时)
	 * @param view
	 */
	@Override
	public void onClickReload(View view) {
		fetchRemoteData();
	}

	/**
	 * 获取ApiService
	 *
	 * @return
	 */
	public abstract Call loadApiService();
}
