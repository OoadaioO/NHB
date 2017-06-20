package com.nhb.app.custom.bean;

import java.io.Serializable;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-07-01 17:55
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class MemberCardBean implements Serializable{
    public String orderId;
    public String enabled;
    public String retireTime;// 过期时间
    public String createTime;// 创建时间
    public String totalUseCount;//总使用次数
    public String currentCount;//当前可使用次数
    public String itemId;//商品Id
    public String priceInStore;// 门店价格
    public String price;// 平台价格
    public String itemName;// 商品名称
    public String itemDesc;//商品描述
    public String buyCount;//购买数量
    public String itemPic;//商品图片
    public String validTime;//商品的有效期【单位：天】
    public String itemType;
    public String storeName;
    public String storePic;
    public String storePhone;
}
