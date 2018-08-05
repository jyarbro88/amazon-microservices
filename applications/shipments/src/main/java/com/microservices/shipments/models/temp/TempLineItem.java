package com.microservices.shipments.models.temp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempLineItem {

    private Long id;
    private Long productId;
    private Long shipmentId;

    private Integer quantity;
    private Double singleItemPrice;
    private Double lineItemTotalPrice;

//    private TempOrder order;

}