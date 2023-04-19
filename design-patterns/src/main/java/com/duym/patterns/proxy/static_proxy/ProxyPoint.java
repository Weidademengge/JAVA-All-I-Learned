package com.duym.patterns.proxy.static_proxy;

/**
 * @author duym
 * @version $ Id: ProxyPoint, v 0.1 2023/04/17 19:37 duym Exp $
 */
public class ProxyPoint implements SellTickets{

    // 声明火车站类对象
    private TrainStation trainStation = new TrainStation();

    @Override
    public void sell() {
        System.out.println("代理点收取一些服务费用");
        trainStation.sell();
    }
}
