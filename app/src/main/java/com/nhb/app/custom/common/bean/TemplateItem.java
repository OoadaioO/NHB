package com.nhb.app.custom.common.bean;

/**
 * 模板项
 * <p/>
 * 5.9.0 created by zhangmao 2016-01-18
 */
public class TemplateItem {

    // 模板类型常量
    public static final int T1  = 10;
    public static final int T2  = 20;
    public static final int T3  = 30;
    public static final int T4A = 40;
    public static final int T4B = 41;
    public static final int T5  = 50;
    public static final int T6  = 60;
    public static final int T7  = 70;
    public static final int T9A = 90;
    public static final int T9B = 91;

    /**
     * 模板ID
     */
    public int             id;
    /**
     * 模板类型
     */
    public int             type;
    /**
     * 标题（空不显示）
     */
    public String          title;
    /**
     * 模板子项（Key:a、b、c、d、e、f、g、h、i,最多9个）
     */
    public TemplateSubItem a;
    public TemplateSubItem b;
    public TemplateSubItem c;
    public TemplateSubItem d;
    public TemplateSubItem e;
    public TemplateSubItem f;
    public TemplateSubItem g;
    public TemplateSubItem h;
    public TemplateSubItem i;

}

