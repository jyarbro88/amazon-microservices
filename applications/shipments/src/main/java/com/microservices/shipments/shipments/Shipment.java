package com.microservices.shipments.shipments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Long orderId;
    private List<Long> orderItemsIds;


}
