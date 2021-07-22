package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/cart"})
@MultipartConfig
public class CartController extends HttpServlet {

    TemplateEngine engine;
    WebContext context;
    ProductDao productDataStore;
    ProductCategoryDao productCategoryDataStore;
    SupplierDao supplierDataStore;
    ProductService productService;
    Cart cart;
    HttpSession session;


    private void setData(HttpServletRequest req, HttpServletResponse resp){

        engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context = new WebContext(req, resp, req.getServletContext());

        productDataStore = ProductDaoMem.getInstance();
        productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        supplierDataStore = SupplierDaoMem.getInstance();
        productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

        session = req.getSession();
        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setContentType("text/html");

//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        CartDao cartDaoDataStore = CartDaoMem.getInstance();
//        LineItemDao lineItemDaoDataStore = LineItemDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//        Cart cart;

        setData(req, resp);


//        if (cartDaoDataStore.find(1) !=null) {
//            cart = cartDaoDataStore.find(1);
//        }
//        else {
//            Cart newCart = new Cart(1);
//            cart = newCart;
//            cartDaoDataStore.add(newCart);
//        }
//
//        String product = req.getParameter("productId");
//        int productId = (product != null) ? Integer.parseInt(product) : 1;
////        Cart cart = cartDaoDataStore.find(productId); //
////        Cart cart = new Cart(1);
//
//
//        if (lineItemDaoDataStore.find(productDataStore.find(productId).getId()) == null) {
//
//            lineItemDaoDataStore.add(new LineItem(productDataStore.find(productId).getId(), productDataStore.find(productId)));
//
//            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setOrderId(cart.getId());
//            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setQuantity(1);
//            cart.addToCart(lineItemDaoDataStore.find(productDataStore.find(productId).getId()));
//            cart.increaseQuantity();
//        } else {
//            lineItemDaoDataStore.find(productDataStore.find(productId).getId()).setQuantity(lineItemDaoDataStore.find(productDataStore.find(productId).getId()).getQuantity() + 1);
//            cart.increaseQuantity();
//        }
//
//        cart.calculateTotal();
//
//        LineItem lineItem1  = new LineItem(lineItemDaoDataStore.getAll().size()+1, productDataStore.find(productId));
//        lineItem1.setOrderId(cart.getId());
//        lineItemDaoDataStore.add(lineItem1);
//        cart.addToCart(lineItem1);
//        cart.setQuantity(cart.getProducts().size());


//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(cart.getProducts());

//        String jsonString = new Gson().toJson(cart.getProducts());
////        assertEquals(expectedResult, jsonString);
//
//        JSONObject obj = new JSONObject();
//
//        obj.put("id", cart.getId());
//        obj.put("total", cart.getTotal());
//        obj.put("products", jsonString);
//        obj.put("quantity", cart.getQuantity());
//
//        try (PrintWriter writer = resp.getWriter()) {
//            writer.append(obj.toString());
//        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

//        context.setVariable("cart", cart);
//        context.setVariable("lineItems", lineItemDaoDataStore.getAll());
//        context.setVariable("categories", productCategoryDataStore.getAll());
//        context.setVariable("suppliers", supplierDataStore.getAll());

        engine.process("cart.html", context, resp.getWriter());

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        setData(req, resp);

        String quantityParam = req.getParameter("quantity");
        String prodIdParam = req.getParameter("productId");

        int id = Integer.parseInt(prodIdParam);
        Product product = productDataStore.find(id);
        int newQuantity = Integer.parseInt(quantityParam);
        int oldQuantity = cart.getQuantityOfProduct(product);

        if (newQuantity > oldQuantity) {
            for (int i = 1; i <= newQuantity-oldQuantity; i++ ) {
                cart.addProduct(product, product.getDefaultPrice());
            }
        }
        if (newQuantity < oldQuantity) {
            for (int i = 1; i <= oldQuantity-newQuantity; i++) {
                cart.removeProduct(product);
            }
        }

        session.setAttribute("cart", cart);

        System.out.println("session - " + session.toString());
        System.out.println("cart - " + cart.toString());

        doGet(req, resp);
    }
}
