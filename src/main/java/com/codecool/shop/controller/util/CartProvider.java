package com.codecool.shop.controller.util;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;

import javax.servlet.http.HttpSession;

public class CartProvider {

    private static final CartDao cartStore = CartDaoMem.getInstance();

    public static Cart get(HttpSession session) {
        Integer userID = (Integer) session.getAttribute("user_id");
        Cart card;
        if(userID != null) {
            card = cartStore.getBy(userID).get();
        } else {
            card = (Cart) session.getAttribute("cart");
        }
        return card;
    }
}
