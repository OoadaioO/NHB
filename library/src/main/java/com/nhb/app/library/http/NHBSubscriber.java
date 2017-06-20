package com.nhb.app.library.http;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import rx.Subscriber;

/**
 * 说明：
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 11:08
 * <p/>
 * 版本：verson 1.0
 */
public abstract class  NHBSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof APIException){
            APIException exception = (APIException) e;
            onError(exception.getCode(),exception.getMessage());
        }else if (e instanceof SocketTimeoutException || e instanceof ConnectException){
            onError(NHBResponse.ERR_NETWORK,"网络连接错误");
        }else {
            onError(NHBResponse.ERR_RESPONSE_FORMAT,"网络连接错误");
        }
        onCompleted();
    }

    public abstract void onError(int code,String msg);
}
