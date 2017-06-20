package com.nhb.app.custom.utils;

import com.fast.library.utils.GsonUtils;
import com.fast.library.utils.SPUtils;
import com.fast.library.utils.StringUtils;
import com.nhb.app.custom.bean.OrderDetailBean;

/**
 * 说明：SharedPreferences(C端)
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/20 16:35
 * <p/>
 * 版本：verson 1.0
 */
public class SpCustom {

    public static final String fileName = "custom";

    private static final SPUtils sp = SPUtils.getInstance(fileName);

    interface KEY{
        //是否显示引导页
        String GUIDE_PAGE_VERSION = "guide_page_version";
        //用户支付成功后订单信息
        String PAY_SUCCESS_ORDER_INFO = "pay_success_order_info";
    }

    public static SPUtils get(){
        return sp;
    }

    public static void setShowGuidePage(boolean value){
        sp.write(KEY.GUIDE_PAGE_VERSION,value);
    }

    public static boolean isShowGuidePage(){
        return sp.readBoolean(KEY.GUIDE_PAGE_VERSION,true);
    }

    public static void setOrderDeatailInfo(OrderDetailBean bean){
        if (bean != null){
            sp.write(KEY.PAY_SUCCESS_ORDER_INFO, GsonUtils.toJson(bean));
        }
    }

    public static OrderDetailBean getOrderDetailBean(){
        String info = sp.readString(KEY.PAY_SUCCESS_ORDER_INFO);
        if (!StringUtils.isEmpty(info)){
            return GsonUtils.toBean(info,OrderDetailBean.class);
        }else {
            return null;
        }
    }

}
