package com.microservices.shipments.shipments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "shipments")
class Shipment {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private Long shippingAddressId;
    private Long orderId;
    private Long lineItemId;
    private Date shippedDate;
    private Date deliveredDate;

}
