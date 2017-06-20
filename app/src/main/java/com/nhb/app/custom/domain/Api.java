package com.nhb.app.custom.domain;

import com.nhb.app.custom.bean.AddressBean;
import com.nhb.app.custom.bean.AppConfig;
import com.nhb.app.custom.bean.AreaCityBean;
import com.nhb.app.custom.bean.HomeDataBean;
import com.nhb.app.custom.bean.ItemsItemBean;
import com.nhb.app.custom.bean.MemberCardBean;
import com.nhb.app.custom.bean.MobileConfigBean;
import com.nhb.app.custom.bean.OrderBean;
import com.nhb.app.custom.bean.OrderDetailBean;
import com.nhb.app.custom.bean.ProvinceBean;
import com.nhb.app.custom.bean.SelectAreaBean;
import com.nhb.app.custom.bean.ShopCategoryGroupBean;
import com.nhb.app.custom.bean.UserBasicInfoBean;
import com.nhb.app.custom.bean.UserLoginBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * <br>***********************************************************************
 * <br> Author:pengxiaofang
 * <br> CreateData:2016-03-03 11:48
 * <br> Version:1.0
 * <br> Description:接口描述
 * <br> 注意:注解中路径不要以"/"开头,以便于我们以后可以给BaseUrl中增加统一路径,原因如下:
 * <p/>
 * <br>        # Good Practise
 * <br>        base url: https://futurestud.io/api/
 * <br>        endpoint: my/endpoint
 * <br>        Result:   https://futurestud.io/api/my/endpoint
 * <p/>
 * <br>        # Bad Practise1
 * <br>        base url: https://futurestud.io/api
 * <br>        endpoint: /my/endpoint
 * <br>        Result:   https://futurestud.io/my/endpoint
 * <p/>
 * <br>        # Bad Practise2
 * <br>        base url: https://futurestud.io/api/
 * <br>        endpoint: /my/endpoint
 * <br>        Result:   https://futurestud.io/my/endpoint
 * <p/>
 * <br>        # Bad Practise3
 * <br>        base url: https://futurestud.io/api
 * <br>        endpoint: my/endpoint
 * <br>        Result:   https://futurestud.io/my/endpoint
 * <p/>
 * <br>***********************************************************************
 */
public interface Api {

    /**
     * 获取首页数据
     */
    @FormUrlEncoded
    @POST("webservice/customerInit/homePageInfo")
    Call<NHBResponse<HomeDataBean>> getHomeData(@Field("areaname") String areacode, @Field("location") String locationName);
    /**
     * ==============================pengxiaofang=============================
     */

    /**
     * 获取消息通知
     *
     * @return
     */
    @GET("webservice/customerPushMess/getMessageList")
    Call<NHBResponse<List<String>>> getMsgNoticeData(@Query("token") String token, @Query("pagenumber") String pagenumber);

    /**
     * 筛选商品
     */
    @GET("webservice/customerShop/itemCategory")
    Call<NHBResponse<List<ShopCategoryGroupBean>>> getFilterItems();

    /**
     * 用户基本信息
     */
    @GET("webservice/customerInfo/getUserInfo")
    Call<NHBResponse<UserBasicInfoBean>> getPersonalInfo(@Query("token") String token);

    /**
     * 退出登录
     *
     * @return
     */
    @GET("webservice/customerInfo/Logout")
    Call<NHBResponse<String>> toLogOut(@Query("token") String token);

    /**
     * 我的订单
     *
     * @return
     */
    @GET("webservice/customerOrder/orderList")
    Call<NHBResponse<List<OrderBean>>> getPersonalOrder(@Query("token") String token);

    /**
     * 订单详情
     *
     * @return
     */
    @GET("webservice/customerOrder/orderDetail")
    Call<NHBResponse<OrderDetailBean>> getOrderDetail(@Query("token") String token, @Query("orderid") String orderid);

    /**
     * 地址列表
     *
     * @return
     */
    @GET("webservice/customerAttach/MyAddress")
    Call<NHBResponse<List<AddressBean>>> getAddressData(@Query("token") String token);

    /**
     * 设置默认地址
     *
     * @param token
     * @param addressid
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerAttach/setDefaultAddress")
    Call<NHBResponse<String>> setDefaultAddress(@Field("token") String token, @Field("addressid") String addressid);

    /**
     * 添加/修改地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerAttach/AddAddress")
    Call<NHBResponse<String>> addAddress(@Field("token") String token, @Field("addressid") String addressId, @Field("realname") String realName, @Field("gender") String gender, @Field("phone") String mobile, @Field("address") String address, @Field("tag") String tag);

    /**
     * 删除地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerAttach/deleteAddress")
    Call<NHBResponse<String>> deleteAddress(@Field("token") String token, @Field("addressid") String addressId);

    /**
     * 用户收藏
     *
     * @return
     */
    @GET("webservice/customerAttach/MyFavorite")
    Call<NHBResponse<List<ItemsItemBean>>> getCollection(@Query("token") String token, @Query("pagenumber") String pagenumber);

    /**
     * 删除/添加收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerShop/starItem")
    Call<NHBResponse<String>> operateCollection(@Field("token") String token, @Field("itemid") String itemId, @Field("operation") String operation);

    /**
     * 获取会员卡
     *
     * @return
     */
    @GET("webservice/customerCard/myCard")
    Call<NHBResponse<List<MemberCardBean>>> getPersonalCard(@Query("token") String token, @Query("cardtype") String cardtype);

    /**
     * 使用会员卡
     */
    @FormUrlEncoded
    @POST("webservice/customerCard/useCard")
    Call<NHBResponse<String>> useMemberCard(@Field("token") String token, @Field("orderid") String orderId, @Field("addreddid") String addreddId, @Field("cardtype") String cardType, @Field("consumecode") String consumeCode, @Field("remark") String markedWords);

    /**
     * app配置相关信息
     */
    @GET("webservice/version/trusttee/upgradeapp")
    Call<NHBResponse<AppConfig>> toGetAppConfig(@Query("client") String client, @Query("version") String version);

    /**
     * 下载h5
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    /**
     * 注册登录
     */
    @GET("webservice/customerInfo/Login")
    Call<NHBResponse<UserLoginBean>> toLogin(@Query("phonenum") String mobile, @Query("validatecode") String authcode, @Query("clientid") String clientID, @Query("appclient") String appclient);

    /**
     * 切换城市
     */
    @GET("webservice/area/getAllOpenCity")
    Call<NHBResponse<List<ProvinceBean>>> getCity();

    /**
     * 根据区域Id获得区域
     */
    @GET("webservice/area/getTown")
    Call<NHBResponse<SelectAreaBean>> getArea(@Query("areaid") String areaId);

    /**
     * 根据城市id获取区域
     *
     * @param areaId
     * @return
     */
    @GET("webservice/area/getProvinceCity")
    Call<NHBResponse<AreaCityBean>> getAreaByCity(@Query("areaid") String areaId);

    /**
     * 搜索自动补全
     */
    @GET("webservice/customerInit/getRemindMess")
    Call<NHBResponse<List<String>>> toGetKeywords(@Query("keyword") String keyword);

    /**
     * 搜索 商品
     *
     * @since 1.6.0
     */
    @GET("webservice/customerShop/searchFunction")
    Call<NHBResponse<List<ItemsItemBean>>> toGetSearchTopicList(@Query("areacode") String areacode, @Query("searchcontent") String searchcontent, @Query("searchtype") String searchtype, @Query("goodscategoryid") String goodscategoryid, @Query("sorttype") String sorttype, @Query("pagenumber") String pagenumber);

    /**
     * 创建订单
     *
     * @param itemId
     * @param buyCount
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerOrder/createOrder")
    Call<NHBResponse<String>> createOrder(@Field("itemid") String itemId, @Field("buycount") String buyCount, @Field("token") String token);
    /**
     * ==============================fayan==================================
     */

    /**
     * 预支付
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/customerOrder/payOrder")
    Call<NHBResponse<String>> payOrder(@Field("token") String token, @Field("orderid") String orderid,@Field("channel") String channel);

    /**
     * 应用配置
     *
     * @return
     */
    @GET("webservice/customerConfig/mobileConfig")
    Call<NHBResponse<MobileConfigBean>> getMobileConfig();


    /**
     * 创建二维码
     *
     * @param token
     * @param orderid
     * @return
     */
    @GET("webservice/customerCard/createQrCode")
    Call<NHBResponse<String>> getQRCodeStr(@Query("token") String token, @Query("orderid") String orderid);

    /**
     * 意见反馈
     *
     * @param message
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/feedback/trusttee/feedback")
    Call<NHBResponse<String>> submitFeedBack(@Field("message") String message, @Field("phone") String phone);
}