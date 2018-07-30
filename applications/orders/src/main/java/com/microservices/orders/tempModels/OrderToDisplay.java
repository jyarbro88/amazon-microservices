package com.microservices.orders.tempModels;

import com.microservices.orders.lineItems.LineItem;
import com.microservices.orders.tempModels.OrderAddressModel;
import com.microservices.orders.tempModels.OrderShipmentModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter @NoArgsConstructor
public class OrderToDisplay {


    private Long orderNumber;
    private Double orderTotalPrice;
    private OrderAddressModel shippingAddress;

    private List<OrderShipmentModel> shipmentList;

    private List<OrderLineItemToDisplay> lineItemsToDisplay;



}
