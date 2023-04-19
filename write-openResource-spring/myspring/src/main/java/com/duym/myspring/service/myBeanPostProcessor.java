package com.duym.myspring.service;

import com.duym.myspring.spring.BeanPostProcessor;
import com.duym.myspring.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author duym
 * @version $ Id: BeanPostProcessor, v 0.1 2023/04/17 15:34 duym Exp $
 */
@Component
public class myBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userServiceImpl")){
            System.out.println("postProcessBefore");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userServiceImpl")){
            Object proxyInstance = Proxy.newProxyInstance(myBeanPostProcessor.class.getClassLoader()
                    , bean.getClass().getInterfaces()
                    , new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("postProcessAfter");
                            return method.invoke(bean,args);
                        }
                    });
            return proxyInstance;
        }
        return bean;
    }
}
