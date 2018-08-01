package com.microservices.orders.models.display;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class OrderToDisplay {

    private Long orderNumber;
    private Double orderTotalPrice;
    private OrderAddressToDisplay shippingAddress;
//    @JsonIgnoreProperties({"id", "productId", "shipmentId", "singleItemPrice", "lineItemTotalPrice"})
    private List<OrderLineItemToDisplay> orderLineItemsList;
//    @JsonIgnoreProperties({"id", ""})
    private List<OrderShipmentsToDisplay> orderShipmentsList;
}
