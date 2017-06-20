package com.nhb.app.custom.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.constant.NHBConstant;
import com.nhb.app.custom.common.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * @author pengxiaofang
 * @since 2015-12-24
 * <p/>
 * 说明：
 * <ul>
 * <li> loadLayoutId():抽象方法，获取contentView，无需再写setContentView(int layoutResID)方法
 * <li>	initialize():抽象方法，用于数据、UI等初始化
 * <li>	toGetPageData(boolean):获取当前页面所需的数据，需要指定是否显示loading框，最好使用此方法获取页面数据
 * <li>	pendingTransitionEnter():startActivity时的动画，默认为右侧进入，子类可以重写此方法设置Activity进入动画
 * <ul/>
 */
public abstract class NHBFragment extends Fragment {

    public View mRootView = null;
    public Context mContext = null;
    public LoadingDialog mLoadingDialog = null;
    //上游页面（Activity或Fragment）
    public String mReferrerPageClass = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        parseArguments();
        mLoadingDialog = new LoadingDialog(mContext);
    }

    /**
     * 解析参数
     */
    protected void parseArguments() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mReferrerPageClass = bundle.getString(NHBConstant.Extras.REFERRER_PAGE_CLASS);
            LogUtils.d(NHBConstant.LOG_TAG, "the referrer_page_class is: " + mReferrerPageClass);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        updateArguments();
        if (null == mRootView) {
            mRootView = inflater.inflate(loadLayoutId(), null);
            //view注入
            ButterKnife.bind(this, mRootView);
            initialize();
        }
        ViewGroup group = (ViewGroup) mRootView.getParent();
        if (group != null) {
            group.removeView(mRootView);
        }
        return mRootView;
    }

    /**
     * 解析参数
     */
    protected void updateArguments() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected View findViewById(@IdRes int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        }
        return null;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivity(intent);
        pendingTransitionEnter();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
        super.startActivityForResult(intent, requestCode);
        pendingTransitionEnter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //移除view注入
        ButterKnife.unbind(this);
    }

    /**
     * 加载View
     *
     * @return
     */
    @LayoutRes
    protected abstract int loadLayoutId();

    /**
     * 初始化
     */
    protected abstract void initialize();

    /**
     * 获取页面数据
     */
    protected void toGetPageData(boolean showLoading) {
    }

    /**
     * 默认入栈Activity从右侧进入
     * 子类可以重写此方法改变补间动画
     */
    protected void pendingTransitionEnter() {
        transitionWithRightEnter();
    }

    /**
     * enter
     * 从右侧进入
     * exit
     * 静止不动
     */
    protected void transitionWithRightEnter() {
        getActivity().overridePendingTransition(R.anim.activity_enter_right, R.anim.activity_standby);
    }

    /**
     * enter
     * 从底部进入
     * exit
     * 静止不动
     */
    protected void transitionWithBottomEnter() {
        getActivity().overridePendingTransition(R.anim.activity_enter_bottom, R.anim.activity_standby);
    }

    /**
     * replaceFragment
     *
     * @param layoutId
     * @param fragment
     * @param tag
     */
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag) {
        //增加页面来源
        try {
            Bundle bundle = fragment.getArguments();
            if (null == bundle) {
                bundle = new Bundle();
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
                fragment.setArguments(bundle);
            } else {
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, tag);
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * replaceFragment,可加入回退栈中
     *
     * @param layoutId
     * @param fragment
     * @param tag
     */
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag, String backStackName) {
        //增加页面来源
        try {
            Bundle bundle = fragment.getArguments();
            if (null == bundle) {
                bundle = new Bundle();
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
                fragment.setArguments(bundle);
            } else {
                bundle.putString(NHBConstant.Extras.REFERRER_PAGE_CLASS, getClass().getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, tag);
        transaction.addToBackStack(backStackName);
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * 加载Loading框
     */
    protected void showLD() {
        try {
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭Loading框
     */
    protected void dismissLD() {
        try {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading框是否显示
     *
     * @return
     */
    public boolean isDialogLoading() {
        if (null == mLoadingDialog) {
            return false;
        } else {
            return mLoadingDialog.isShowing();
        }
    }
}