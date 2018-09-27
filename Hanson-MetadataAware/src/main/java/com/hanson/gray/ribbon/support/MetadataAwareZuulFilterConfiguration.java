package com.hanson.gray.ribbon.support;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hanson.gray.filter.MetadataAwareZuulFilter;
import com.hanson.gray.ribbon.rule.HansonRule;

/**
 * @author Hanson
 * 动态路由自动写入ZuulFiter，ribbon.filter.autowirted.zuul.enabled=true开启
 */
@Configuration
@ConditionalOnClass(HansonRule.class)
@ConditionalOnProperty(value = "ribbon.filter.autowirted.zuul.enabled", matchIfMissing = true)
public class MetadataAwareZuulFilterConfiguration{
	@ConditionalOnMissingBean
    @Bean
    public MetadataAwareZuulFilter metadataAwareZuulFilter() {
        return new MetadataAwareZuulFilter();
    }    
}
