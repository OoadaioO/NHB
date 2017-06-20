package com.nhb.app.manager.model;

import com.fast.library.utils.SPUtils;
import com.nhb.app.manager.bean.UserInfo;

/**
 * 说明：SpManager
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 15:49
 * <p/>
 * 版本：verson 1.0
 */
public class SpManager {

    private final static String fileName = "manager";

    private static SPUtils sp = SPUtils.getInstance(fileName);

    private SpManager(){}

    interface Key{
        String userInfo = "userinfo";//用户userinfo
        String phone = "phone";//用户手机号码
        String pwd = "pwd";//密码
    }

    public static SPUtils get(){
        return sp;
    }

    public static boolean isLogin(){
        return getUserInfo() != null;
    }

    public static void savePhone(String phone){
        sp.write(Key.phone,phone);
    }

    public static String getPhone(){
        return sp.readString(Key.phone,"");
    }
    public static void savePwd(String pwd){
        sp.write(Key.pwd,pwd);
    }

    public static String getPwd(){
        return sp.readString(Key.pwd,"");
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public static void saveUserInfo(String userInfo){
        sp.write(Key.userInfo,userInfo);
    }

    public static void exit(){
        sp.write(Key.userInfo,"");
    }

    public static UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo().toBean(sp.readString(Key.userInfo));
        return userInfo;
    }

}
