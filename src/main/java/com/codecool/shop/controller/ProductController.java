package com.codecool.shop.controller;


import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.jdbc.CategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.ProductDaoJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {


    TemplateEngine engine;
    WebContext context;
    Cart cart;
    HttpSession session;
    ProductDao productDataStore = null;
    CategoryDao categoryDataStore;
    SupplierDao supplierDataStore;
    ProductService productService;

    private void setData(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context = new WebContext(req, resp, req.getServletContext());

        productDataStore = ProductDaoJdbc.getInstance();
        categoryDataStore = CategoryDaoJdbc.getInstance();
        supplierDataStore = SupplierDaoMem.getInstance();
        productService = new ProductService(productDataStore, categoryDataStore, supplierDataStore);

        session = req.getSession();
        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);

        }
        context.setVariable("categories", categoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        try {
            setData(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String category = req.getParameter("category");
        String supplier = req.getParameter("supplier");

        if (category == null && supplier == null) {
            context.setVariable("products", productDataStore.getAll());


        } else if (category != null) {
                context.setVariable("category", categoryDataStore.find(Integer.parseInt(category)));
                context.setVariable("products", productService.getProductsForCategory(Integer.parseInt(category)));
        } else {
            context.setVariable("supplier", supplierDataStore.find(Integer.parseInt(supplier)));
            context.setVariable("products", productService.getProductsForSupplier(Integer.parseInt(supplier)));
        }
        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        try {
            setData(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String productId = req.getParameter("productId");
        int id = Integer.parseInt(productId);

        Product product = productDataStore.find(id);
        cart.addProduct(product, product.getDefaultPrice());
        session.setAttribute("cart", cart);

        doGet(req, resp);
    }
}
