package com.ebanma.cloud.demo.data.dao;

import com.ebanma.cloud.demo.data.model.OrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    OrderDO selectOrderById(long id);

    void insert(OrderDO orderDO);
}