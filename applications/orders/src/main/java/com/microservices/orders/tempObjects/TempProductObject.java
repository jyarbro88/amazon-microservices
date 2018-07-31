package com.microservices.orders.tempObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TempProductObject {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

}