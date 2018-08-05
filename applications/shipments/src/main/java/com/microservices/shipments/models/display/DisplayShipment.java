package com.microservices.shipments.models.display;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayShipment {

    private Long orderNumber;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date shipmentDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date deliveredDate;
    private List<DisplayLineItem> orderDisplayLineItems;

}
