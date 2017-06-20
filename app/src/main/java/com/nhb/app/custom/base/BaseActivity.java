package com.nhb.app.custom.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;

import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.BR;
import com.nhb.app.custom.common.dialog.LoadingDialog;
import com.nhb.app.custom.receiver.PushUtils;
import com.nhb.app.custom.ui.personal.LoginActivity;
import com.nhb.app.custom.utils.UserInfoUtils;


/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 11:48
 * Version:1.0
 * Description:Activity基类,负责绑定View和ViewModel
 * ***********************************************************************
 */
public abstract class BaseActivity<VM extends NHBViewModel, VDB extends ViewDataBinding> extends NHBActivity {

    /**
     * Activity对应的ViewModel {@link NHBViewModel} 或 {@link FetchDataViewModel}
     */
    protected VM viewModel;
    /**
     * 系统自动生成的ViewDataBinding,当创建layout编译后会自动生成。<br>
     * eg:{activity_home.xml} 会生成 {ActivityHomeBinding} <br>
     * 此对象中持有了layout中设置id的view对象,eg: {ViewDataBinding.mainLlContent}
     */
    protected VDB viewDataBinding;

    @Override
    protected void gmInit() {
        //设置当前Activity的屏幕方向
        setScreenOrientation();
        //成员变量context
        mContext = this;
        //输出当前Activity名称
        LogUtils.d("currentClass ===>> " + getClass().getSimpleName());
        //解析Intent
        parseIntent(getIntent());
        //获取layout
        int layoutId = loadLayoutId();
        //加载viewModel
        viewModel = loadViewModel();
        //设置Activity动作监听
        viewModel.setOnActivityActionListener(new NHBViewModel.ActivityActionListener() {
            @Override
            public void startActivity(Intent intent) {
                BaseActivity.this.startActivity(intent);
            }

            @Override
            public void finishActivity() {
                if (!isFinishing()) {
                    finish();
                }
            }

            @Override
            public void finishActivityForResult(int resultCode) {
                setResult(resultCode);
                if (!isFinishing()) {
                    finish();
                }
            }

            @Override
            public void finishActivityForResult(int resultCode, Intent intent) {
                setResult(resultCode, intent);
                if (!isFinishing()) {
                    finish();
                }
            }

            @Override
            public void startActivityForResult(Intent intent, int requestCode) {
                BaseActivity.this.startActivityForResult(intent, requestCode);
            }
        });
        // 当layout和viewModel都不为空时绑定
        if (0 != layoutId && null != viewModel) {
            // 此方法替代Activity的 #setContentView# 方法
            viewDataBinding = DataBindingUtil.setContentView(this, loadLayoutId());
            viewDataBinding.setVariable(BR.viewModel, viewModel);
        }
        //初始化Loading
        mLoadingDialog = new LoadingDialog(this);
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
        //初始化
        initialize();
        //初始化个推
        PushUtils.init(this);
    }

    /**
     * 获取ViewModel
     *
     * @return
     * @see NHBViewModel
     * @see FetchDataViewModel
     */
    protected abstract VM loadViewModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消所有的网络请求
        if (null != viewModel) {
            viewModel.cancelAllRequests();
        }
    }

    /**
     * 是否已登录
     *
     * @return
     */
    public static boolean isLogin() {
        return UserInfoUtils.checkUserLogin();
    }

    /**
     * 启动登录流程
     */
    public void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        transitionWithBottomEnter();
    }

    /**
     * 启动登录流程
     */
    public void startLogin(int intentFlag) {
        startActivity(new Intent(this, LoginActivity.class).setFlags(intentFlag));
        transitionWithBottomEnter();
    }
}
