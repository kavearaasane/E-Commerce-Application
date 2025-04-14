package com.hexaware.entity;

public class OrderItems {
    private int orderItemid;
    private int orderId;
    public int productId;
    public int quantity;


    public OrderItems()
    {
        super();
    }
    public OrderItems(int orderItemid, int orderId, int productId, int quantity) {
        this.orderItemid = orderItemid;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderItemid() {
        return orderItemid;
    }

    public void setOrderItemid(int orderItemid) {
        this.orderItemid = orderItemid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemid=" + orderItemid +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
