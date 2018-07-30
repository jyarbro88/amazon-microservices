package com.microservices.shipments.shipments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ShipmentsController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping(value = "/shipments/{accountId}", produces = "application/json")
    public List<Shipment> getAllShipmentsForAccountId(
            @PathVariable(value = "accountId") Long accountId
    ){
        return shipmentRepository.findAllByAccountId(accountId);
    }

    @GetMapping(value = "/shipments/lineItems/{shipmentId}", produces = "application/json")
    public Optional<Shipment> getLineItemForShipment(
            @PathVariable(value = "shipmentId") Long shipmentId
    ){
        return shipmentRepository.findById(shipmentId);
//        return shipmentRepository.findByLineItemId(shipmentId);
    }
}
