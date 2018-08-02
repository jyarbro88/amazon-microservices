package com.microservices.shipments.shipments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private Long shippingAddressId;
    private Long orderId;
    private Long lineItemId;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date shippedDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date deliveredDate;

    public Shipment(Long accountId, Long shippingAddressId, Long orderId, Long lineItemId, Date shippedDate, Date deliveredDate) {
        this.accountId = accountId;
        this.shippingAddressId = shippingAddressId;
        this.orderId = orderId;
        this.lineItemId = lineItemId;
        this.shippedDate = shippedDate;
        this.deliveredDate = deliveredDate;
    }
}
