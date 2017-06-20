package com.nhb.app.manager.contract;

import android.content.Context;

import com.fast.library.BaseFragment;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.nhb.app.library.tab.ViewPageInfo;
import com.nhb.app.manager.base.BasePresenter;
import com.nhb.app.manager.base.BaseView;

import java.util.ArrayList;

/**
 * 说明：首页
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 10:00
 * <p>
 * 版本：verson 1.0
 */
public interface MainContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract ArrayList<CustomTabEntity> createTab();
        public abstract ArrayList<ViewPageInfo> createFragment();
    }
}
