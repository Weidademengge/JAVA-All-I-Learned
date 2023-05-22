package com.ebanma.cloud.demo.interactive.request;

import com.ebanma.cloud.demo.domain.entity.Order;
import com.ebanma.cloud.demo.domain.valueObject.User;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderCreateRequest {
    private String orderNo;
    @NotNull
    private Long memberId;
    private Long supplierId;
    private Long productId;
    @Range(min = 0L, max = 1000L)
    private Long productCount;
    private BigDecimal productAmountTotal;
    private BigDecimal orderAmountTotal;
    @NotBlank
    private String address;

    private User convertMember(Long memberId) {
        User user = new User();
        user.setId(memberId);
        return user;
    }

    public Order convert() {
        Order order = new Order();
        order.setOrderNo(this.orderNo);
        order.setMember(this.convertMember(this.memberId));
        order.setSupplierId(this.supplierId);
        order.setProductId(this.productId);
        order.setProductCount(this.productCount);
        order.setProductAmountTotal(this.productAmountTotal);
        order.setOrderAmountTotal(this.orderAmountTotal);
        order.setAddress(this.address);
        return order;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getProductAmountTotal() {
        return productAmountTotal;
    }

    public void setProductAmountTotal(BigDecimal productAmountTotal) {
        this.productAmountTotal = productAmountTotal;
    }

    public BigDecimal getOrderAmountTotal() {
        return orderAmountTotal;
    }

    public void setOrderAmountTotal(BigDecimal orderAmountTotal) {
        this.orderAmountTotal = orderAmountTotal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}