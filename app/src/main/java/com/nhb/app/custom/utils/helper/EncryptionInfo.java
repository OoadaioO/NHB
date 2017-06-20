package com.nhb.app.custom.utils.helper;

import com.fast.library.utils.DateUtils;

/**
 * 说明：加密信息
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 11:07
 * <p/>
 * 版本：verson 1.0
 */
public class EncryptionInfo{
//    订单号，会员卡号
    public String orderId;
//    创建二维码时间
    public String createTime;
//    用户的登录token
    public String customToken;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomToken() {
        return customToken;
    }

    public void setCustomToken(String customToken) {
        this.customToken = customToken;
    }

    public EncryptionInfo(String orderId, String customToken){
        this.orderId = orderId;
        this.customToken = customToken;
        this.createTime = DateUtils.getNowTime(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_4);
    }

    public static String getTimeFromat(){
        return DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_4;
    }

}
