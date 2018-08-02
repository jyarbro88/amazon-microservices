package com.microservices.orders.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

//    public LineItem(Long productId, Long shipmentId, Integer quantity, Double singleItemPrice, Double lineItemTotalPrice, Order order) {
//        this.productId = productId;
//        this.shipmentId = shipmentId;
//        this.quantity = quantity;
//        this.singleItemPrice = singleItemPrice;
//        this.lineItemTotalPrice = lineItemTotalPrice;
//        this.order = order;
//    }


    public Double getLineItemTotalPrice(){
        return lineItemTotalPrice;
    }

    public void setLineItemTotalPrice(Double lineItemTotalPrice){
        lineItemTotalPrice = singleItemPrice * quantity;
        this.lineItemTotalPrice = lineItemTotalPrice;
    }
}