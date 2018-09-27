package com.hanson.gray.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netflix.zuul.context.RequestContext;
/**
 * @author Hanson
 * 提取header中的信息，放入RequestContext供HansonRule进行动态路由
 */
@Component
public class MetadataAwareInteceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
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
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
