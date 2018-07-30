package com.microservices.orders.displayObjects;

import com.microservices.orders.tempObjects.TempShipmentObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter @NoArgsConstructor
public class OrderToDisplay {


    private Long orderNumber;
    private Double orderTotalPrice;
    private OrderAddressToDisplay shippingAddress;
    private List<OrderLineItemToDisplay> lineItemsToDisplay;
    private List<OrderShipmentsToDisplay> orderShipmentsToDisplayList;



}
