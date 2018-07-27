package com.microservices.accounts.addresses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "addresses")
public class Addresses {

    @Id
    @GeneratedValue
    private Long id;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String state;
    private String zipCode;
    private String country;


}
