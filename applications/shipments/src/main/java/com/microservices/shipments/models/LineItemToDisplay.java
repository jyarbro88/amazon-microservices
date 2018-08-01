package com.microservices.shipments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemToDisplay {

    private Integer quantity;
    private String productName;
}
