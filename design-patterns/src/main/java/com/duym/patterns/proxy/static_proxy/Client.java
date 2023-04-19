package com.duym.patterns.proxy.static_proxy;

/**
 * @author duym
 * @version $ Id: Client, v 0.1 2023/04/17 19:39 duym Exp $
 */
public class Client {
    public static void main(String[] args) {
        // 创建对象
        ProxyPoint proxyPoint = new ProxyPoint();
        // 调用方法进行卖票
        proxyPoint.sell();

    }
}
