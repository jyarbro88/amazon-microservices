package com.microservices.orders.models.temp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TempProductObject {

    private Long id;
    private String name;
    private String description;
    private Double price;

}
