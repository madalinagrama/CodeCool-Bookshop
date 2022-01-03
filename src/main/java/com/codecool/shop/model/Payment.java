package com.codecool.shop.model;

import java.util.List;

public class Payment {

    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String city;
    private String phoneNumber;

    public Payment(List<String> paymentParams) {
        this.firstName = paymentParams.get(0);
        this.lastName = paymentParams.get(1);
        this.email = paymentParams.get(2);
        this.streetAddress = paymentParams.get(3);
        this.city = paymentParams.get(4);
        this.phoneNumber = paymentParams.get(5);

    }
}
