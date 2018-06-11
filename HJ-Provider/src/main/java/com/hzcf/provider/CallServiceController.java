package com.hzcf.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.base.result.ResponseCode;
import com.hzcf.base.result.ResponseData;
import com.hzcf.service.MS_RuleEngineService;
import com.hzcf.service.MS_VariableService;
@RestController
public class CallServiceController {
	@Autowired
	MS_VariableService variableService;
	@Autowired
	MS_RuleEngineService ruleService;
	@Value("${spring.cloud.config.discovery.serviceId}")
	String serviceId;
	@Value("${eureka.client.serviceUrl.defaultZone}")
	String defaultZone;
	
	@GetMapping("/response/{id}")
	public ResponseData auth(@PathVariable Integer id){
		if(id > 0){
			return ResponseData.fail(ResponseCode.codeOf(id));
		}else{
			JSONObject request = JSONObject.parseObject("{\"taskId\":\"123\",\"ruleId\":\"321\",\"companyCode\":\"9c4f92a0ce684ccfa960ec3bb6dcc7f9\",\"service\":\"zcPublicService.blacklist\",\"param\":{\"riskItems\":[{\"riskItemValue\":\"430224198902203316\",\"riskTime\":\"2017\",\"sourceCode\":\"10\",\"riskTypeCode\":\"10\",\"riskType\":\"资料虚假类\",\"riskItemType\":\"身份证号\",\"riskItemTypeCode\":\"101010\",\"source\":\"宜信个人客户\"}]}}");
			return ResponseData.ok(request);
		}
	}
	
	@RequestMapping("/var")
    public JSONObject var(){
		System.out.println(serviceId);
		System.out.println(defaultZone);
		JSONObject request = JSONObject.parseObject("{\"taskId\":\"123\",\"ruleId\":\"321\",\"companyCode\":\"9c4f92a0ce684ccfa960ec3bb6dcc7f9\",\"service\":\"zcPublicService.blacklist\",\"param\":{\"riskItems\":[{\"riskItemValue\":\"430224198902203316\",\"riskTime\":\"2017\",\"sourceCode\":\"10\",\"riskTypeCode\":\"10\",\"riskType\":\"资料虚假类\",\"riskItemType\":\"身份证号\",\"riskItemTypeCode\":\"101010\",\"source\":\"宜信个人客户\"}]}}");
		JSONObject ret = variableService.execute(request);
		System.out.println(ret.toJSONString());
        return ret;
    }
	
	@RequestMapping("/rule")
	public JSONObject rule(){
		System.out.println(serviceId);
		System.out.println(defaultZone);
		JSONObject request = JSONObject.parseObject("{\"param\":{\"X_NEXTDATA_R02\":0,\"Z_LAWXP_R01\":0,\"X_NEXTDATA_R01\":\"\",\"X_91_R01\":0,\"X_NEXTDATA_R04\":0,\"Z_LAWXP_R03\":0,\"X_NEXTDATA_R03\":false,\"Z_LAWXP_R02\":0,\"X_NEXTDATA_R05\":\"\",\"Z_LAWXP_R04\":0,\"X_91_R02\":0,\"X_91_R03\":0,\"X_SUANHUA_R06\":0,\"X_SUANHUA_R05\":0,\"X_SUANHUA_R02\":0,\"Z_GEOTMT_R02\":false,\"Z_EMAY_R04\":\"0\",\"Z_EMAY_R01\":\"0\",\"X_SUANHUA_R08\":0,\"Z_EMAY_R03\":\"0\",\"X_SUANHUA_R07\":0,\"X_RONG360_R03\":0,\"X_RONG360_R02\":0,\"Z_FPJK_R03\":\"1\",\"X_GEOTMT_R05\":\"\",\"X_GEOTMT_R04\":\"\"},\"ruleId\":\"176fd7f763df49d2a42179eb6ab24932\",\"groupKey\":\"R_NEW_3\",\"taskId\":\"BOR20170804000626\"}");
		JSONObject ret = ruleService.rule(request);
		System.out.println(ret.toJSONString());
		return ret;
	}
}