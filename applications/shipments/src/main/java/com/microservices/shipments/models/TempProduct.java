package com.microservices.shipments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempProduct {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
