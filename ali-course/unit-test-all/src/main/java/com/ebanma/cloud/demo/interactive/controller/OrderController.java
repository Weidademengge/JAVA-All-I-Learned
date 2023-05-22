package com.ebanma.cloud.demo.interactive.controller;

import com.ebanma.cloud.demo.domain.entity.Order;
import com.ebanma.cloud.demo.interactive.request.OrderCreateRequest;
import com.ebanma.cloud.demo.interactive.response.ResponseWrapper;
import com.ebanma.cloud.demo.interactive.validator.OrderRequestValidator;
import com.ebanma.cloud.demo.service.OrderApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderRequestValidator orderRequestValidator;

    @Autowired
    public OrderController(OrderApplicationService orderApplicationService, OrderRequestValidator orderRequestValidator) {
        this.orderApplicationService = orderApplicationService;
        this.orderRequestValidator = orderRequestValidator;
    }

    @PostMapping("/orders")
    public ResponseWrapper create(@RequestBody @Valid @NotNull OrderCreateRequest orderCreateRequest) {
        orderRequestValidator.validateCreate(orderCreateRequest);
        Order order = orderCreateRequest.convert();
        orderApplicationService.create(order);
        return ResponseWrapper.success();
    }
}