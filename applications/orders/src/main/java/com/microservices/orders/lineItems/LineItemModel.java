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
@Table(name = "order_line_items")
public class LineItemModel {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long shipmentId;
    private Long orderId;
    private Integer quantity;
    private Double singleItemPrice;
    private Double lineItemTotalPrice;
    private Boolean shipped;
    private Boolean delivered;


}
