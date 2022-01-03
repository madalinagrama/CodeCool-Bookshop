package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.sql.SQLException;

public interface UserDao {

    void add(User user) throws SQLException;
    User find(int id);
    void remove(int id);
}
