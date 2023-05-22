package com.ebanma.cloud.demo.domain.service;

import com.ebanma.cloud.demo.domain.entity.Order;
import com.ebanma.cloud.demo.domain.service.specification.OrderSpecification;
import com.ebanma.cloud.demo.domain.service.strategy.OrderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final List<OrderStrategy> orderStrategies;
    private final OrderSpecification orderSpecification;

    @Autowired
    public OrderService(List<OrderStrategy> orderStrategies, OrderSpecification orderSpecification) {
        this.orderStrategies = orderStrategies;
        this.orderSpecification = orderSpecification;
    }

    public void archive(Order order) {
        orderSpecification.satisfiedArchive(order);
        orderStrategies.stream()
                .filter(strategy -> strategy.match(order))
                .findFirst()
                .ifPresent(strategy -> strategy.archive(order));
    }
}