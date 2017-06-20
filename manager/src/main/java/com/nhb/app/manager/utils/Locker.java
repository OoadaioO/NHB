package com.nhb.app.manager.utils;

import android.text.TextUtils;

import com.fast.library.utils.Base64;
import com.fast.library.utils.DESUtils;
import com.fast.library.utils.GsonUtils;
import com.fast.library.utils.LogUtils;
import com.fast.library.utils.MD5;
import com.fast.library.utils.StringUtils;
import com.nhb.app.manager.utils.EncryptionInfo;

/**
 * 说明：加密解密工具
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 11:06
 * <p/>
 * 版本：verson 1.0
 */
public class Locker {

    private static String createSecretKey(String key){
        return MD5.getMD5Num(key,2);
    }

    /**
     * 加密二维码信息
     * @return
     */
    public static String createQrCode(String key,String orderId,String customToken){
        EncryptionInfo encryptionInfo = new EncryptionInfo(orderId,customToken);
        return encrypt(key,GsonUtils.toJson(encryptionInfo));
    }

    /**
     * 解析二维码信息
     * @param key
     * @param info
     * @return
     */
    public static EncryptionInfo obtainQrCode(String key,String info){
        EncryptionInfo encryptionInfo = null;
        try {
            String decrypt = decrypt(key,info);
            if (!StringUtils.isEmpty(decrypt)){
                encryptionInfo = GsonUtils.toBean(decrypt,EncryptionInfo.class);
            }
        }catch (Exception e){
            encryptionInfo = null;
        }
        return encryptionInfo;
    }

    /**
     * 创建唯一消费码
     * @return
     */
    public static String createUniqueId(EncryptionInfo info){
        StringBuilder builder = new StringBuilder();
        builder.append(info.orderId).append(info.createTime);
        return MD5.getMD5(builder.toString());
    }

    /**
     * 加密
     * @param key
     * @param info
     * @return
     */
    private static String encrypt(String key,String info){
        if (!TextUtils.isEmpty(info)) {
            try{
                info = Base64.encode(info.getBytes());
                info = DESUtils.encryption(info,createSecretKey(key).substring(0,8));
            }catch (Exception e){
                LogUtils.e(e);
                info = "";
            }

        }
        return info;
    }

    /**
     * 解密
     * @param encryptInfo
     * @return
     */
    private static String decrypt(String key,String encryptInfo){
        if (!TextUtils.isEmpty(encryptInfo)) {
            try{
                encryptInfo = DESUtils.decrypt(encryptInfo, createSecretKey(key).substring(0,8));
                encryptInfo = new String(Base64.decode(encryptInfo),"UTF-8");
            }catch (Exception e){
                LogUtils.e(e);
                encryptInfo = "";
            }
        }
        return encryptInfo;
    }
}
