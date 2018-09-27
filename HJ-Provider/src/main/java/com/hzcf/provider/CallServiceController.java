package com.hzcf.provider;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.base.result.ResponseCode;
import com.hzcf.base.result.ResponseData;
import com.hzcf.provider.util.FangDai;
import com.hzcf.service.MS_VariableService;

@RestController
public class CallServiceController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MS_VariableService variableService;
	@Value("${spring.cloud.config.discovery.serviceId}")
	String serviceId;
	@Value("${eureka.client.serviceUrl.defaultZone}")
	String defaultZone;
	@Value("${project.version}")
	String version;
	@Value("${spring.application.name}")
	String appName;

	@GetMapping("/response/{id}")
	public ResponseData auth(@PathVariable Integer id) {
		if (id > 0) {
			return ResponseData.fail(ResponseCode.codeOf(id));
		} else {
			JSONObject request = JSONObject.parseObject(
					"{\"taskId\":\"123\",\"ruleId\":\"321\",\"companyCode\":\"9c4f92a0ce684ccfa960ec3bb6dcc7f9\",\"service\":\"zcPublicService.blacklist\",\"param\":{\"riskItems\":[{\"riskItemValue\":\"430224198902203316\",\"riskTime\":\"2017\",\"sourceCode\":\"10\",\"riskTypeCode\":\"10\",\"riskType\":\"资料虚假类\",\"riskItemType\":\"身份证号\",\"riskItemTypeCode\":\"101010\",\"source\":\"宜信个人客户\"}]}}");
			return ResponseData.ok(request);
		}
	}
	
	/**
	 * 
	 * @param request
	 *   type 
	 *      1:等额本金
	 *      1:等额本金
	 *      1:等额本金
	 *      1:等额本金
	 * @return
	 */
	@PostMapping("/fangdai")
	public JSONObject fangdai(@RequestBody JSONObject request) {
		JSONObject ret = new JSONObject();
		double principal = (double) request.get("principal") * 10000;
		int months = request.getIntValue("month");
		double rate = request.getDoubleValue("rate");
		int type = request.getIntValue("type");
		switch (type) {
		case 1:
			//计算等额本金还款
			ret = FangDai.calculateEqualPrincipal(principal, months, rate);
			break;
		case 2:
			//计算等额本息还款
			ret = FangDai.calculateEqualPrincipalAndInterest(principal, months, rate);
			break;
		case 3:
			//计算等额本金还款详情
			ret = FangDai.calculateEqualPrincipalDetail(principal, months, rate);
			break;
		case 4:
			//计算等额本息还款详情
			ret = FangDai.calculateEqualPrincipalDetail(principal, months, rate);
			break;

		default:
			break;
		}
		return ret;
	}

	@RequestMapping("/var")
	public JSONObject var() {
		System.out.println(serviceId);
		System.out.println(defaultZone);
		JSONObject request = JSONObject.parseObject(
				"{\"taskId\":\"123\",\"ruleId\":\"321\",\"companyCode\":\"9c4f92a0ce684ccfa960ec3bb6dcc7f9\",\"service\":\"zcPublicService.blacklist\",\"param\":{\"riskItems\":[{\"riskItemValue\":\"430224198902203316\",\"riskTime\":\"2017\",\"sourceCode\":\"10\",\"riskTypeCode\":\"10\",\"riskType\":\"资料虚假类\",\"riskItemType\":\"身份证号\",\"riskItemTypeCode\":\"101010\",\"source\":\"宜信个人客户\"}]}}");
		JSONObject ret = variableService.execute(request);
		System.out.println(ret.toJSONString());
		return ret;
	}

	@RequestMapping("/ver")
	public JSONObject version() {
		JSONObject ret = JSONObject.parseObject("{\"version\":\"" + version + "\",\"serviceId\":\"" + serviceId
				+ "\",\"appName\":\"" + appName + "\"}");
		return ret;
	}

	
	@RequestMapping("/startThread")
	public String startThread() {
		for (int i = 1; i < 6; i++) {
			try {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String threadName = Thread.currentThread().getName();
						try {
							//100秒内随机数
							long time = Math.round((Math.random()*100))*1000;
							Thread.sleep(time);
							logger.info("[当前线程] - [{}],休眠[{}] ms", threadName,time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				t.setName("线程" + i);
				t.start();
				Thread.sleep(i * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("[启动线程] - [{}]", i);
		}
		
		return ResponseData.ok("成功").toString();
	}
	
	public AtomicInteger started = new AtomicInteger();
    public AtomicInteger ended = new AtomicInteger();
    
    @GetMapping("/longTimeTask")
    public String longTimeTask() {
        System.out.println( Thread.currentThread().getName() + " -> " + this + " Get one, got: " + started.addAndGet(1) );
        // 模拟一个执行时间很长的任务
        //100万内随机数
        long time = Math.round(((Math.random()*500)+10)*10000);
        logger.info("[执行综述] - [{}]", time);
        //循环打印直到0停止
        for (long j = time; j >=0 ; j--) {
        	if(j%10==0){
        		logger.info("[执行到] - [{}]", j);
        	}
        }
        System.out.println( Thread.currentThread().getName() + " -> " + this + "  Finish one, finished: " + ended.addAndGet(1) );
        return ResponseData.ok("成功").toString();
    }
}