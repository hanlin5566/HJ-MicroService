package com.hzcf.base.result;

import com.alibaba.fastjson.JSONObject;

/**
 * Create by hanlin on 2018年5月22日
 **/
public class ResponseData {
	private Object data;
	private int code;
	private String msg;
	
	private ResponseData(Object data, ResponseCode code) {
		super();
		this.data = data;
		this.code = code.code();
		this.msg = code.text();
	}
	
	private ResponseData(ResponseCode code) {
		super();
		//TODO:返回的是null还是{}在这指定
		this.data = new JSONObject();
		this.code = code.code();
		this.msg = code.text();
	}

	public static ResponseData ok(Object data){
		return new ResponseData(data, ResponseCode.OK);
	}
	
	public static ResponseData fail(ResponseCode code){
		return new ResponseData(code);
	}
	
	public static ResponseData failByParam(){
		return new ResponseData(ResponseCode.ERROR_PARAM);
	}
	
	public static ResponseData failByUnauthorized(){
		return new ResponseData(ResponseCode.UNAUTHORIZED);
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
