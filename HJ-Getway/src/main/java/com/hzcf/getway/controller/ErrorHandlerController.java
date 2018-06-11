package com.hzcf.getway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzcf.base.misc.ConstantLogInfo;
import com.hzcf.base.result.ResponseCode;
import com.hzcf.base.result.ResponseData;
import com.hzcf.security.util.IPUtils;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * Create by hanlin on 2018年6月5日
 **/
@RestController  
public class ErrorHandlerController implements ErrorController {  
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    /** 
     * 出异常后进入该方法，交由下面的方法处理 
     */  
    @Override  
    public String getErrorPath() {  
        return "/error";  
    }  
  
    @RequestMapping("/error")  
    public Object error(HttpServletRequest request, HttpServletResponse response){  
    	RequestContext ctx = RequestContext.getCurrentContext(); 
        ZuulException exception = (ZuulException)ctx.getThrowable(); 
        logger.error(ConstantLogInfo.ZUUL_ROUTER_ERROR,exception.getCause(),exception.errorCause,IPUtils.getIpAddress(request));
        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");  
        ResponseData fail = ResponseData.fail(ResponseCode.codeOf(status));
        fail.setData(exception.errorCause);
        return fail;
    }  
  
}  