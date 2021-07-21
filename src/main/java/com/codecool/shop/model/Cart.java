package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart {
    private static final AtomicInteger count = new AtomicInteger(1);
    int id;
    private transient List<LineItem> products = new ArrayList<>();
    float total;
    int quantity;

    public Cart(int id) {
        this.id = id;
    }

    public Cart(List<LineItem> products, int id) {
//        this.id = count.incrementAndGet();
        this.id = id;
        this.products = products;
    }

    public int getId() {
        return this.id;
    }

    public List<LineItem> getProducts() {
        return products;
    }

    public float getTotal() {
        return total;
    }

    public void calculateTotal() {
        total = 0;
        products.forEach( product -> {
            total += product.getQuantity() * product.getUnitPrice();
        });
    }

    public void addToCart(LineItem lineItem){
        products.add(lineItem);
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        quantity +=1;
    }

}
