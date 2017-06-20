package com.nhb.app.custom.domain;

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
	 * 文件读写错误
	 */
	public static final int ERR_IO				= 3;
	/**
	 * 未登录
	 */
	public static final int ERR_AUTHORIZATION   = 403;

	/**
	 * 非0  异常处理
	 */
	public int code;
	/**
	 * code==1时，需要判断这个
	 */
	public int    error_code;
	/**
	 * 返回状态信息
	 */
	public String message;
	/**
	 * 具体数据
	 */
	public T      data;

}
