package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseModel {
    private int id;
    private String description;

    public Supplier(String name, String description) {
        super(name);
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

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