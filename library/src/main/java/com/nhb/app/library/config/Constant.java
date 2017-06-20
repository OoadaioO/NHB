package com.nhb.app.library.config;

import com.fast.library.utils.SDCardUtils;

import java.io.File;

/**
 * 说明：常量
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 17:17
 * <p/>
 * 版本：verson 1.0
 */
public class Constant {

    public final static String APP = "NianHuiBao";

    //崩溃日志保存路径
    public final static String CRASH = SDCardUtils.getExternalStorage() + APP + File.separator + "crash" + File.separator;
    //图片缓存路径
    public final static String IMAGE_CACHE = "image";
    //超时时间
    public final static int TIME_OUT = 5000;

    public final static String ABC = "8PUzMNfNB7rGqh0D";

    public interface CardType{
        String Home = "0";//到家卡
        String Shop = "1";//到店卡
    }
}
