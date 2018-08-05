package com.microservices.orders.models.display;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    private Long orderNumber;
    private Double orderTotalPrice;
    private OrderAddress shippingAddress;
    private List<OrderLineItem> orderLineItemsList;
    private List<OrderShipments> orderShipmentsList;
}
