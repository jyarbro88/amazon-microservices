package com.microservices.orders.lineItems;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter @NoArgsConstructor
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

    public LineItem(Long productId, Long shipmentId, Long orderId, Integer quantity, Double singleItemPrice, Double lineItemTotalPrice) {
        this.productId = productId;
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.singleItemPrice = singleItemPrice;
        this.lineItemTotalPrice = lineItemTotalPrice;
    }
}
