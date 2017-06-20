package com.nhb.app.custom.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhb.app.custom.BR;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 11:48
 * Version:1.0
 * Description:Fragment基类,负责绑定View和ViewModel
 * ***********************************************************************
 */
public abstract class BaseFragment<VM extends NHBViewModel, VDB extends ViewDataBinding> extends NHBFragment {

	/**
	 * Fragment对应的ViewMode {@link NHBViewModel} 或 {@link FetchDataViewModel}
	 */
	public VM                 viewModel;
	/**
	 * 系统自动生成的ViewDataBinding,当创建layout编译后会自动生成。<br>
	 * eg:{activity_home.xml} 会生成 {ActivityHomeBinding} <br>
	 * 此对象中持有了layout中设置id的view对象,eg: {ViewDataBinding.mainLlContent}
	 */
	public VDB                viewDataBinding;
	public OnViewLoadListener mOnViewLoadListener;

	/**
	 * Fragment的view加载状态监听
	 */
	public interface OnViewLoadListener {
		/**
		 * 开始加载view,此时还不能获取view的对象
		 */
		void onLoadStarted();

		/**
		 * 加载view完毕,此时可以获取view的对象
		 */
		void onLoadFinished();
	}

	/**
	 * 设置Fragment的view加载状态监听
	 * @param onViewLoadListener
	 * @return
	 */
	public BaseFragment setOnViewLoadListener(OnViewLoadListener onViewLoadListener) {
		mOnViewLoadListener = onViewLoadListener;
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 解析传递过来的参数
		updateArguments();
		// 开始加载数据
		if (null != mOnViewLoadListener) {
			mOnViewLoadListener.onLoadStarted();
		}
		// View做缓存处理
		if (null == mRootView) {
			// 加载layout
			int layoutId = loadLayoutId();
			// 加载ViewModel
			viewModel = loadViewModel();
			if (0 != layoutId && null != viewModel) {
				viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
				viewDataBinding.setVariable(BR.viewModel, viewModel);
			}
			// 获取根view
			mRootView = viewDataBinding.getRoot();
			viewModel.setOnActivityActionListener(new NHBViewModel.ActivityActionListener() {
				@Override
				public void startActivity(Intent intent) {
					BaseFragment.this.startActivity(intent);
				}

				@Override
				public void startActivityForResult(Intent intent, int requestCode) {
					BaseFragment.this.startActivityForResult(intent, requestCode);
				}

				@Override
				public void finishActivity() {
					getActivity().finish();
				}

				@Override
				public void finishActivityForResult(int resultCode) {
					getActivity().setResult(resultCode);
					getActivity().finish();
				}

				@Override
				public void finishActivityForResult(int requestCode, Intent intent) {
					getActivity().setResult(requestCode, intent);
					getActivity().finish();
				}
			});
			//监听数据请求状态更新Loading框的显示与隐藏
			viewModel.dataLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
				@Override
				public void onPropertyChanged(Observable sender, int propertyId) {
					if (viewModel.dataLoading.get()) {
						showLD();
					} else {
						dismissLD();
					}
				}
			});
			// 初始化,可以做逻辑处理
			initialize();
			// 数据加载完毕
			if (null != mOnViewLoadListener) {
				mOnViewLoadListener.onLoadFinished();
			}
		}
		ViewGroup group = (ViewGroup) mRootView.getParent();
		if (group != null) {
			group.removeView(mRootView);
		}
		return mRootView;
	}

	/**
	 * 获取ViewModel
	 * @return
	 * 		@see NHBViewModel
	 * 		@see FetchDataViewModel
	 */
	protected abstract VM loadViewModel();

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消所有的网络请求
		if (null != viewModel) {
			viewModel.cancelAllRequests();
		}
	}
}
