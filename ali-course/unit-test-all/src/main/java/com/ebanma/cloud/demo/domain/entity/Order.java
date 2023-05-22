package com.ebanma.cloud.demo.domain.entity;

import com.ebanma.cloud.demo.domain.enums.OrderStatus;
import com.ebanma.cloud.demo.domain.valueObject.User;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;
    private String orderNo;
    private User member;
    private Long supplierId;
    private Long productId;
    private Long productCount;
    private BigDecimal productAmountTotal;
    private BigDecimal orderAmountTotal;
    private String address;
    private Date createTime;
    private OrderStatus status;
    private Date effectiveTime;

    public Order() {
    }

    public Order(String orderNo, User member, Long supplierId, Long productId, OrderStatus status) {
        Assert.notNull(orderNo, "Order.orderNo can not be null");
        this.orderNo = orderNo;
        this.member = member;
        this.supplierId = supplierId;
        this.productId = productId;
        this.status = status;
    }

    public void effective() {
        this.status = OrderStatus.EFFECTIVE;
        this.effectiveTime = new Date();
    }

    public void archive() {
        this.status = OrderStatus.ARCHIVE;
        this.effectiveTime = null;
    }

    public String getMemberName() {
        return this.member.getName();
    }

    public String toReadableString() {
        return "Order{" +
                "id=" + id +
                ", orderNo=" + orderNo + '\'' +
                ", memver=" + member.toString() +
                ", status=" + status +
                "}";
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}