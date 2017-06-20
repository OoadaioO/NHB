package com.nhb.app.library.base;

import android.content.Context;
import android.os.Bundle;

import com.fast.library.BaseActivity;
import com.fast.mvp.presenter.MvpPresenter;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 说明：NHBActivity
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 16:25
 * <p/>
 * 版本：verson 1.0
 */
public abstract class NHBActivity<Presenter extends MvpPresenter> extends BaseActivity<Presenter>{

    protected Context mContext;
    protected CompositeSubscription mCompositeSubscription;
    /**
     * 在onCreate()中运行
     * @param bundle
     */
    @Override
    public void onInitCreate(Bundle bundle) {
        mContext = this;
        ButterKnife.bind(this);
        //初始化LoaderManager
        getSupportLoaderManager().initLoader(createLoaderID(),null,this);
    }

    /**
     * 解除所有的订阅者
     */
    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unSubscribe();
    }
}
