package com.microservices.orders.tempModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor
public class OrderProductsModel {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

}
