package com.nhb.app.manager;

import com.fast.library.glide.GlideLoader;
import com.nhb.app.library.NHBApplication;
import com.nhb.app.manager.view.EmptyView;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * 说明：BaseApplication
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 16:20
 * <p/>
 * 版本：verson 1.0
 */
public class BaseApplication extends NHBApplication{

    private static BaseApplication sApplication = null;

    /**
     * 获取BaseApplication
     * @return
     */
    public static BaseApplication getApplication(){
        return sApplication;
    }
    @Override
    public void onInit() {
        sApplication = this;
        PgyCrashManager.register(this);
        EmptyView.setDefaultNetErrorIcon(R.drawable.network_error);
        GlideLoader.setGlobleHolder(R.drawable.icon_gray_logo,R.drawable.icon_gray_logo);
    }

}
