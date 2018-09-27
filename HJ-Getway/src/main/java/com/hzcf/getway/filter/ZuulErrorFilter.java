package com.hzcf.getway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.hzcf.base.misc.ConstantLogInfo;
import com.hzcf.base.result.ResponseCode;
import com.hzcf.base.result.ResponseData;
import com.hzcf.security.util.IPUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Create by hanlin on 2018年6月1日
 **/
@Component
public class ZuulErrorFilter extends ZuulFilter {

    private static final String ERROR_STATUS_CODE_KEY = "responseStatusCode";

    private Logger logger = LoggerFactory.getLogger(ZuulErrorFilter.class);

    public static final String DEFAULT_ERR_MSG = "系统繁忙,请稍后再试";

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.containsKey(ERROR_STATUS_CODE_KEY);
    }

    @Override
    public Object run() {       
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = ctx.getRequest();
            int statusCode = (Integer) ctx.get(ERROR_STATUS_CODE_KEY);
            String message = DEFAULT_ERR_MSG;
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
                    logger.warn("Error during filtering",e);
                }
                logger.error(ConstantLogInfo.ZUUL_ROUTER_ERROR,re.getMessage(),request.getRequestURI(),IPUtils.getIpAddress(request));
            }

            if(StringUtils.isBlank(message))message = DEFAULT_ERR_MSG;
            request.setAttribute("javax.servlet.error.status_code", statusCode);
            request.setAttribute("javax.servlet.error.message", message);

            ctx.setResponseBody(ResponseData.fail(ResponseCode.INTERNAL_SERVER_ERROR).toString());
        } catch (Exception e) {
            String error = "Error during filtering[ErrorFilter]";
            logger.error(error,e);
            ctx.setResponseBody(ResponseData.fail(ResponseCode.INTERNAL_SERVER_ERROR).toString());
        }
        return null;

    }

    private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
}