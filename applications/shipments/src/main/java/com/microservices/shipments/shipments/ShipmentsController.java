package com.microservices.shipments.shipments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/shipments"})
public class ShipmentsController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping(value = "/{accountId}")
    public List<Shipment> getAllShipmentsForAccountId(
            @PathVariable(value = "accountId") Long accountId
    ){
        return shipmentRepository.findAllByAccountId(accountId);
    }


}
