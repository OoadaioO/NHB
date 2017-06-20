package com.nhb.app.manager.utils;

/**
 * 说明：CommonUtils
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/8/20 23:53
 * <p/>
 * 版本：verson 1.0
 */
public class CommonUtils {

    /**
     * 获取消息订单的状态
     * @param tradeState
     * @return
     */
    public static String getMessageState(String tradeState){
        String msg = "";
        switch (tradeState){
            case "1":
                msg = "待确认";
                break;
            case "2":
                msg = "已确认";
                break;
            case "3":
                msg = "已取消";
                break;
            case "4":
                msg = "已完成";
                break;
        }
        return msg;
    }

}
