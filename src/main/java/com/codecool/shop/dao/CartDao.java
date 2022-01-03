package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    void add(Cart cart, LineItem lineItem);
    Cart find(int id);
    void remove(int id);

    List<Cart> getAll();

    Optional<Cart> getBy(Integer userID);
}

