package com.nhb.app.custom.base;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.fast.library.ui.ToastUtil;
import com.nhb.app.custom.bean.RemindBean;
import com.nhb.app.custom.domain.BusinessCallback;
import com.nhb.app.custom.domain.NHBResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 11:48
 * Version:1.0
 * Description:顶层ViewModel
 * ***********************************************************************
 */
public class NHBViewModel extends BaseObservable {

	// 请求队列
	protected        List<Call>           requestArray = new ArrayList<>();
	// 各处小圆点
	public static ObservableField<RemindBean> badges       = new ObservableField<>();
	// 接口请求中
	public final ObservableBoolean dataLoading = new ObservableBoolean();
	// 描述Activity动作的接口
	public ActivityActionListener mActivityActionListener;

	public interface ActivityActionListener {

		void startActivity(Intent intent);

		void startActivityForResult(Intent intent, int requestCode);

		void finishActivity();

		void finishActivityForResult(int resultCode);

		void finishActivityForResult(int resultCode, Intent intent);

	}

	/**
	 * 设置Activity动作监听
	 * @param activityActionListener
	 */
	public void setOnActivityActionListener(ActivityActionListener activityActionListener) {
		mActivityActionListener = activityActionListener;
	}

	/**
	 * 数据请求
	 *
	 * @param requestCode 定义的请求码,当请求数据返回后根据返回码处理对应的逻辑
	 *                    @see #handleData(int, Object, NHBResponse)
	 * @param call		  构造的请求器
	 */
	public void fetchRemoteData(int requestCode, Call call) {
		fetchRemoteData(requestCode, call, false);
	}

	/**
	 * 数据请求
	 *
	 * @param requestCode 定义的请求码,当请求数据返回后根据返回码处理对应的逻辑
	 *                    @see #handleData(int, Object, NHBResponse)
	 * @param call        构造的请求器
	 *                    @see
	 * @param isSilence   是否静默请求,如果为true则请求过程中不会出现Loading框
	 */
	protected void fetchRemoteData(int requestCode, Call call, boolean isSilence) {
		if (null == call) {
			//			dataLoading.set(true);
			//			dataLoading.set(false);
			return;
		}
		requestArray.add(call);
		loadData(requestCode, call, isSilence);
	}

	/**
	 * 开始请求数据
	 * @param requestCode 定义的请求码,当请求数据返回后根据返回码处理对应的逻辑
	 * @param apiService 构造的请求器
	 * @param isSilence 是否静默请求,如果为true则请求过程中不会出现Loading框
	 */
	private final void loadData(int requestCode, Call apiService, final boolean isSilence) {
		if (!isSilence) {
			dataLoading.set(true);
		}
		apiService.enqueue(new BusinessCallback(requestCode) {

			@Override
			public void onSuccess(int requestCode, Object bean, NHBResponse response) {
				handleData(requestCode, bean, response);
			}

			@Override
			public void onError(int requestCode, int errorCode, String errorMessage) {
				ToastUtil.get().shortToast(errorMessage);
				handleFailedData(requestCode,errorMessage);
			}

			@Override
			public void onComplete(int requestCode, Call call) {
				super.onComplete(requestCode, call);
				if (!isSilence) {
					dataLoading.set(false);
				}
				requestArray.remove(call);
			}
		});
	}

	/**
	 * 处理数据
	 * @param requestCode 定义的请求码,当请求数据返回后根据返回码处理对应的逻辑
	 * @param response 完整信息
	 * @param data 解析后的数据,对应下边data标签,使用时需要将data强转成对应的业务bean
	 *             {
	 *             		code:0,
	 *             		message:xx,
	 *             		data:{}
	 *             }
	 *
	 */
	public void handleData(int requestCode, Object data, NHBResponse response) {
	}

	/**
	 * 处理请求错误数据
	 *
	 * @param requestCode  定义的请求码,当请求数据返回后根据返回码处理对应的逻辑
	 * @param errorMessage 错误信息
	 */
	public void handleFailedData(int requestCode, String errorMessage) {
	}

	/**
	 * 取消所有的请求
	 */
	public void cancelAllRequests() {
		for (Call request : requestArray) {
			request.cancel();
		}
		requestArray.clear();
	}

	/**
	 * 关闭Activity
	 */
	public void finishActivity() {
		if (null != mActivityActionListener) {
			mActivityActionListener.finishActivity();
		}
	}

	/**
	 * 启动Activity
	 * @param intent
	 * 		  @see Intent
	 */
	public void startActivity(Intent intent) {
		if (null != mActivityActionListener) {
			mActivityActionListener.startActivity(intent);
		}
	}

	/**
	 * 启动Activity且带参数并接收返回值
	 * @param intent
	 * @param requestCode
	 */
	public void startActivityForResult(Intent intent, int requestCode) {
		if (null != mActivityActionListener) {
			mActivityActionListener.startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * 关闭Activity不返回数据
	 * @param resultCode
	 */
	public void finishActivityForResult(int resultCode) {
		if (null != mActivityActionListener) {
			mActivityActionListener.finishActivityForResult(resultCode);
		}
	}

	/**
	 * 关闭Activity且返回数据
	 * @param resultCode
	 * @param intent
	 */
	public void finishActivityForResult(int resultCode, Intent intent) {
		if (null != mActivityActionListener) {
			mActivityActionListener.finishActivityForResult(resultCode, intent);
		}
	}
}
