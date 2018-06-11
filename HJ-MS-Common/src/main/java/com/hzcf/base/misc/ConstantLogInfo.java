package com.hzcf.base.misc;
/**
 * Create by hanlin on 2018年5月31日
 * 使用sl4j的format占位符格式{}
 **/
public class ConstantLogInfo {
	/**
	 * TOKEN 验证失败信息
	 */
	public static final String TOEKN_ERROR = "token验证失败,请求地址[{}],客户端IP[{}]。";
	public static final String INTERNAL_SERVER_ERROR = "内部请求发生{}错误,请求地址[{}],客户端IP[{}]。";
	public static final String ZUUL_ROUTER_ERROR = "网关请求发生{}错误,网关[{}],客户端IP[{}]。";
}
