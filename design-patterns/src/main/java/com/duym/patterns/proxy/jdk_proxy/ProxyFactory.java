package com.duym.patterns.proxy.jdk_proxy;


import com.duym.patterns.proxy.static_proxy.SellTickets;
import com.duym.patterns.proxy.static_proxy.TrainStation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author duym
 * @version $ Id: ProxyFactory, v 0.1 2023/04/17 19:42 duym Exp $
 */
public class ProxyFactory {
    
    // 声明目标对象
    private TrainStation station = new TrainStation();
    
    // 获取代理对象的方法
    public SellTickets getProxyObject(){
        //返回代理对象
        /**
         * ClassLoader Loader:类加载器，用户加载代理类
         * Class<?>[] interfaces:代理类实现的接口的字节码对象
         * InvocationHandler h : 代理对象的调用处理程序
         */
        SellTickets proxyObject = (SellTickets)Proxy.newProxyInstance(
                station.getClass().getClassLoader(),
                station.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     *
                     * Object proxy:代理对象。和proxyObject对象是同一个对象，在invoke方法中基本不用
                     * Method method: 对接口中的方法进行封装的method对象
                     * Object[] args:调用方法的实际参数
                     *
                     * 返回值：方法的返回值
                     *
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        System.out.println("invoke方法执行了");
                        System.out.println("代售点收取一定的服务费用(JDK动态代理)");
                        // 执行目标对象的方法
                        Object obj = method.invoke(station, args);
                        return obj;
                    }
                }
        );

        return proxyObject;
    }
}
