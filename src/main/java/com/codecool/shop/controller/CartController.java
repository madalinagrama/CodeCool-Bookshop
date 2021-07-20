package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartDao cartDaoDataStore = CartDaoMem.getInstance();
        LineItemDao lineItemDaoDataStore = LineItemDaoMem.getInstance();


//        Cart cart = cartDaoDataStore.find(1); // TODO get the actual cart ID

        String product = req.getParameter("productId");
        int productId = (product != null) ? Integer.parseInt(product) : 1;
        Cart cart = cartDaoDataStore.find(1); // TODO get the actual cart ID

        if (lineItemDaoDataStore.find(productDataStore.find(productId).getId()) == null) {

            lineItemDaoDataStore.add(new LineItem(productDataStore.find(productId).getId(), productDataStore.find(productId)));

            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setOrderId(cart.getId());
            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setQuantity(1);
            cart.addToCart(lineItemDaoDataStore.find(productDataStore.find(productId).getId()));
            cart.increaseQuantity();
        } else {
            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setQuantity(lineItemDaoDataStore.find(productDataStore.find(productId).getId()).getQuantity() + 1);
            cart.increaseQuantity();
        }

        cart.calculateTotal();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(cart.getProducts());


        JSONObject obj = new JSONObject();
        obj.put("id", cart.getId());
        obj.put("total", cart.getTotal());
        obj.put("products", json);
        obj.put("quantity", cart.getQuantity());


        try (PrintWriter writer = resp.getWriter()) {
            writer.append(obj.toString());
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/cart.html", context, resp.getWriter());
    }

}
