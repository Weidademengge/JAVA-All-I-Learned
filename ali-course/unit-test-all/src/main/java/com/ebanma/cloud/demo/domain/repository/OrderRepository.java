package com.ebanma.cloud.demo.domain.repository;

import com.ebanma.cloud.demo.data.dao.OrderDao;
import com.ebanma.cloud.demo.data.model.OrderDO;
import com.ebanma.cloud.demo.domain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderRepository {

    private final OrderDao orderDao;

    @Autowired
    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void save(Order order) {
        OrderDO orderDO = this.serialize(order);
        orderDao.insert(orderDO);
    }

    private OrderDO serialize(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderNo(order.getOrderNo());
        orderDO.setMemberId(order.getMember().getId());
        orderDO.setMemberName(order.getMember().getName());
        orderDO.setSupplierId(order.getSupplierId());
        orderDO.setProductId(order.getProductId());
        orderDO.setProductCount(order.getProductCount());
        orderDO.setProductAmountTotal(order.getProductAmountTotal());
        orderDO.setOrderAmountTotal(order.getOrderAmountTotal());
        orderDO.setAddress(order.getAddress());
        orderDO.setCreateTime(new Date());
        return orderDO;
    }
}