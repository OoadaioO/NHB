package com.nhb.app.manager.event;

/**
 * 说明：EventBus事件类型
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/6/27 14:32
 * <p>
 * 版本：verson 1.0
 */
public interface EventType {
    int MESSAGE_NUM = 0;//消息数量
    int MESSAGE_LOGINOUT = 1;//退出登录
    int MESSAGE_REFRESH = 2;//刷新消息列表
}
