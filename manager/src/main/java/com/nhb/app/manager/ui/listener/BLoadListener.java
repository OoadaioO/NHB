package com.nhb.app.manager.ui.listener;

/**
 * 说明：BLoadListener
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 9:23
 * <p/>
 * 版本：verson 1.0
 */
public abstract class BLoadListener<T>{
    public void onStart(){}
    /**
     * 成功获取数据
     *
     * @param result
     */
    public abstract void onSucc(T result);

    /**
     * 失败
     * @param errCode
     * @param message
     */
    public abstract void onFail(int errCode,String message);

    public void onFinish(){}
}
