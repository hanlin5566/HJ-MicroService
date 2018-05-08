package com.hzcf.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.service.MS_VariableService;
@RestController
public class CallVarController {
	@Autowired
	MS_VariableService variableService;
	@Value("${spring.cloud.config.discovery.serviceId}")
	String serviceId;
	@Value("${eureka.client.serviceUrl.defaultZone}")
	String defaultZone;
	
	@RequestMapping("/")
    public JSONObject var(){
		System.out.println(serviceId);
		System.out.println(defaultZone);
		JSONObject request = JSONObject.parseObject("{\"taskId\":\"123\",\"ruleId\":\"321\",\"companyCode\":\"9c4f92a0ce684ccfa960ec3bb6dcc7f9\",\"service\":\"zcPublicService.blacklist\",\"param\":{\"riskItems\":[{\"riskItemValue\":\"430224198902203316\",\"riskTime\":\"2017\",\"sourceCode\":\"10\",\"riskTypeCode\":\"10\",\"riskType\":\"资料虚假类\",\"riskItemType\":\"身份证号\",\"riskItemTypeCode\":\"101010\",\"source\":\"宜信个人客户\"}]}}");
		JSONObject ret = variableService.execute(request);
		System.out.println(ret.toJSONString());
        return ret;
    }
}