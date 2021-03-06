package com.microservices.orders.models.display;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DisplayOrderLineItem {

    private Long orderLineItemId;
    private String productName;
    private Integer quantity;
}
