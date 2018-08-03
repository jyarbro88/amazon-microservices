package com.microservices.orders.models.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class TempShipmentObject {

    private Long id;
    private Long accountId;
    private Long shippingAddressId;
    private Long orderId;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date shippedDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date deliveredDate;
}
