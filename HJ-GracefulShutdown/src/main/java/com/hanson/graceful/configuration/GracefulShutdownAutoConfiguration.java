package com.hanson.graceful.configuration;

import javax.servlet.Servlet;

import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.xnio.SslClientAuthMode;

import com.hanson.graceful.FeignBeanFactoryPostProcessor;
import com.hanson.graceful.endpoint.GracefulShutdownEndpoint;
import com.hanson.graceful.properties.GracefulShutdownProperties;
import com.hanson.graceful.shutdown.TomcatGracefulShutdown;
import com.hanson.graceful.shutdown.UndertowGracefulShutdown;
import com.hanson.graceful.shutdown.UndertowGracefulShutdownWrapper;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * 这个配置类将被Spring Boot的自动配置装载
 */
@Configuration
//匹配endpoints.shutdown.graceful.enabled 为true时开始优雅停机
@ConditionalOnProperty(prefix = "endpoints.shutdown.graceful", name = "enabled", havingValue = "true",  matchIfMissing = true)
@EnableConfigurationProperties(GracefulShutdownProperties.class)
@Import(EmbeddedServletContainerAutoConfiguration.BeanPostProcessorsRegistrar.class)
public class GracefulShutdownAutoConfiguration {

    @Autowired
    private GracefulShutdownProperties gracefulShutdownProperties;

    /**
     * 如果使用tomcat则注册tomcat优雅停机的bean
     */
    @Configuration
    @ConditionalOnClass({Servlet.class, Tomcat.class})
    @ConditionalOnBean(TomcatEmbeddedServletContainerFactory.class)
    public static class EmbeddedTomcat {
        @Bean
        public TomcatGracefulShutdown tomcatShutdown() {
            return new TomcatGracefulShutdown();
        }

        /**
         * 为tomcat容器添加优雅停机
         */
        @Bean
        public EmbeddedServletContainerCustomizer tomcatCustomizer() {
            return new EmbeddedServletContainerCustomizer() {
                public void customize(ConfigurableEmbeddedServletContainer container) {
                    if (container instanceof TomcatEmbeddedServletContainerFactory) {
                        ((TomcatEmbeddedServletContainerFactory) container).addConnectorCustomizers(tomcatShutdown());
                    }
                }
            };
        }
    }

    /**
     * 如果使用undertow则注册undertow优雅停机的bean
     */
    @Configuration
    @ConditionalOnClass({Servlet.class, Undertow.class, SslClientAuthMode.class})
    @ConditionalOnBean(UndertowEmbeddedServletContainerFactory.class)
    public static class EmbeddedUndertow {

        @Bean
        public UndertowGracefulShutdown undertowShutdown() {
            return new UndertowGracefulShutdown();
        }


        /**
         * 为undertow添加优雅停机
         */
        @Bean
        public EmbeddedServletContainerCustomizer undertowCustomizer() {
            return new EmbeddedServletContainerCustomizer() {
                public void customize(ConfigurableEmbeddedServletContainer container) {
                    if (container instanceof UndertowEmbeddedServletContainerFactory) {
                        ((UndertowEmbeddedServletContainerFactory) container).addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer());
                    }
                }
            };
        }

        @Bean
        public UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer() {
            return new UndertowDeploymentInfoCustomizer() {
                public void customize(DeploymentInfo deploymentInfo) {
                    deploymentInfo.addOuterHandlerChainWrapper(undertowGracefulShutdownWrapper());
                }
            };
        }

        @Bean
        public UndertowGracefulShutdownWrapper undertowGracefulShutdownWrapper() {
            return new UndertowGracefulShutdownWrapper();
        }
    }
    /**
     * 如果使用了feign则注册 @FeignBeanFactoryPostProcessor
     */
    @Configuration
    @ConditionalOnClass({EurekaAutoServiceRegistration.class,})
    public static class EmbeddedFeign {
    	@Bean
    	public FeignBeanFactoryPostProcessor feignBeanFactoryPostProcessor(){
    		return new FeignBeanFactoryPostProcessor();
    	}
    }

    @Bean
    @ConditionalOnMissingBean
    protected GracefulShutdownEndpoint gracefulShutdownEndpoint() {
        return new GracefulShutdownEndpoint(gracefulShutdownProperties.getTimeout(), gracefulShutdownProperties.getWait());
    }
}
