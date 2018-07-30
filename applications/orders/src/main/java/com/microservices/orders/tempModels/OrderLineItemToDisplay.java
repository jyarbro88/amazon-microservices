package com.microservices.orders.tempModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderLineItemToDisplay {

    private String productName;
    private Integer quantity;
}
