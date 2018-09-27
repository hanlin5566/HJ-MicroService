package com.hanson.gray.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class MetadataAwareZuulFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		Map<String,String> context = new HashMap<String,String>();
		Enumeration<String> enums  = request.getHeaderNames();
        while(enums.hasMoreElements())
        {
            //请求头
            String key =  enums.nextElement();
            Enumeration<String> enums2 = request.getHeaders(key);
            while(enums2.hasMoreElements())
            {
                String value = enums2.nextElement();
                context.put(key, value);
            }
        }
		RequestContext.getCurrentContext().set(FilterConstants.LOAD_BALANCER_KEY,context);
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
