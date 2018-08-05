package com.microservices.orders.models.display;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayOrderDetails {

    private Long orderNumber;
    private Double orderTotalPrice;
    private DisplayOrderAddress shippingAddress;
    private List<DisplayOrderLineItem> displayOrderLineItemsList;
    private List<DisplayOrderShipment> displayOrderShipmentsList;
}
