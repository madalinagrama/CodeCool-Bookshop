package com.codecool.shop.service;

import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;

import javax.sql.DataSource;

public class UserServiceStore {

    private static UserService userService;

    public static void initialize() {
        if(userService == null) {
            userService = new UserService(new UserDaoMem());
        }
    }

    public static void initialize (DataSource dataSource) {
        if(userService == null) {
            userService = new UserService(new UserDaoJdbc(dataSource));
        }
    }

    public static UserService getUserService() {
        return userService;
    }
}
