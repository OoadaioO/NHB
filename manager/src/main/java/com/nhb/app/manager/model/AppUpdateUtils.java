package com.nhb.app.manager.model;

import com.fast.library.utils.AndroidInfoUtils;
import com.fast.library.utils.StringUtils;
import com.nhb.app.library.http.NHBSubscriber;
import com.nhb.app.manager.bean.AppUpdateBean;

/**
 * 说明：APP升级工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/8/20 22:47
 * <p/>
 * 版本：verson 1.0
 */
public class AppUpdateUtils {

    public static AppUpdateUtils sAppUtils;

    public interface OnAppUpdateListener{
        void onUpdate(boolean isUpdate,AppUpdateBean bean);
    }

    private AppUpdateUtils(){}

    public synchronized  static AppUpdateUtils getInstance(){
        if (sAppUtils == null){
            sAppUtils = new AppUpdateUtils();
        }
        return sAppUtils;
    }

    /**
     * 检查是否需要升级
     * @param listener
     */
    public void checkApp(final OnAppUpdateListener listener){
        if (listener == null)return;
        ManagerAPI.getAPI().upgradeApp().subscribe(new NHBSubscriber<AppUpdateBean>() {

            @Override
            public void onError(int code, String msg) {
                listener.onUpdate(false,null);
            }

            @Override
            public void onNext(AppUpdateBean bean) {
                if (bean != null && !StringUtils.isEquals(bean.version, AndroidInfoUtils.versionName())){
                    listener.onUpdate(true,bean);
                }else {
                    listener.onUpdate(false,null);
                }
            }
        });
    }

}
