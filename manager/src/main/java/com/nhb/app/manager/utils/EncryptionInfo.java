package com.nhb.app.manager.utils;

import android.os.Parcel;
import android.os.Parcelable;

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
public class EncryptionInfo implements Parcelable{
//    订单号，会员卡号
    public String orderId;
//    创建二维码时间
    public String createTime;
//    用户的登录token
    public String customToken;

    protected EncryptionInfo(Parcel in) {
        orderId = in.readString();
        createTime = in.readString();
        customToken = in.readString();
    }

    public static final Creator<EncryptionInfo> CREATOR = new Creator<EncryptionInfo>() {
        @Override
        public EncryptionInfo createFromParcel(Parcel in) {
            return new EncryptionInfo(in);
        }

        @Override
        public EncryptionInfo[] newArray(int size) {
            return new EncryptionInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(createTime);
        dest.writeString(customToken);
    }
}
