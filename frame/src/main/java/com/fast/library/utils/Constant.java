package com.fast.library.utils;

import java.io.File;

/**
 * 说明：常用变量
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/26 23:57
 * <p/>
 * 版本：verson 1.0
 */
public final class Constant {

    /******************************* 文件路径 ****************************************/

    public static class FilePath{

        public static final String tor = File.separator;
        // SD卡根目录
        public static final String ROOT = SDCardUtils.getExternalStorage() + tor;
        // 默认下载路径
        public static final String FILE_DOWNLOAD = ROOT + "Download" + tor;
    }

    /******************************* 网络连接 ****************************************/
    public static class Http{
        // 网络请求超时时间 10s
        public static final int TIMEOUT = 10000;
        // 网络请求默认key
        public static final String DEFAULT_KEY = "defalut_key";
        // 网络请求方式：post
        public static final String POST = "POST";
        // 网络请求方式：get
        public static final String GET = "GET";
        // 网络请求方式：put
        public static final String PUT = "PUT";
        // 网络请求方式：delete
        public static final String DELETE = "DELETE";
        // 网络请求方式：head
        public static final String HEAD = "HEAD";
        // 网络请求方式：patch
        public static final String PATCH = "PATCH";
        // 最大下载数量
        public static final int MAX_DOWNLOAD_COUNT = 10;
    }

}

