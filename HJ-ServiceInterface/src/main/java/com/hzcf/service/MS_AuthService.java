package com.hzcf.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSONObject;
/**
 * Create by hanlin on 2018年5月24日
 **/
@FeignClient(value = "HJ-Authentication")
public interface MS_AuthService {
	@PostMapping()
	public JSONObject decision(JSONObject request);
}