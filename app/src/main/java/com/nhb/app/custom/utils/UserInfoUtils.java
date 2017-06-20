package com.nhb.app.custom.utils;

import android.text.TextUtils;

import com.nhb.app.custom.bean.MobileConfigBean;
import com.nhb.app.custom.bean.UserLoginBean;
import com.nhb.app.custom.constant.Constants;
import com.nhb.app.custom.constant.UrlConstantsH5;

/**
 * Created by pxfile on 6/19/16.
 */
public class UserInfoUtils {

    //鉴宝登陆或者注册成功后的信息
    private static final String SP_KEY_USER_ID = "user_id";
    private static final String SP_KEY_USER_NICKNAME = "nickName";
    private static final String SP_KEY_USER_TOKEN = "token";
    private static final String SP_KEY_USER_HEAD_URL = "head";
    private static final String SP_KEY_USER_PHONE = "mobile";
    private static final String SP_KEY_SOCIAL_LOGIN_FLG = "is_social_flg";
    private static final String SP_KEY_USER_CACHE_HEAD_URL = "head_cache";//用于缓存重置头像后的新URL
    public static final String DEFAULT_USE_NOTE = "只要我们能梦想的，我们就能实现。";//

    public static final String SP_KEY_CUSTOMER_USER_AGREEMENT = "sp_key_customer_user_agreement";//用户协议h5地址;
    public static final String SP_KEY_CUSTOMER_HELP_URL = "sp_key_customer_help_url";//帮助协议
    public static final String SP_KEY_SERVICE_PHONE = "sp_key_service_phone";//客服电话

    public static void saveUserLoginInfo(UserLoginBean info) {
        if (info != null) {
            SpCustom.get().write(SP_KEY_USER_ID, info.userId);
            SpCustom.get().write(SP_KEY_USER_NICKNAME, info.userName);
            SpCustom.get().write(SP_KEY_USER_TOKEN, info.token);
            SpCustom.get().write(SP_KEY_USER_PHONE, info.mobile);
            SpCustom.get().write(SP_KEY_USER_HEAD_URL, info.headImg);
        }
    }

    public static void saveMobileConfigBean(MobileConfigBean configBean) {
        if (null != configBean) {
            SpCustom.get().write(SP_KEY_CUSTOMER_USER_AGREEMENT, configBean.customerUserAgreement);
            SpCustom.get().write(SP_KEY_CUSTOMER_HELP_URL, configBean.customerHelpUrl);
            SpCustom.get().write(SP_KEY_SERVICE_PHONE, configBean.servicePhone);
        }
    }

    public static boolean checkUserLogin() {
        String token = getUserToken();
        return !TextUtils.isEmpty(token);
    }

    public static void saveNickName(String nickName) {
        SpCustom.get().write(SP_KEY_USER_NICKNAME, nickName);
    }

    public static void setSocialLoginFlg(boolean isSocialLogin) {
        SpCustom.get().write(SP_KEY_SOCIAL_LOGIN_FLG, isSocialLogin);
    }

    public static boolean getSocialLoginFlg() {
        return SpCustom.get().readBoolean(SP_KEY_SOCIAL_LOGIN_FLG);
    }

    public static void setPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            SpCustom.get().write(SP_KEY_USER_PHONE, phone);
        }
    }

    public static void setToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SpCustom.get().write(SP_KEY_USER_TOKEN, token);
        }
    }

    public static String getPhone() {
        return SpCustom.get().readString(SP_KEY_USER_PHONE);
    }

    public static void saveCacheHeadImagePath(String path) {
        SpCustom.get().write(SP_KEY_USER_CACHE_HEAD_URL, path);
    }

    public static String getCacheHeadImagePath() {
        return SpCustom.get().readString(SP_KEY_USER_CACHE_HEAD_URL);
    }


    public static String getUserId() {
        return SpCustom.get().readString(SP_KEY_USER_ID);
    }

    public static String getUserNickName() {
        return SpCustom.get().readString(SP_KEY_USER_NICKNAME);
    }

    public static String getUserToken() {
        return SpCustom.get().readString(SP_KEY_USER_TOKEN);
    }

    public static String getUserHeadImage() {
        return SpCustom.get().readString(SP_KEY_USER_HEAD_URL);
    }

    public static void clearToken() {
        SpCustom.get().remove(SP_KEY_USER_TOKEN);
    }

    public static void clearCacheImageUrl() {
        SpCustom.get().write(SP_KEY_USER_CACHE_HEAD_URL, "");
    }

    public static String getCustomerUserAgreement() {
        return SpCustom.get().readString(SP_KEY_CUSTOMER_USER_AGREEMENT, UrlConstantsH5.USER_PROTOCOL);
    }

    public static String getCustomerHelpUrl() {
        return SpCustom.get().readString(SP_KEY_CUSTOMER_HELP_URL, UrlConstantsH5.USING_HELP);
    }

    public static String getServicePhone() {
        return SpCustom.get().readString(SP_KEY_SERVICE_PHONE, Constants.CUSTOM_PHONE_NUMBER);
    }

    public static void logOut() {
        SpCustom.get().write(SP_KEY_USER_ID, "");
        SpCustom.get().write(SP_KEY_USER_NICKNAME, "");
        SpCustom.get().write(SP_KEY_USER_TOKEN, "");
        SpCustom.get().write(SP_KEY_USER_HEAD_URL, "");
        SpCustom.get().write(SP_KEY_USER_PHONE, "");
        setSocialLoginFlg(false);
        clearCacheImageUrl();
    }
}
