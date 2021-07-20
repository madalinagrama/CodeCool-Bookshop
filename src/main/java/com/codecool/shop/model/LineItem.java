package com.codecool.shop.model;

import java.io.Serializable;

public class LineItem implements Serializable {
    
    private int orderId;
    private int lineNumber;
    private int quantity;
    private int itemId;
    private String currency;
    private float unitPrice;
    private Product product;
    private float total;

    public LineItem() {
    }

    public LineItem(int lineNumber, Product product) {
        this.lineNumber = lineNumber;
        this.quantity = 1;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int OrderId) {
        this.orderId = orderId;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotal() {
        return total;
    }

    public Product getItem() {
        return product;
    }

    public Product getProduct() {
        return product;
    }

    public void setItem(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
