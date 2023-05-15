package com.duym.dubbo2.rpc.annotation.processor;

import com.duym.dubbo2.rpc.annotation.RpcReference;
import com.duym.dubbo2.rpc.client.NettyClientGroup;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 该 BeanPostProcessor 用于注入远程服务接口代理类
 */
public class RcpServiceProcessor
        implements InstantiationAwareBeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // 判断类里是否有 @RpcService 注解
        Class<?> clazz = context.getType(beanName);
        if (Objects.isNull(clazz)) {
            return bean;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            // 找出标记了InjectService注解的属性
            RpcReference injectService = field.getAnnotation(RpcReference.class);
            if (injectService == null) {
                continue;
            }

            // 获取服务名称
            String providerName = injectService.value();
            // 获取接口Class
            Class<?> fieldClass = field.getType();
            // 获取nettyClient
            NettyClientGroup nettyClientGroup = context.getBean(NettyClientGroup.class);

            RpcServiceProxy rpcProxy = new RpcServiceProxy(fieldClass, providerName, nettyClientGroup);
            Object proxy = rpcProxy.getProxy();

            Object object = bean;
            // 当我们需要获取私有属性的属性值得时候，我们必须设置Accessible为true，然后才能获取
            field.setAccessible(true);
            try {
                // 通过field.set()重新给带有远程服务接口设置新的属性值，即代理对象
                field.set(object, proxy);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public RcpServiceProcessor() {
        System.out.println("-----RcpServiceInjectBeanPostProcessor-----------");
    }
}