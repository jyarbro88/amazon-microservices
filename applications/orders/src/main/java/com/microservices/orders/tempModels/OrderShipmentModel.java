package com.microservices.orders.tempModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class OrderShipmentModel {

    private Long id;
    private Long accountId;
    private Long shippingAddressId;
    private Long orderId;
    private Long lineItemId;
    private Date shippedDate;
    private Date deliveredDate;
}
