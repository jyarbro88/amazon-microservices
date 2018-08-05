package com.microservices.shipments.shipments;

import com.microservices.shipments.models.Shipment;
import com.microservices.shipments.models.display.DisplayShipment;
import com.microservices.shipments.services.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/shipments")
public class ShipmentsController {

    private ShipmentService shipmentService;

    public ShipmentsController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping(value = "/showAll")
    public Iterable<Shipment> getAllShipments(){
        return shipmentService.getAllShipments();
    }

    @GetMapping(value = "/{id}")
    public Optional<Shipment> findShipmentById(
            @PathVariable(value = "id") Long shipmentId
    ){
        return shipmentService.getShipmentById(shipmentId);
    }

    @GetMapping
    public List<DisplayShipment> getAllShipmentsForAccountIdOrderedByDeliveryDate(
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

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity updateShipment(
            @RequestBody Shipment shipment
    ){
        shipmentService.updateShipment(shipment);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/updateShipping")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity updateShipping(
            @RequestParam(value = "orderId") Long orderId,
            @RequestBody Shipment shipment
    ){
        shipmentService.updateShippingDate(orderId, shipment);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipment(@PathVariable(value = "id") Long shipmentId) {
        shipmentService.deleteShipment(shipmentId);
    }
}
