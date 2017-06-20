package com.nhb.app.custom.ui.launch;

import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fast.library.tools.BackExit;
import com.fast.library.tools.BackTools;
import com.fast.library.ui.ToastUtil;
import com.fast.library.utils.MPermissionUtil;
import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseActivity;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.databinding.ActivityMainBinding;
import com.nhb.app.custom.location.LocationServiceutils;
import com.nhb.app.custom.ui.home.fragment.HomeFragment;
import com.nhb.app.custom.ui.items.fragment.ItemsFragment;
import com.nhb.app.custom.ui.others.fragment.MoreFragment;
import com.nhb.app.custom.ui.personal.fragment.PersonalFragment;
import com.nhb.app.custom.utils.UtilsManager;
import com.nhb.app.custom.viewmodel.MainTabVM;
import com.pgyersdk.update.PgyUpdateManager;

import static com.nhb.app.custom.base.BaseApplication.appContext;


public class MainActivity extends BaseActivity<MainTabVM, ActivityMainBinding> {

    public static final String HOME = "home";
    public static final String ITEMS = "items";
    public static final String PERSONAL = "personal";
    public static final String MORE = "more";

    public HomeFragment mHomeFragment;
    public ItemsFragment mItemsFragment;
    public PersonalFragment mPersonalFragment;
    public MoreFragment mMoreFragment;

    //默认选中首页
    private String mTargetMenu = HOME;

    //之所以设置为int值代替boolean是因为当用户长按home键清除应用时会清空静态变量
    public static int mainActivityDestroy;

    @Override
    protected MainTabVM loadViewModel() {
        return new MainTabVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Extras.RELAUNCH, false)) {
            return;
        }
        setUpView();
    }

    @Override
    protected void intentWithNormal(Intent intent) {
        super.intentWithNormal(intent);
        //是否需要重新加载此页面
        boolean relaunch = intent.getBooleanExtra(Extras.RELAUNCH, false);
        if (relaunch) {
            finish();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.activity_standby);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    @Override
    protected void onDestroy() {
        mainActivityDestroy = 2;
        super.onDestroy();
    }

    @Override
    protected void intentWithActionView(Uri uri) {
        super.intentWithActionView(uri);
        // TODO: 6/25/16 从通知栏进入 pengxiaofang
//        mTargetMenu = uri.getQueryParameter(Extras.Push.PARAM_PAGE);
    }


    @Override
    protected void initialize() {
        /**
         * 地图相关
         */
        LocationServiceutils.getInstance().startBaiduLocationService(this);
        MPermissionUtil.requestAllPermission(mContext, this);
        viewModel.selectedTab.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switchTab();
            }
        });
        // 默认为首页
        viewModel.selectedTab.set(mTargetMenu);
        PgyUpdateManager.register(this);
    }

    /**
     * 切换tab
     */
    private void switchTab() {
        viewDataBinding.includeMainMenu.mainRlMenuHome.setSelected(false);
        viewDataBinding.includeMainMenu.mainRlMenuItems.setSelected(false);
        viewDataBinding.includeMainMenu.mainRlMenuPersonal.setSelected(false);
        viewDataBinding.includeMainMenu.mainRlMenuMore.setSelected(false);

        if (viewModel.selectedTab.get().equals(HOME)) {
            viewDataBinding.includeMainMenu.mainRlMenuHome.setSelected(true);
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
            }
            mTargetMenu = HOME;
            replaceFragmentByTag(getFragmentLayoutId(), mHomeFragment, HOME, null);
        } else if (viewModel.selectedTab.get().equals(ITEMS)) {
            viewDataBinding.includeMainMenu.mainRlMenuItems.setSelected(true);
            if (mItemsFragment == null) {
                mItemsFragment = new ItemsFragment();
            }
            mTargetMenu = ITEMS;
            replaceFragmentByTag(getFragmentLayoutId(), mItemsFragment, ITEMS, null);
        } else if (viewModel.selectedTab.get().equals(PERSONAL)) {
            if (!isLogin()) {
                startLogin();
                return;
            }
            viewDataBinding.includeMainMenu.mainRlMenuPersonal.setSelected(true);
            if (mPersonalFragment == null) {
                mPersonalFragment = new PersonalFragment();
            }
            mTargetMenu = PERSONAL;
            replaceFragmentByTag(getFragmentLayoutId(), mPersonalFragment, PERSONAL, null);
        } else if (viewModel.selectedTab.get().equals(MORE)) {
            viewDataBinding.includeMainMenu.mainRlMenuMore.setSelected(true);
            if (mMoreFragment == null) {
                mMoreFragment = new MoreFragment();
            }
            mTargetMenu = MORE;
            replaceFragmentByTag(getFragmentLayoutId(), mMoreFragment, MORE, null);
        }
    }

    private void setUpView() {
        if (!TextUtils.isEmpty(mTargetMenu)) {
            viewModel.selectedTab.set(mTargetMenu);
        } else {
            viewModel.selectedTab.set(HOME);
        }
    }

    @IdRes
    private int getFragmentLayoutId() {
        return R.id.main_ll_content;
    }

    @Override
    public void onBackPressed() {
        BackTools.onBackPressed(new BackExit(){
            @Override
            public void showTips() {
                ToastUtil.get().shortToast(R.string.press_back_again);
            }

            @Override
            public void exit() {
                ToastUtil.get().cancelToast();
                LocationServiceutils.getInstance().stopBaiduLocationService();
                super.exit();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (MPermissionUtil.PERMISSION_REQ_CODE == requestCode) {
            UtilsManager.getInstance().setApplicationContext(appContext);
        }
    }
}
