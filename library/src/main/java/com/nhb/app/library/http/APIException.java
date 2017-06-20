package com.nhb.app.library.http;

/**
 * 说明：自定义错误
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/22 10:43
 * <p/>
 * 版本：verson 1.0
 */
public class APIException extends Throwable{

    private int code;
    private String msg;

    public APIException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
