package com.nhb.app.manager.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.fast.library.utils.StringUtils;
import com.nhb.app.library.config.Constant;

/**
 * 说明：订单消息
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 9:22
 * <p/>
 * 版本：verson 1.0
 */
public class OrderMessageBean implements Parcelable{

//    private String myPhone;//B端用户手机号码
    private String customPhone;//C端用户手机号码
    private String customPic;//C端用户的头像
    private String customName;//C端用户的姓名
    private String orderId;//订单号
    private String tradeNo;//交易流水号
    private String tradeState;//交易状态

    private String address;//用户的地址
    private String itemPic;//商品的图片
    private String itemName;//商品的名称
    private String itemPrice;//商品的价钱
    private String cardType;//卡片类型[1:到家卡 0:到点卡]
    private String createTime;//卡片类型[1:到家卡 0:到点卡]
    private String remark;//用户留言

    public OrderMessageBean(){}

    protected OrderMessageBean(Parcel in) {
        customPhone = in.readString();
        customPic = in.readString();
        customName = in.readString();
        orderId = in.readString();
        tradeNo = in.readString();
        tradeState = in.readString();
        address = in.readString();
        itemPic = in.readString();
        itemName = in.readString();
        itemPrice = in.readString();
        cardType = in.readString();
        createTime = in.readString();
        remark = in.readString();
    }

    public static final Creator<OrderMessageBean> CREATOR = new Creator<OrderMessageBean>() {
        @Override
        public OrderMessageBean createFromParcel(Parcel in) {
            return new OrderMessageBean(in);
        }

        @Override
        public OrderMessageBean[] newArray(int size) {
            return new OrderMessageBean[size];
        }
    };

    public String getTradeMsg(){
        String msg = "";
        if (!isClick()){
            msg = "已消费";
        }else {
            switch (tradeState){
                case "1":
                    msg = "待确认";
                    break;
                case "2":
                    msg = "已确认";
                    break;
                case "3":
                    msg = "已取消";
                    break;
                case "4":
                    msg = "已完成";
                    break;
            }
        }
        return msg;
    }

    public boolean isClick(){
        return StringUtils.isEquals(Constant.CardType.Home,cardType);
    }

    public String getCustomPhone() {
        return customPhone;
    }

    public void setCustomPhone(String customPhone) {
        this.customPhone = customPhone;
    }

    public String getCustomPic() {
        return customPic;
    }

    public void setCustomPic(String customPic) {
        this.customPic = customPic;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return StringUtils.isEmpty(remark) ? "无" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(customPhone);
        parcel.writeString(customPic);
        parcel.writeString(customName);
        parcel.writeString(orderId);
        parcel.writeString(tradeNo);
        parcel.writeString(tradeState);
        parcel.writeString(address);
        parcel.writeString(itemPic);
        parcel.writeString(itemName);
        parcel.writeString(itemPrice);
        parcel.writeString(cardType);
        parcel.writeString(createTime);
        parcel.writeString(remark);
    }
}
