package com.hzcf.operation.base.result;

import com.hzcf.common.base.enums.EnumType;

/**
 * Create by hanlin on 2017年11月6日
 **/
public enum ResponseCode implements EnumType{
	UNKNOWN(0, "未知"),
	OK(200,"成功"),
	ERROR_PARAM(400,"请求参数错误"),
	LOGIN_FAILED(411,"登录失败"),
	UNAUTHORIZED(401,"未授权"),
	PAYMENT_REQUIRED(402,"调用次数超限"),
	FORBIDDEN(403,"拒绝访问"),
	RESOURCE_NOT_FOUND(404,"请求资源未找到"),
	METHOD_NOT_ALLOWED(405,"请求方法不被允许调用"),
	NOT_ACCEPTABLE(406,"请求不可接受"),
	PROXY_AUTH_REQUIRED(407,"需要代理身份验证"),
	REQUEST_TIMEOUT (408,"请求超时"),
	CONFLICT (409,"数据冲突"),
	GONE (410,"已失效"),
	INTERNAL_SERVER_ERROR(500, "系统内部错误"),
	BAD_GATEWAY(502, "错误的网关"),
	GATEWAY_TIMEOUT(502, "网关超时")
	;
	private final int code;
    private final String text;

    private ResponseCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String text() {
        return text;
    }

    public static ResponseCode codeOf(int code) {
        for (ResponseCode value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid ResponseCode code: " + code);
    }
}
