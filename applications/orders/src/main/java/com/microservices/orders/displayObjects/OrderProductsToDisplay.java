package com.microservices.orders.displayObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderProductsToDisplay {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

}
