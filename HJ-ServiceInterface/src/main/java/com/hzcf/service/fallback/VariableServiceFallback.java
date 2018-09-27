package com.hzcf.service.fallback;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.service.MS_VariableService;

/**
 * Create by hanlin on 2018年7月18日
 **/
@Component
public class VariableServiceFallback implements MS_VariableService{

	@Override
	public JSONObject execute(JSONObject request) {
		JSONObject ret = new JSONObject();
		ret.put("success", false);
        ret.put("message", "Variable service is busy,Please try again later.");
		return ret;
	}
}
