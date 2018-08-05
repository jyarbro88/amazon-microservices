package com.microservices.shipments.models.temp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.shipments.models.temp.TempLineItem;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempOrder {

    private Long id;
    private Long accountId;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date orderDate;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Double totalPrice;
    private List<TempLineItem> lineItems;

}
