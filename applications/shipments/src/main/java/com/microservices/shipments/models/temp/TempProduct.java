package com.microservices.shipments.models.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempProduct {

    private Long id;
    private String name;
    private String description;
    private Double price;

}
