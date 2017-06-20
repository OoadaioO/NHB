package com.nhb.app.library.base;

import android.os.Bundle;
import android.view.View;

import com.fast.library.view.BaseLazyFragment;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 说明：NHBFragment
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/23 9:35
 * <p>
 * 版本：verson 1.0
 */
public abstract class NHBFragment extends BaseLazyFragment{

    protected CompositeSubscription mCompositeSubscription;

    @Override
    protected void onInitCreate(Bundle savedInstanceState, View view) {
        ButterKnife.bind(this,view);
        if (isRegisterEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 注册EventBus
     * @return
     */
    protected boolean isRegisterEventBus(){
        return false;
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
    public void onDestroyView() {
        ButterKnife.unbind(this);
        unSubscribe();
        if (isRegisterEventBus()){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }
}
