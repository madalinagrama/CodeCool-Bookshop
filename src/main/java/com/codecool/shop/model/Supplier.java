package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseModel {
    private int id;
    private String description;
//    private List<Product> products;

    public Supplier(String name, String description) {
        super(name);
        this.description = description;
//        this.products = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

//    public void setProducts(ArrayList<Product> products) {
//        this.products = products;
//    }
//
//    public List<Product> getProducts() {
//        return this.products;
//    }
//
//    public void addProduct(Product product) {
//        this.products.add(product);
//    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}