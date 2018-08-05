package com.microservices.shipments.models.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayLineItem {

    private Integer quantity;
    private String productName;
}
