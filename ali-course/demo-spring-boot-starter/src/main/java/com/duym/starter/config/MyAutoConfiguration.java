package com.duym.starter.config;

import com.duym.starter.pojo.SimpleBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duym
 * @date 2023/04/13
 */
@Configuration
@ConditionalOnBean(ConfigMarker.class)
public class MyAutoConfiguration {

    static {
        System.out.println("MyAutoConfiguration init....");
	}

	@Bean
    public SimpleBean simpleBean(){
        return new SimpleBean();
	} 
}