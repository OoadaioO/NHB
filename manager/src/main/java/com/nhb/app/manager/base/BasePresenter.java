package com.nhb.app.manager.base;

import com.fast.mvp.presenter.MvpPresenter;

/**
 * 说明：BasePresenter
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/16 10:53
 * <p>
 * 版本：verson 1.0
 */
public abstract class BasePresenter<T extends BaseView> implements MvpPresenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    @Override
    public T getMvpView() {
        return mMvpView;
    }

    @Override
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    @Override
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException{
        public MvpViewNotAttachedException(){
            super("请先在请求Presenter数据之前调用attachView()");
        }
    }

}
