package com.ebanma.cloud.demo.domain.service.strategy;

import com.ebanma.cloud.demo.domain.entity.Order;

public interface OrderStrategy {

    boolean match(Order order);

    void archive(Order order);
}