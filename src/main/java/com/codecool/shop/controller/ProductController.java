package com.codecool.shop.controller;


import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.jdbc.CategoryDaoJdbc;
import com.codecool.shop.dao.jdbc.SupplierDaoJdbc;
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
    ProductDao productDataStore;
    CategoryDao categoryDataStore;
    SupplierDao supplierDataStore;
    ProductService productService;

    private void setData(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
//        try {
//            DataSource dataSource = new DatabaseManager().setup();
//            if (DatabaseManager.getDao().equals("memory")) {
//                productDataStore = ProductDaoMem.getInstance();
//            } else {
//                productDataStore = new ProductDaoJdbc(dataSource);
//            }
                engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
                context = new WebContext(req, resp, req.getServletContext());

                productDataStore = ProductDaoMem.getInstance();
                categoryDataStore = CategoryDaoJdbc.getInstance();
                supplierDataStore = SupplierDaoJdbc.getInstance();
                productService = new ProductService(productDataStore, categoryDataStore, supplierDataStore);

                session = req.getSession();
                cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);

            }

        context.setVariable("categories", categoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//
//        }
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

//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//        ProductService productService = new ProductService(productDataStore,productCategoryDataStore, supplierDataStore);

//        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
//        WebContext context = new WebContext(req, resp, req.getServletContext());

        String category = req.getParameter("category");
        int categoryId = (category != null) ? Integer.parseInt(category) : 1;
        String supplier = req.getParameter("supplier");
        int supplierId = (supplier != null) ? Integer.parseInt(supplier) : 0;

        String checkAll = req.getParameter("all");
        if (checkAll == null) {
            context.setVariable("products", productDataStore.getAll());
        } else {
            if (category != null) {
                context.setVariable("category", productService.getProductCategory(categoryId));
                context.setVariable("products", productService.getProductCategory(categoryId));
            } else {
                context.setVariable("supplier", productService.getProductSupplier(supplierId));
                context.setVariable("products", productService.getProductSupplier(supplierId));
            }

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
        System.out.println("check");

//        ProductDao productDataStore = ProductDaoMem.getInstance();


        String productId = req.getParameter("productId");
        int id = Integer.parseInt(productId);

        Product product = productDataStore.find(id);
        cart.addProduct(product, product.getDefaultPrice());
        session.setAttribute("cart", cart);

//        System.out.println("product" + product);
//        System.out.println("cart" + cart);

//        engine.process("product/index.html", context, resp.getWriter());

        doGet(req, resp);


    }
}
