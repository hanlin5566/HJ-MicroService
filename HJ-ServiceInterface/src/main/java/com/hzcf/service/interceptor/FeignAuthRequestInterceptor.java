package com.hzcf.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import com.hzcf.security.util.InnerTokenUtils;

/**
 * Create by hanlin on 2018年5月24日 
 * feign统一拦截器，供内部请求时将tokenset进header
 **/
public class FeignAuthRequestInterceptor implements RequestInterceptor {
	@Autowired
	InnerTokenUtils innerToken;

	@Override
	public void apply(RequestTemplate template) {
		template.header("Authorization", innerToken.getToken());
	}

}
