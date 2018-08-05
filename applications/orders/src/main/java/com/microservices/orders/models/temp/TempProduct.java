package com.microservices.orders.models.temp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempProduct {

    private Long id;
    private String name;
    private String description;
    private Double price;

}
