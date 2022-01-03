package com.codecool.shop.model;

public class User extends BaseModel{

    private final String password;


    public User(String name, String password) {
        super(name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
