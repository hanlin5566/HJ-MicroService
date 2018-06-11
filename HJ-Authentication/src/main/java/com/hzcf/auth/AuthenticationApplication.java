package com.hzcf.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Create by hanlin on 2018年5月24日
 **/
@SpringBootApplication
@ComponentScan(basePackages={"com.hzcf"})
public class AuthenticationApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
