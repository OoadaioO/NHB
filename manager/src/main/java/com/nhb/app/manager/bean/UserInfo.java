package com.nhb.app.manager.bean;

import com.fast.library.bean.Pojo;
import com.google.gson.annotations.SerializedName;

/**
 * 说明：用户的token
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/7/14 16:34
 * <p>
 * 版本：verson 1.0
 */
public class UserInfo extends Pojo{
    @SerializedName("storeToken")
    public String token;
    public String phone;
    public String shopName;
    public String shopHeadImg;
}
