package com.hzcf.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hzcf.operation.base.result.ResponseData;
import com.hzcf.security.util.JWTUtils;

import io.jsonwebtoken.Claims;
/**
 * Create by hanlin on 2018年5月24日
 **/
@Component
public class AuthenticationFilter implements Filter{
	
	@Autowired
	private JWTUtils jwtUtils;
	
	//白名单
	private List<String> whiteList =  Arrays.asList("/auth","/info");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;  
        httpResponse.setCharacterEncoding("UTF-8");    
        httpResponse.setContentType("application/json; charset=utf-8"); 
        String token = httpRequest.getHeader("Authorization");
        String uri = httpRequest.getRequestURI();
        if(whiteList.contains(uri)){
        	//白名单
        	chain.doFilter(request, response);
        }else{
        	//验证TOKEN
			if (StringUtils.isEmpty(token)) {
				PrintWriter print = httpResponse.getWriter();
				print.write(ResponseData.failByUnauthorized().toString());
				return;
			}
			try {
				Claims claims = jwtUtils.parseJWT(token);
				//TODO:无异常则能获取到token中的内容作出相应逻辑，并跳转
				claims.getSubject();
//				String uid = claims.get("uid",String.class);
				chain.doFilter(httpRequest, response);
			} 
//			catch (ExpiredJwtException e) {
//				// 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
//				httpResponse.getWriter().write(ResponseData.failByUnauthorized().toString());
//			} catch (SignatureException e) {
//				// 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
//				httpResponse.getWriter().write(ResponseData.failByUnauthorized().toString());
//			} 
			catch (Exception e) {
				httpResponse.getWriter().write(ResponseData.failByUnauthorized().toString());
			}
			return;
        }
        
	}

	@Override
	public void destroy() {
	}
	
}
