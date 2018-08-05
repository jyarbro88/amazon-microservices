package com.microservices.orders.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "line_items")
public class LineItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long shipmentId;
    private Long orderId;

    private Integer quantity;
    private Double singleItemPrice;
    private Double lineItemTotalPrice;

}