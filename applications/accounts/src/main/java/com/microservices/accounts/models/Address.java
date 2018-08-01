package com.microservices.accounts.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public Address(String addressOne, String addressTwo, String city, String state, String zipCode, String country) {
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
}
