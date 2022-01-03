package com.codecool.shop.controller;

import com.codecool.shop.controller.util.EngineProcessor;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Payment;
import com.codecool.shop.controller.util.PaymentFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartDao = CartDaoMem.getInstance();

        Payment paymentDetails = PaymentFactory.get(req);
        String paymentMethod = req.getParameter("payment-method");
//        cartDao.getBy(1).ifPresent(cart -> cart.setPaymentDetails(paymentDetails));
        String htmlFilename;
        if (paymentMethod.equals("paypal")) {
            htmlFilename = "paypal_payment.html";
        } else {
            htmlFilename = "credit_card_payment.html";
        }
        Map<String, Object> templateVariables = new HashMap<>();
        EngineProcessor.apply(req, resp, templateVariables, htmlFilename);
    }
}
