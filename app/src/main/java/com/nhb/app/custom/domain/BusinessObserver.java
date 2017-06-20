package com.nhb.app.custom.domain;

import rx.Observer;

/**
 * Created by pengxiaofang on 18/5/16.
 * RXJava
 */
public abstract class BusinessObserver implements Observer {

    @Override
    public void onNext(Object data) {
        if(data instanceof NHBResponse){
           NHBResponse NHBResponse = (NHBResponse)data;
            if (NHBResponse.code == 0) {
                try {
                    onSuccess(NHBResponse.data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                onError(NHBResponse.message);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    public abstract void onSuccess(Object bean);

    public abstract void onError(String message);
}
