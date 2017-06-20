package com.nhb.app.library.event;

/**
 * 说明：Event事件
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 17:10
 * <p/>
 * 版本：verson 1.0
 */
public class EventCenter<T>{

    //事件类型
    public int type = -1;
    //事件数据
    public T data = null;

    public EventCenter(T data){
        this(-1,data);
    }

    public EventCenter(int type,T data){
        this.type = type;
        this.data = data;
    }
}
