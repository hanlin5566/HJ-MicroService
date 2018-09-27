package com.hzcf.getway.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Create by hanlin on 2018年5月30日
 * 仅用于测试
 **/
@Component
public class PreZuulFilterTest extends ZuulFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static AtomicInteger queryTimes = new AtomicInteger(0);
	
	/**
	 * shouldFilter：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。
	 * 在上例中，我们直接返回true，所以该过滤器总是生效。
	 */
	@Override
	public boolean shouldFilter() {
		return false;
	}

	/*
	 * run：过滤器的具体逻辑。需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，
	 * 不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，
	 * 当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
	 */
	@Override
	public Object run() {
		long random = (long) (Math.random()*2000);
		long sleepTime = 1000+random;
		try {
			Thread.sleep(sleepTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
		String msg = String.format("queryTime:[%s],sleep:[%s],queryTimes:[%s]",sdf.format(new Date()),sleepTime,queryTimes.incrementAndGet());
		System.out.println(msg);
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.setSendZuulResponse(false);
		JSONObject ret = new JSONObject();
		ret.put("state", 2);
		ret.put("result", "{\"msg\":+\""+msg+"\"}");
		ctx.setResponseBody(ret.toJSONString());
		ctx.getResponse().setContentType("application/json; charset=utf-8");
		logger.error("前置过滤器-->路由请求之前调用。");
		return null;
	}

	/**
	 * filterType： 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
	 * pre：可以在请求被路由之前调用 routing：在路由请求时候被调用 post：在routing和error过滤器之后被调用
	 * error：处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	/**
	 * filterOrder：通过int值来定义过滤器的执行顺序
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

}
