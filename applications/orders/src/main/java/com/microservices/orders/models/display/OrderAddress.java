package com.microservices.orders.models.display;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddress {

    private Long shippingAddressId;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
