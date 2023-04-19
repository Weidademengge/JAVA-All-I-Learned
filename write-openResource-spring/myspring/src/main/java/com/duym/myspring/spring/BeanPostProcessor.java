package com.duym.myspring.spring;

/**
 * 豆后置处理程序
 *
 * @author duym
 * @date 2023/04/17
 */
public interface BeanPostProcessor {

    public Object postProcessBeforeInitialization(String beanName,Object bean);
    public Object postProcessAfterInitialization(String beanName,Object bean);


}
