package com.nhb.app.custom.bean;

import com.nhb.app.custom.common.bean.TemplateSubItem;

import java.util.List;

/**
 * Created by pxfile on 6/23/16.
 */
public class HomeDataBean {

    /**
     * 轮播
     */
    public List<CommonBannerBean> bannerList;

    /**
     * 三张特殊图片
     */
    public List<TemplateSubItem> featureList;
    /**
     * 热门商品
     */
    public List<TemplateSubItem> hotList;

    /**
     * 热搜词
     */
    public String hotSearch;

    /**
     * 定位区域Id
     */
    public String areaId;

    /**
     * 定位区域名称
     */
    public String areaName;

    /**
     * 判断是否开通
     */
    public String isOpen;
}
