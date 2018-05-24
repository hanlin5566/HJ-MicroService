package com.hzcf.service.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Create by hanlin on 2018年5月24日
 **/
@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.hzcf.service"})
public class ServiceInterfaceConfig {

}
