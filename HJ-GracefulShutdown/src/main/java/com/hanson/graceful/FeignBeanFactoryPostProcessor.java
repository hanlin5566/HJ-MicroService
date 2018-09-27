package com.hanson.graceful;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Create by hanlin on 2018年6月19日
 **/
/**
 * #1952
 * https://github.com/spring-cloud/spring-cloud-netflix/issues/1952
 * 解决shutdown时报 Error creating bean with name 'eurekaAutoServiceRegistration' 异常。
 * 问题是EurekaAutoServiceRegistration bean已经被销毁，然后它收到了与feignclient关联的应用程序上下文的contextcloseent。BeanFactory尝试再次创建该bean并触发该异常。
 */
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (containsBeanDefinition(beanFactory, "feignContext", "eurekaAutoServiceRegistration")) {
            BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
            bd.setDependsOn("eurekaAutoServiceRegistration");
        }
    }

    private boolean containsBeanDefinition(ConfigurableListableBeanFactory beanFactory, String... beans) {
        return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
    }
}
