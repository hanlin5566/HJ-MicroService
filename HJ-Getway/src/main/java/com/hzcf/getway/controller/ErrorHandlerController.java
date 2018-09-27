package com.hzcf.getway.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hzcf.base.misc.ConstantLogInfo;
import com.hzcf.security.util.IPUtils;
import com.netflix.zuul.context.RequestContext;

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
//        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");  
        String message = "系统繁忙,请稍后再试";
        if (ctx.containsKey("throwable")) {
            Throwable e = (Exception) ctx.get("throwable");
            Throwable re = getOriginException(e);
            if(re instanceof java.net.ConnectException){
                message = "Real Service Connection refused";
                logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
            }else if(re instanceof java.net.SocketTimeoutException){
                message = "Real Service Timeout";
                logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
            }else if(re instanceof com.netflix.client.ClientException){
                message = re.getMessage();
                logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
            }else{
            	message = re.getMessage();
                logger.warn("Error during filtering",e);
            }
            logger.error(ConstantLogInfo.ZUUL_ROUTER_ERROR,re.getMessage(),request.getRequestURI(),IPUtils.getIpAddress(request));
        }
        JSONObject ret = new JSONObject();
        ret.put("errorReturn", message);
		ret.put("state", 1);
        return ret;
    }  
    
    private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
  
}  