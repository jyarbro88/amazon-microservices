package com.microservices.shipments.shipments;

import com.microservices.shipments.models.ShipmentToDisplay;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShipmentsController {

    private ShipmentService shipmentService;

    public ShipmentsController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    //    @GetMapping(value = "/shipments/{accountId}", produces = "application/json")
//    public List<Shipment> getAllShipmentsForAccountId(
//            @PathVariable(value = "accountId") Long accountId
//    ){
//        return shipmentRepository.findAllByAccountId(accountId);
//    }

    @GetMapping(value = "/shipments/showAll")
    public Iterable<Shipment> getAllShipments(){
        return shipmentService.getAllShipments();
    }

    @GetMapping(value = "/shipments/{id}")
    public Optional<Shipment> findShipmentById(
            @PathVariable(value = "id") Long shipmentId
    ){
        return shipmentService.getShipmentById(shipmentId);
    }

//    @GetMapping(value = "/shipments/lineItems/{shipmentId}", produces = "application/json")
//    public Optional<Shipment> getLineItemForShipment(
//            @PathVariable(value = "shipmentId") Long shipmentId
//    ){
//        return shipmentService.get
//    }

    @GetMapping(value = "/shipments")
    public List<ShipmentToDisplay> getAllShipmentsForAccountIdOrderedByDeliveryDate(
            @RequestParam(value = "accountId") Long accountId
    ){
        return shipmentService.getShipmentByAccountId(accountId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shipment createNewShipment(
            @RequestBody Shipment shipment
    ){
        return shipmentService.createNewShipment(shipment);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shipment updateShipment(
            @RequestBody Shipment shipment
    ){
        return shipmentService.updateShipment(shipment);
    }

    @DeleteMapping(value = "/shipments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipment(@PathVariable(value = "id") Long shipmentId) {
        shipmentService.deleteShipment(shipmentId);
    }
}
