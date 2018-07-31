package com.microservices.orders.displayObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class OrderToDisplay {

    private Long orderNumber;
    private Double orderTotalPrice;
    private OrderAddressToDisplay shippingAddress;
    private List<OrderLineItemToDisplay> orderLineItemsList;
    private List<OrderShipmentsToDisplay> orderShipmentsList;
}
