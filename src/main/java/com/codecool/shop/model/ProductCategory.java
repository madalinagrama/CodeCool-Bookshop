package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends BaseModel {

    private int id;
    private String description;
    private transient String department;

    public ProductCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.description = description;
    }

    public String getDepartment() {
        return department;
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
    public String getDescription() {
        return description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}