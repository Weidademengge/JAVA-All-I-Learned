package com.duym.usertestall.config;

import com.duym.usertestall.interceptor.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author duym
 * @version $ Id: WebConfig, v 0.1 2023/04/13 17:06 duym Exp $
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private RateLimitInterceptor rateLimitInterceptor;
    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/api/**");
//    }

    /**
     * 添加资源处理程序
     *
     * @param registry 注册表
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("ali-course/user-test-all/uploads/**")
//                .addResourceLocations("D:\\java-project\\mine\\JAVA-All-I-Learned\\ali-course\\user-test-all\\uploads\\");
//    }
}
