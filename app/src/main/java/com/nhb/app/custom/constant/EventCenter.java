package com.nhb.app.custom.constant;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-09-03 18:10
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class EventCenter<T> {

    public EventCenter(String type, T data) {
        this.type = type;
        this.data = data;
    }

    /**
     *

     */
    public String type;
    /**
     *
     */
    public T data;
}
