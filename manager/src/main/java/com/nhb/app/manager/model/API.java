package com.nhb.app.manager.model;

import com.nhb.app.library.http.NHBResponse;
import com.nhb.app.manager.bean.AppUpdateBean;
import com.nhb.app.manager.bean.GoodsBean;
import com.nhb.app.manager.bean.OrderMessageBean;
import com.nhb.app.manager.bean.ParseQrCode;
import com.nhb.app.manager.bean.TradeStateBean;
import com.nhb.app.manager.bean.UserInfo;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static android.R.attr.order;

/**
 * 说明：API
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 16:01
 * <p/>
 * 版本：verson 1.0
 */
public interface API {
    /**
     * 获取服务器时间
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("time/trusttee/TimeServlet")
    Observable<NHBResponse<String>> getServerTime(@Field("name") String name);

    /**
     * B端登录
     * @param phone
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("login/StoreLogin")
    Observable<NHBResponse<UserInfo>> login(@Field("phonenum") String phone, @Field("password")String pwd, @Field("udid") String udid,@Field("appclient") String appclient);

    /**
     * B端所有的商品
     * @param storeToken
     * @param pagenumber
     * @param pagesize
     * @param pagenumber 请求商品列表类型（0：待上架 1：已上架 2：已下架）
     * @return
     */
    @FormUrlEncoded
    @POST("item/searchFunction")
    Observable<NHBResponse<ArrayList<GoodsBean>>> getHomeItems(@Field("storetoken") String storeToken,
                                                               @Field("pagenumber")String pagenumber,
                                                               @Field("pagesize") String pagesize,
                                                               @Field("searchtype") String searchtype);

    /**
     * B端所有商户订单消息
     * @param storeToken
     * @param pagenumber
     * @param pagesize
     * @return
     */
    @FormUrlEncoded
    @POST("businessCard/shopOrderMessage")
    Observable<NHBResponse<ArrayList<OrderMessageBean>>> getShopOrderMessage(@Field("storetoken") String storeToken,
                                                               @Field("pagenumber")String pagenumber,
                                                               @Field("pagesize") String pagesize);

    /**
     * B端所有的商品
     * @param storeToken
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @FormUrlEncoded
    @POST("login/changeStorePwd")
    Observable<NHBResponse<String>> updatePwd(@Field("storetoken") String storeToken,@Field("oldpwd")String oldPwd,@Field("newpwd") String newPwd);

    /**
     * B端所有的商品
     * @param phone
     * @param message
     * @return
     */
    @FormUrlEncoded
    @POST("feedback/trusttee/feedback")
    Observable<NHBResponse<String>> submitSuggestion(@Field("phone") String phone,@Field("message")String message);

    /**
     * 检查应用升级
     * @param client
     * @param version
     * @return
     */
    @FormUrlEncoded
    @POST("version/trusttee/upgradeapp")
    Observable<NHBResponse<AppUpdateBean>> appUpdate(@Field("client") String client, @Field("version")String version);

    /**
     * 使用会员卡
     * @param token
     * @param orderid
     * @param cardtype
     * @param consumecode
     * @param customtoken
     * @return
     */
    @FormUrlEncoded
    @POST("businessCard/useCard")
    Observable<NHBResponse<OrderMessageBean>> useCard(@Field("token") String token, @Field("orderid")String orderid,
                                                      @Field("cardtype")String cardtype, @Field("consumecode")String consumecode, @Field("customtoken")String customtoken);

    /**
     * 修改交易状态
     * @param token
     * @param tradeno
     * @param operationtype
     * @return
     */
    @FormUrlEncoded
    @POST("businessCard/updateTradeState")
    Observable<NHBResponse<TradeStateBean>> updateTradeState(@Field("token") String token, @Field("tradeno")String tradeno,
                                                    @Field("operationtype")String operationtype);

    /**
     * 解析二维码
     * @param cipher
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("customerCard/parseQrCode")
    Observable<NHBResponse<ParseQrCode>> parseQrCode(@Field("cipher") String cipher, @Field("token")String token);
}
