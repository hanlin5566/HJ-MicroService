package com.hzcf.service.fallback;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.service.MS_AuthService;

/**
 * Create by hanlin on 2018年7月18日
 **/
@Component
public class AuthServiceFallback implements MS_AuthService{

	@Override
	public JSONObject login(JSONObject request) {
		JSONObject ret = new JSONObject();
        ret.put("success", false);
        ret.put("message", "some exception occur call fallback method.");
		return ret;
	}

}
