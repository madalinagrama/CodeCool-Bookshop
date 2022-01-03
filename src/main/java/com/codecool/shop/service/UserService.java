package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;

public class UserService {

    private UserDao userDao;

    public UserService (UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
