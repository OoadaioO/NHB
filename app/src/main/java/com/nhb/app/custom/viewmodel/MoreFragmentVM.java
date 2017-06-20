package com.nhb.app.custom.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.ActivityUtils;
import com.fast.library.utils.AndroidInfoUtils;
import com.fast.library.utils.ToolUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseApplication;
import com.nhb.app.custom.base.FetchDataViewModel;
import com.nhb.app.custom.bean.MobileConfigBean;
import com.nhb.app.custom.common.dialog.CommonDialog;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.domain.NHBResponse;
import com.nhb.app.custom.domain.PicassoManager;
import com.nhb.app.custom.domain.RestClient;
import com.nhb.app.custom.ui.personal.AboutUsActivity;
import com.nhb.app.custom.ui.personal.FeedBackActivity;
import com.nhb.app.custom.ui.personal.WebViewActivity;
import com.nhb.app.custom.utils.AppConfigFetchUtils;
import com.nhb.app.custom.utils.CacheCleanUtil;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.custom.utils.UserInfoUtils;
import com.nhb.app.custom.viewmodel.interfaces.ITitleBarSimple;

import retrofit2.Call;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-03 10:58
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MoreFragmentVM extends FetchDataViewModel implements ITitleBarSimple {
    //缓存大小
    public ObservableField<String> cacheSize = new ObservableField<>();
    //版本号
    public ObservableField<String> versionCode = new ObservableField<>(AndroidInfoUtils.versionName());

    @Override
    public ObservableField<String> getTitle() {
        return new ObservableField<>(ResourceUtil.getString(R.string.menu_bar_more));
    }

    public void clickClearCache(View view) {
        new CommonDialog(view.getContext(), ResourceUtil.getString(R.string.prompt), ResourceUtil.getString(R.string.submit_clear_cache), ResourceUtil.getString(R.string.cancel), ResourceUtil.getString(R.string.confirm), new CommonDialog.OnActionListener() {
            @Override
            public void clickConfirm() {
                clearCache();
            }
        }).show();
    }

    private void clearCache() {
        PicassoManager.getInstance().clearDiskCache();
        try {
            String size = CacheCleanUtil.getTotalCacheSize(BaseApplication.appContext);
            if (TextUtils.equals("0KB", size)) {
                ToastUtil.get().shortToast(R.string.cache_no_need_clean);
                return;
            }
            CacheCleanUtil.clearAllCache(BaseApplication.appContext);
            ToastUtil.get().shortToast(R.string.cache_clean_result);
        } catch (Exception e) {
            ToastUtil.get().shortToast(R.string.cache_clean_failed);
        }
        updateCacheUI();
    }

    public void updateCacheUI() {
        try {
            cacheSize.set(CacheCleanUtil.getTotalCacheSize(BaseApplication.appContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当前版本
     *
     * @param view
     */
    public void clickCurrentVersion(View view) {
        AppConfigFetchUtils.fetchAppConfig(view.getContext());
    }

    /**
     * 用户协议
     *
     * @param view
     */
    public void clickUserProtocol(View view) {
        startActivity(new Intent(view.getContext(), WebViewActivity.class).putExtra(Constants.WEB_URL, UserInfoUtils.getCustomerUserAgreement()));
    }

    /**
     * 使用帮助
     *
     * @param view
     */
    public void clickUsingHelp(View view) {
        startActivity(new Intent(view.getContext(), WebViewActivity.class).putExtra(Constants.WEB_URL, UserInfoUtils.getCustomerHelpUrl()));
    }

    /**
     * 关于我们
     *
     * @param view
     */
    public void clickAboutUs(View view) {
        startActivity(new Intent(view.getContext(), AboutUsActivity.class));
    }

    /**
     * 客服电话
     *
     * @param view
     */
    public void clickCustomerPhone(View view) {
        ToolUtils.callPhone(view.getContext(), UserInfoUtils.getServicePhone());
    }

    /**
     * 给我们好评
     *
     * @param view
     */
    public void clickHighPraise(View view) {
        ActivityUtils.jumpToMarket(view.getContext(), AndroidInfoUtils.getPackageName());
    }

    /**
     * 我要合作
     *
     * @param view
     */
    public void clickCooperation(View view) {
        ToolUtils.callPhone(view.getContext(), UserInfoUtils.getServicePhone());
    }

    /**
     * 分享
     *
     * @param view
     */
    public void clickInviteFriend(View view) {
        // TODO: 7/3/16 分享 pengxiaofang
        ToastUtil.get().shortToast(R.string.invite_friend);
    }

    /**
     * 意见反馈
     *
     * @param view
     */
    public void clickFeedBack(View view) {
        startActivity(new Intent(view.getContext(), FeedBackActivity.class));
    }

    @Override
    public Call loadApiService() {
        return RestClient.getService().getMobileConfig();
    }

    @Override
    public void handleData(int requestCode, Object data, NHBResponse response) {
        MobileConfigBean mobileConfigBean = (MobileConfigBean) data;
        UserInfoUtils.saveMobileConfigBean(mobileConfigBean);
    }
}
