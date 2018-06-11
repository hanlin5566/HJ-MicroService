package com.hzcf.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hzcf.service.interceptor.FeignAuthRequestInterceptor;

import feign.Logger;

/**
 * Create by hanlin on 2018年5月24日
 **/
@Configuration
public class FeignConfiguration {
	 /**
     * 日志级别
     * @return
     */
    @Bean  
    Logger.Level feignLoggerLevel() {  
        return Logger.Level.FULL;  
    }
    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token,供内部请求时将tokenset进header
     * @return
     */
    @Bean
    public FeignAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignAuthRequestInterceptor();
    }
}
