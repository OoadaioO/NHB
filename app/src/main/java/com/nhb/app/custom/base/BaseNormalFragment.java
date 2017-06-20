package com.nhb.app.custom.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.fast.library.utils.LogUtils;
import com.nhb.app.custom.constant.Constants;

import java.util.List;

/**
 * @author pengxiaofang
 *         <p/>
 *         基类Fragment，暂时保留，如无使用场景，后期去掉
 */
public abstract class BaseNormalFragment extends NHBFragment {

    protected String TAG = BaseFragment.class.getSimpleName();
    protected String PAGE_NAME = "";// 页面名称（页面唯一编号）
    protected String REFERRER = "";// 来源页面（页面唯一编号）
    protected String BUSINESS_ID = "";// 业务ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ((Object) this).getClass().getSimpleName();// ((Object)
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // =====================================================================================

    @Override
    protected void updateArguments() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            String referrer = bundle.getString(Constants.REFERRER_PAGE_NAME);
            if (!TextUtils.isEmpty(referrer)) {
                REFERRER = referrer;
            }
            LogUtils.d(TAG, "REFERRER = " + REFERRER);
        }
        super.updateArguments();
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(Constants.REFERRER_PAGE_NAME, PAGE_NAME);
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(Constants.REFERRER_PAGE_NAME, PAGE_NAME);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag) {
        handleArguments(fragment);
        super.replaceFragmentByTag(layoutId, fragment, tag);
    }

    @Override
    public void replaceFragmentByTag(@IdRes int layoutId, Fragment fragment, String tag, String backStackName) {
        handleArguments(fragment);
        super.replaceFragmentByTag(layoutId, fragment, tag, backStackName);
    }

    public String getPageName() {
        return PAGE_NAME;
    }

    private void handleArguments(Fragment fragment) {
        try {
            String referrerPageName = getReferrerPageName(getChildFragmentManager());
            Bundle bundle = fragment.getArguments();
            if (null == bundle) {
                bundle = new Bundle();
                bundle.putString(Constants.REFERRER_PAGE_NAME, referrerPageName);
                fragment.setArguments(bundle);
            } else {
                bundle.putString(Constants.REFERRER_PAGE_NAME, referrerPageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getReferrerPageName(FragmentManager fragmentManager) {
        // 切换 Fragment 时，取当前显示的 Fragment 的 page_name 作为新 Fragment 的 REFERRER
        Fragment currentFragment = null;
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    currentFragment = fragment;
            }
        }
        String referrerPageName = REFERRER;
        if (currentFragment != null) {
            referrerPageName = ((BaseNormalFragment) currentFragment).getPageName();
        }
        LogUtils.d(TAG, "referrerPageName = " + referrerPageName);
        return referrerPageName;
    }

}
