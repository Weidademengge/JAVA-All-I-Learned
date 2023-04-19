package com.duym.patterns.proxy.cglib_proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author duym
 * @version $ Id: ProxyFactory, v 0.1 2023/04/17 20:10 duym Exp $
 */
public class ProxyFactory implements MethodInterceptor {

    // 声明火车站对象
    private TrainStation station = new TrainStation();

    public TrainStation getProxyObject(){
        // 创建Enhancer对象，类似于JDK代理中的Proxy类
        Enhancer enhancer = new Enhancer();
        // 设置父类的字节码对象
        enhancer.setSuperclass(TrainStation.class);
        // 设置回调函数
        enhancer.setCallback(this);

        //创建代理对象
        TrainStation proxyObject = (TrainStation) enhancer.create();
        return proxyObject;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("方法被调用");
        System.out.println("代售点收取一定的服务费用（cglib）");
        // 要调用目标对象的方法
        Object obj = method.invoke(station, objects);
        return obj;


    }
}
