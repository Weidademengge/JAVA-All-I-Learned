package com.duym.usertestall.config;
  
import java.util.concurrent.Executor;  
  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.scheduling.annotation.EnableAsync;  
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;  
  
@Configuration  
@EnableAsync  
public class ThreadPoolConfig {  
  
    @Bean("exportServiceExecutor")  
    public Executor exportServiceExecutor() {  
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  
        // 当前机器的核心数  
        int processors = Runtime.getRuntime().availableProcessors();  
  
        executor.setCorePoolSize(processors);  
        executor.setMaxPoolSize(processors * 2);  
        executor.setQueueCapacity(Integer.MAX_VALUE);  
        executor.setThreadNamePrefix("export-service-");  
  
        executor.initialize();  
        return executor;  
    }  
}