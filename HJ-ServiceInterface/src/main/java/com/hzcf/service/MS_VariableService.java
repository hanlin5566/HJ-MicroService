package com.hzcf.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value = "HJ-Variable")
public interface MS_VariableService {
	@RequestMapping(value = { "/var" }, method = RequestMethod.POST)
	public JSONObject execute(JSONObject request);
}
