package com.ebanma.cloud.demo.domain.service.strategy;

import com.ebanma.cloud.demo.domain.entity.Order;
import com.ebanma.cloud.demo.domain.enums.OrderStatus;
import com.ebanma.cloud.demo.exception.BadRequestException;

public class IllegalOrderStrategy implements OrderStrategy{
    @Override
    public boolean match(Order order) {
        return order.getStatus() == OrderStatus.ILLEGAL;
    }

    @Override
    public void archive(Order order) {
        throw new BadRequestException("Archive order failed, order is illegal");
    }
}