package com.ebanma.cloud.demo.domain.service.strategy;

import com.ebanma.cloud.demo.domain.entity.Order;
import com.ebanma.cloud.demo.domain.enums.OrderStatus;

public class EffectiveOrderStrategy implements OrderStrategy {
    @Override
    public boolean match(Order order) {
        return order.getStatus() == OrderStatus.EFFECTIVE;
    }

    @Override
    public void archive(Order order) {
        order.archive();
    }
}