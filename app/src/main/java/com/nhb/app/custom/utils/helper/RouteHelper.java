package com.nhb.app.custom.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fast.library.utils.UIUtils;
import com.nhb.app.custom.R;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.Extras;
import com.nhb.app.custom.ui.items.CreateOrderActivity;
import com.nhb.app.custom.ui.items.ItemsDetailActivity;
import com.nhb.app.custom.ui.items.UserPayActivity;
import com.nhb.app.custom.ui.items.UserPaySuccessActivity;
import com.nhb.app.custom.ui.personal.LoginActivity;
import com.nhb.app.custom.utils.ResourceUtil;
import com.nhb.app.library.config.Constant;

/**
 * Created by fanly on 2016/8/6.
 */
public class RouteHelper {

    private static RouteHelper mInstance = new RouteHelper();

    public static RouteHelper getInstance() {
        return mInstance;
    }

    private RouteHelper(){}

    /**
     * 跳转商品详情
     * @param activity
     * @param itemId 商品ID
     */
    public void startItemDetail(Activity activity,String itemId){
        Intent intent = new Intent(activity,ItemsDetailActivity.class);
        intent.putExtra(Constants.ITEM_ID, itemId);
        intent.putExtra(Constants.WEB_URL, String.format(UIUtils.getString(R.string.base_url)+ResourceUtil.getString(R.string.item_detail_url),itemId));
        intent.putExtra(Constants.WEB_TITLE, ResourceUtil.getString(R.string.items_detail));
        activity.startActivity(intent);
    }

    /**
     * 跳转确认订单
     * @param activity
     * @param itemId 商品ID
     * @param itemName 商品名称
     * @param itemPrice 商品价钱
     */
    public void startCreateOrder(Activity activity,String itemId,String itemName,String itemPrice){
        Intent intent = new Intent(activity,CreateOrderActivity.class);
        intent.putExtra(Extras.ITEM_ID, itemId);
        intent.putExtra(Extras.ITEM_NAME, itemName);
        intent.putExtra(Extras.ITEM_PRICE, itemPrice);
        activity.startActivity(intent);
    }

    /**
     * 跳转收银台
     * @param activity
     * @param itemName 商品名称
     * @param itemPrice 商品价钱
     */
    public void startUserPay(Context activity, String itemName, String itemPrice, String orderId) {
        Intent intent = new Intent(activity, UserPayActivity.class);
        intent.putExtra(Extras.ORDER_ITEM_NAME, itemName);
        intent.putExtra(Extras.ORDER_PRICE, itemPrice);
        intent.putExtra(Extras.ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    /**
     * 跳转支付完成页面
     * @param activity
     * @param orderId 订单号
     */
    public void startPaySuccess(Activity activity,String orderId){
        Intent intent = new Intent(activity,UserPaySuccessActivity.class);
        intent.putExtra(Extras.ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    /**
     * 跳转登录页面
     * @param activity
     */
    public void startLogin(Activity activity){
        Intent intent = new Intent(activity,LoginActivity.class);
        activity.startActivity(intent);
    }


}
