package com.nhb.app.manager.model;

import com.fast.library.utils.AndroidInfoUtils;
import com.nhb.app.library.config.Constant;
import com.nhb.app.library.http.APIException;
import com.nhb.app.library.http.NHBResponse;
import com.nhb.app.library.http.ServerClient;
import com.nhb.app.manager.base.NmConstant;
import com.nhb.app.manager.bean.AppUpdateBean;
import com.nhb.app.manager.bean.GoodsBean;
import com.nhb.app.manager.bean.OrderMessageBean;
import com.nhb.app.manager.bean.ParseQrCode;
import com.nhb.app.manager.bean.TradeStateBean;
import com.nhb.app.manager.bean.UserInfo;

import java.util.ArrayList;

import retrofit2.http.Field;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 说明：ManagerAPI
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 16:01
 * <p/>
 * 版本：verson 1.0
 */
public class ManagerAPI {

    private final static API api = ServerClient.getAPI(API.class);
    private static ManagerAPI managerAPI;

    private ManagerAPI(){}

    public synchronized static ManagerAPI getAPI(){
        if (managerAPI == null){
            managerAPI = new ManagerAPI();
        }
        return managerAPI;
    }

    private <T> Observable.Transformer<NHBResponse<T>,T> applySchedulers(){
        return new Observable.Transformer<NHBResponse<T>,T>(){
            @Override
            public Observable<T> call(Observable<NHBResponse<T>> nhbResponseObservable) {
                return nhbResponseObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Func1<NHBResponse<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(NHBResponse<T> response) {
                                return flatResponse(response);
                            }
                        });
            }
        };
    }

    /**
     * 转换结果
     * @param response
     * @param <T>
     * @return
     */
    private <T> Observable<T> flatResponse(final NHBResponse<T> response){
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()){
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onNext(response.data);
                    }
                }else {
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onError(new APIException(response.code,response.message));
                    }
                    return;
                }
                if (!subscriber.isUnsubscribed()){
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 获取服务器时间
     * @param name
     * @return
     */
    public Observable<String> getServerTime(String name){
        return api.getServerTime(name)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<UserInfo> login(String phone, String pwd, String udid){
        return api.login(phone,pwd,udid,"2")
                .compose(this.<UserInfo>applySchedulers());
    }

    /**
     * 所有的商品列表
     * @return
     */
    public Observable<ArrayList<GoodsBean>> getHomeItems(int pageNumber, int searchType){
        return api.getHomeItems(SpManager.getUserInfo().token,String.valueOf(pageNumber), NmConstant.Page.SIZE,String.valueOf(searchType))
                .compose(this.<ArrayList<GoodsBean>>applySchedulers());
    }

    /**
     * 商户订单消息列表
     * @return
     */
    public Observable<ArrayList<OrderMessageBean>> getShopOrderMessage(int pageNumber){
        return api.getShopOrderMessage(SpManager.getUserInfo().token,String.valueOf(pageNumber), NmConstant.Page.SIZE)
                .compose(this.<ArrayList<OrderMessageBean>>applySchedulers());
    }

    /**
     * 修改密码
     * @return
     */
    public Observable<String> updatePwd(String oldPwd,String newPwd){
        return api.updatePwd(SpManager.getUserInfo().token,oldPwd,newPwd)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 提交反馈
     * @return
     */
    public Observable<String> suggestion(String phone,String message){
        return api.submitSuggestion(phone,message)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 检查应用升级
     * @return
     */
    public Observable<AppUpdateBean> upgradeApp(){
        return api.appUpdate("1", AndroidInfoUtils.versionName())
                .compose(this.<AppUpdateBean>applySchedulers());
    }

    /**
     * 使用会员卡
     * @return
     */
    public Observable<OrderMessageBean> useCard(String code, String customToken, String orderId){
        return api.useCard(SpManager.getUserInfo().token, orderId, Constant.CardType.Shop,code,customToken)
                .compose(this.<OrderMessageBean>applySchedulers());
    }

    /**
     * 修改交易状态
     * @return
     */
    public Observable<TradeStateBean> updateTradeState(String token,String tradeno,String operationtype){
        return api.updateTradeState(SpManager.getUserInfo().token, tradeno,operationtype)
                .compose(this.<TradeStateBean>applySchedulers());
    }

    /**
     * 解析二维码
     * @return
     */
    public Observable<ParseQrCode> parseQrCode(String cipher){
        return api.parseQrCode(cipher,SpManager.getUserInfo().token)
                .compose(this.<ParseQrCode>applySchedulers());
    }
}
