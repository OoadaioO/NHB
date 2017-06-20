package com.nhb.app.custom.domain;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.nhb.app.custom.constant.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-21 15:49
 * <p> Version: 1.0
 * <p> Description: 主要设置Picasso图片缓存路基及获取Picasso实例
 * <p>
 * <p>***********************************************************************
 */
public class PicassoManager {

    /**
     * PicassoManager实例
     */
    private static PicassoManager singleton;
    /**
     * 自定义图片缓存路径
     */
    private OkHttp3Downloader downloader;
    /**
     * 网络库
     */
    private OkHttpClient okHttpClient;
    /**
     * Picasso实例
     *
     * @see Picasso
     */
    private Picasso picasso;

    private PicassoManager() {

    }

    /**
     * 获取PicassoManager实例
     *
     * @return
     */
    public static PicassoManager getInstance() {
        if (singleton == null) {
            synchronized (PicassoManager.class) {
                if (singleton == null) {
                    singleton = new PicassoManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取Picasso实例
     *
     * @param context
     * @return
     */
    public static Picasso with(Context context) {
        if (singleton == null) {
            synchronized (PicassoManager.class) {
                if (singleton == null) {
                    singleton = new PicassoManager();
                }
            }
        }
        return singleton.getPicasso(context);
    }

    /**
     * 获取Picasso实例
     *
     * @param context
     * @return
     */
    private Picasso getPicasso(Context context) {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder().cache(new Cache(new File(Constants.CacheKey.PIC_CACHE), Integer.MAX_VALUE)).build();
        }
        if (null == downloader) {
            downloader = new OkHttp3Downloader(okHttpClient);
        }
        if (null == picasso) {
            picasso = new Picasso.Builder(context.getApplicationContext()).downloader(downloader).build();
            // 设置Picasso使用单例模式
            Picasso.setSingletonInstance(picasso);
        }
        return picasso;
    }


    /**
     * 清除本地图片缓存
     */
    public void clearDiskCache() {
        if (null == okHttpClient) {
            return;
        }
        try {
            okHttpClient.cache().delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地图片缓存路径
     *
     * @return
     */
    public File diskCacheDirectory() {
        if (null == okHttpClient) {
            return null;
        }
        return okHttpClient.cache().directory();
    }
}
