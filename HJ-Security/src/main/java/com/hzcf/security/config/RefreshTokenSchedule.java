package com.hzcf.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.hzcf.security.util.InnerTokenUtils;

/**
 * Create by hanlin on 2018年5月24日
 **/
@EnableScheduling
@Configuration  
public class RefreshTokenSchedule implements SchedulingConfigurer {  
	// jwt的生命周期 单位分钟
	@Value("${hj.secrity.jwt.ttl}")
	private long ttl;
	//用于服务内部jwt刷新周期，单位分钟。逻辑定时任务（hj.secrity.jwt.ttl-hj.secrity.jwt.reload），刷新后对jwt重新赋值。
	@Value("${hj.secrity.jwt.reload}")
	private long reload;
	
	@Autowired
	InnerTokenUtils innerToken;
	
    @Override  
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {  
        taskRegistrar.setTaskScheduler(poolScheduler());  
        // add job  
        taskRegistrar.addFixedRateTask(new IntervalTask(  
            new Runnable() {  
                @Override  
                public void run() {  
                    innerToken.reloadApiToken();
                }  
            },  
            (ttl - reload ) * 1000 * 60, 0));//jwt过期时间-刷新时间 单位分钟
    }  
      
    @Bean  
    public TaskScheduler poolScheduler() {  
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();  
        scheduler.setThreadNamePrefix("poolScheduler");  
        scheduler.setPoolSize(10);  
        return scheduler;  
    }  
  
}  
