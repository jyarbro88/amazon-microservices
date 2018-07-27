package com.microservices.products.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Double price;

}
