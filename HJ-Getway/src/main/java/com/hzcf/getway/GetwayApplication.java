package com.hzcf.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.hzcf.security.config.RefreshTokenSchedule;
import com.hzcf.security.filter.AuthenticationFilter;

/**
 * Create by hanlin on 2018年4月13日
 **/
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication()
//因为有些共用类是放在了com.hzcf.xxx.所以需要制定扫描起始位置，或者把此类放到com.hzcf下。
//关闭自动刷新token
@ComponentScan(basePackages={"com.hzcf"}
				,excludeFilters={
						//GetWay不需要本地token以及ServletFilter
						  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=AuthenticationFilter.class),
						  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=RefreshTokenSchedule.class)})
public class GetwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetwayApplication.class, args);
	}
}
