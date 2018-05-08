package com.hzcf.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value = "HJ-Rule-Engine")
public interface MS_RuleEngineService {
	@RequestMapping(value = "rule", method = RequestMethod.POST)
	public JSONObject rule(JSONObject request);
}