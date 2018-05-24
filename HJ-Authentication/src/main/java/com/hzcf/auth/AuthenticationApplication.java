package com.hzcf.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
/**
 * Create by hanlin on 2018年5月24日
 **/
@EnableDiscoveryClient
@SpringBootApplication
//因为有些共用类是放在了com.hzcf.xxx.所以需要制定扫描起始位置，或者把此类放到com.hzcf下。
@ComponentScan(basePackages={"com.hzcf"})
public class AuthenticationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
