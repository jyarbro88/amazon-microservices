package com.microservices.orders.LineItems;

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
    private Integer quantity;
    private Double lineItemTotalPrice;


}
