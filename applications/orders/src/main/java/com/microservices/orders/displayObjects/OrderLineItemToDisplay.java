package com.microservices.orders.displayObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderLineItemToDisplay {

    private Long orderLineItemId;
    private String productName;
    private Integer quantity;
}
