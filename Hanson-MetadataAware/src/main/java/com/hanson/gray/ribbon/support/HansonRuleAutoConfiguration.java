package com.hanson.gray.ribbon.support;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.hanson.gray.ribbon.rule.HansonRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;

/**
 * @author Hanson
 * 动态路由的自动配置类，ribbon.filter.metadata.enabled=true开启
 */
@Configuration
@ConditionalOnClass(DiscoveryEnabledNIWSServerList.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = "ribbon.filter.metadata.enabled", matchIfMissing = true)
public class HansonRuleAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HansonRule hansonRule() {
        return new HansonRule();
    }
}
