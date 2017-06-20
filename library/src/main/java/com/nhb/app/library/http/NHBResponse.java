package com.nhb.app.library.http;

/**
 * 回调返回数据 基本格式
 *
 * @author jie
 * @since 3.7
 */
public class NHBResponse<T> {

	/**
	 * 成功
	 */
	public static final int SUCCESS             = 0;
	/**
	 * response类型转换错误
	 */
	public static final int ERR_RESPONSE_FORMAT = 1;
	/**
	 * 网络错误
	 */
	public static final int ERR_NETWORK         = 2;
	/**
	 * 未登录
	 */
	public static final int ERR_AUTHORIZATION   = 403;

	/**
	 * 状态码
	 */
	public int    code;
	/**
	 * 返回状态信息
	 */
	public String message;
	/**
	 * 具体数据
	 */
	public T      data;

	public boolean isSuccess(){
		return code == SUCCESS;
	}
}
