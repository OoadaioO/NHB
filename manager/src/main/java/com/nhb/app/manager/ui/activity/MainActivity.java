package com.nhb.app.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.fast.library.tools.BackExit;
import com.fast.library.tools.BackTools;
import com.fast.library.ui.ContentView;
import com.fast.library.utils.StringUtils;
import com.fast.mvp.loader.PresenterFactory;
import com.fast.mvp.loader.PresenterLoader;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.nhb.app.library.adapter.FragmentAdapter;
import com.nhb.app.library.event.EventCenter;
import com.nhb.app.manager.R;
import com.nhb.app.manager.base.CommonActivity;
import com.nhb.app.manager.bean.AppUpdateBean;
import com.nhb.app.manager.contract.MainContract;
import com.nhb.app.manager.dialog.AppUpdateDialog;
import com.nhb.app.manager.event.EventType;
import com.nhb.app.manager.model.AppUpdateUtils;
import com.nhb.app.manager.model.SpManager;
import com.nhb.app.manager.presenter.MainPresenterImpl;
import com.nhb.app.manager.ui.helper.MessageHelper;
import com.pgyersdk.update.PgyUpdateManager;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 说明：首页
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/17 16:21
 * <p>
 * 版本：verson 1.0
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends CommonActivity<MainContract.Presenter> implements MainContract.View, OnTabSelectListener {

    public static String TYPE_FROM_NOTIFY_TO_MESSAGE = "type_from_notify_to_message";

    @Bind(R.id.tabLayout)
    CommonTabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private FragmentAdapter adapter;
    private AppUpdateDialog mAppUpdateDialog;

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory<MainContract.Presenter>() {

            @Override
            public MainContract.Presenter create() {
                return new MainPresenterImpl();
            }
        });
    }

    @Override
    public void onInitStart() {
        super.onInitStart();
        getPresenter().attachView(this);
        adapter = new FragmentAdapter(getSupportFragmentManager(),this,getPresenter().createFragment());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setTabData(getPresenter().createTab());
        tabLayout.setOnTabSelectListener(this);
        //默认选中第一个
        onTabSelect(0);
//        应用更新
        PgyUpdateManager.register(this);
        if (getIntent() != null && !StringUtils.isEmpty(getIntent().getStringExtra(TYPE_FROM_NOTIFY_TO_MESSAGE))){
            onTabSelect(1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && !StringUtils.isEmpty(intent.getStringExtra(TYPE_FROM_NOTIFY_TO_MESSAGE))){
            onTabSelect(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        检查版本更新
        AppUpdateUtils.getInstance().checkApp(new AppUpdateUtils.OnAppUpdateListener() {
            @Override
            public void onUpdate(boolean isUpdate, AppUpdateBean bean) {
                if (isUpdate){
                    if (mAppUpdateDialog == null){
                        mAppUpdateDialog = new AppUpdateDialog(MainActivity.this);
                    }
                    mAppUpdateDialog.showDialog(bean);
                }
            }
        });
    }

    @Override
    protected int setTitleBarResID() {
        return NONE_TITLE;
    }

    @Override
    public void onTabSelect(int position) {
        if (position == 1){
//            点击消息
            MessageHelper.cleanMessage();
        }
        viewPager.setCurrentItem(position,false);
        tabLayout.setCurrentTab(position);
    }

    @Override
    public void onTabReselect(int position) {
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showMessageNum(EventCenter<Integer> event){
        if (event.type != EventType.MESSAGE_NUM)return;
        if (event.data <= 0){
            tabLayout.hideMsg(1);
        }else {
            tabLayout.showMsg(1,event.data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginOut(EventCenter<String> event){
        if (event.type == EventType.MESSAGE_LOGINOUT){
            SpManager.exit();
            showActivity(LoginActivity.class);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
