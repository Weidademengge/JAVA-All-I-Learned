package com.ebanma.cloud.demo.domain.service.specification;

import com.ebanma.cloud.demo.domain.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderSpecification {
    public void satisfiedArchive(Order order) {
        Assert.notNull(order.getMember(), "Member cannot be null");
        Assert.notNull(order.getSupplierId(), "SupplierId cannot be null");
        Assert.notNull(order.getProductId(), "ProductId cannot be null");
    }
}