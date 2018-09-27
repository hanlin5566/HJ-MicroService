package com.hanson.gray.ribbon.support;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hanson.gray.filter.MetadataAwareInteceptor;
import com.hanson.gray.ribbon.rule.HansonRule;

/**
 * @author Hanson
 * 动态路由自动写入inteceptor，ribbon.filter.metadata.enabled=true开启
 */
@Configuration
@ConditionalOnClass(HansonRule.class)
@ConditionalOnProperty(value = "ribbon.filter.autowirted.inteceptor.enabled", matchIfMissing = true)
public class MetadataAwareInteceptorConfiguration extends WebMvcConfigurerAdapter{
	@ConditionalOnMissingBean
    @Bean
    public MetadataAwareInteceptor metadataAwareInteceptor() {
        return new MetadataAwareInteceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(metadataAwareInteceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
