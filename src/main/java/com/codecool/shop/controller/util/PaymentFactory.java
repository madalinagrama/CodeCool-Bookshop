package com.codecool.shop.controller.util;

import com.codecool.shop.model.Payment;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PaymentFactory {

    public static Payment get(HttpServletRequest req) {
        List<String> paymentParams = new ArrayList<>();
        paymentParams.add(req.getParameter("first_name"));
        paymentParams.add(req.getParameter("last_name"));
        paymentParams.add(req.getParameter("email"));
        paymentParams.add(req.getParameter("street_address"));
        paymentParams.add(req.getParameter("city"));
        paymentParams.add(req.getParameter("phone_number"));

        return new Payment(paymentParams);
    }
}
