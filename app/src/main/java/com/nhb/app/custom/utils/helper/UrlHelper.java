package com.nhb.app.custom.utils.helper;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 商品详情帮助类
 * Created by fanly on 2016/8/6.
 */

public class UrlHelper {

    public final static String NORMAL = "http";//普通链接
    public final static String TEL = "nianhuibao://tel?";//电话
    public final static String STAR = "nianhuibao://star?";//收藏
    public final static String SHARE = "nianhuibao://share?";//分享
    public final static String BUY = "nianhuibao://buy?";//抢购
    public final static String BACK= "nianhuibao://back";//抢购

    public final static String TOKEN= "nianhuibao://token";//获取token
    public final static String LOGIN= "nianhuibao://login";//去登录


    public interface UrlType{
        int UNKNOWN = -1;//未知类型
        int NORMAL = 0;//普通链接
        int TEL = 1;//电话
        int STAR = 2;//收藏
        int SHARE = 3;//分享
        int BUY = 4;//抢购
        int BACK = 5;//返回
    }

    /**
     * 获取操作类型
     * @param url
     * @return
     */
    public static int getUrlType(String url){
        int type = UrlType.UNKNOWN;
        if (!TextUtils.isEmpty(url)){
            if (url.startsWith(NORMAL)){
                type = UrlType.NORMAL;
            }else if (url.startsWith(TEL)){
                type = UrlType.TEL;
            }else if (url.startsWith(STAR)){
                type = UrlType.STAR;
            }else if (url.startsWith(SHARE)){
                type = UrlType.SHARE;
            }else if (url.startsWith(BUY)){
                type = UrlType.BUY;
            }else if (url.startsWith(BACK)){
                type = UrlType.BACK;
            }
        }
        return type;
    }

    /**
     * 获取Url数据
     * @param url
     * @return
     */
    public static String getUrlData(String url){
        if (TextUtils.isEmpty(url)){
            return "";
        }
        int index = url.indexOf("=") + 1;
        if (index > 1){
            return URLDecoder.decode(url.substring(index));
        }
        return "";
    }
}
