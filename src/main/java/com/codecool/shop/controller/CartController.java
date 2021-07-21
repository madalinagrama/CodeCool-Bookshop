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
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartDao cartDaoDataStore = CartDaoMem.getInstance();
        LineItemDao lineItemDaoDataStore = LineItemDaoMem.getInstance();
        Cart cart;


        if (cartDaoDataStore.find(1) !=null) {
            cart = cartDaoDataStore.find(1);
        }
        else {
            Cart newCart = new Cart(1);
            cart = newCart;
            cartDaoDataStore.add(newCart);

        }

        String product = req.getParameter("productId");
        int productId = (product != null) ? Integer.parseInt(product) : 1;
//        Cart cart = cartDaoDataStore.find(productId); // TODO get the actual cart ID
//        Cart cart = new Cart(1);


        System.out.println("cart.hashcode() " + cart.hashCode());
        System.out.println("CartDaoMem.getInstance() " + CartDaoMem.getInstance());
        System.out.println("lineItemDaoDataStore " + lineItemDaoDataStore);
        System.out.println("cartDaoDataStore.find(1) " + cartDaoDataStore.find(1));

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

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(cart.getProducts());


//        JSONObject obj = new JSONObject();
//        obj.put("id", cart.getId());
//        obj.put("total", cart.getTotal());
//        obj.put("products", json);
//        obj.put("quantity", cart.getQuantity());

        LineItem lineItem1  = new LineItem(lineItemDaoDataStore.getAll().size()+1, productDataStore.find(productId));
        lineItem1.setOrderId(cart.getId());
        lineItemDaoDataStore.add(lineItem1);
        cart.addToCart(lineItem1);
        cart.setQuantity(cart.getProducts().size());


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cart);
        context.setVariable("lineItems", lineItemDaoDataStore.getAll());

        engine.process("cart.html", context, resp.getWriter());

//        try (PrintWriter writer = resp.getWriter()) {
//            writer.append(obj.toString());
//        }

    }

}
