package com.hzcf.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
/**
 * Create by hanlin on 2018年5月24日
 **/
@FeignClient(value = "HJ-Decision-Flow")
public interface MS_DecistionService {
	@RequestMapping(value = "decision", method = RequestMethod.POST)
	public JSONObject decision(JSONObject request);
	
	@RequestMapping(value = "decisiontree", method = RequestMethod.POST)
	public JSONObject decisiontree(JSONObject request);
}