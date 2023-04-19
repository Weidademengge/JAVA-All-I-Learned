package com.duym.patterns.proxy.static_proxy;

/**
 * @author duym
 * @version $ Id: TrainStation, v 0.1 2023/04/17 19:37 duym Exp $
 */
public class TrainStation implements SellTickets {


    @Override
    public void sell() {
        System.out.println("火车站卖票");
    }
}
