package com.hzcf.service.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Create by hanlin on 2018年5月24日
 **/
@Configuration
//TODO:开启熔断、服务发现。
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.hzcf.service"})
public class ServiceInterfaceConfig {

}
