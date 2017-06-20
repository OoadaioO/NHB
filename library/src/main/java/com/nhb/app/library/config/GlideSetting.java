package com.nhb.app.library.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.fast.library.glide.GlideConfig;

/**
 * 说明：Glide配置
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 17:15
 * <p/>
 * 版本：verson 1.0
 */
public class GlideSetting extends GlideConfig{

    @Override
    public boolean isRGB8888() {
        return true;
    }

    @Override
    public int setDiskCacheType() {
        return GlideConfig.TYPE_INNER;
    }

    @Override
    public String setDiskCacheName() {
        return Constant.IMAGE_CACHE;
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
