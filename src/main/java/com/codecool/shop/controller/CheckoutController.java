package com.codecool.shop.controller;

import com.codecool.shop.controller.util.CartProvider;
import com.codecool.shop.controller.util.EngineProcessor;
import com.codecool.shop.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> templateVariable = new HashMap<>();
        Cart cart = CartProvider.get(req.getSession());
        templateVariable.put("cart", cart);

        String htmlFileName = "payment.html";
        EngineProcessor.apply(req, resp, templateVariable, htmlFileName);
    }
}
