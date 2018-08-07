package com.microservices.shipments.services;

import com.microservices.shipments.models.Shipment;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateDateService {

    private ShipmentRepository shipmentRepository;

    public UpdateDateService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ResponseEntity updateShippingDate(Long orderId, Shipment passedInShipment) {

        List<Shipment> allByOrderId = shipmentRepository.findAllByOrderId(orderId);
        for (Shipment shipment : allByOrderId) {
            if (passedInShipment.getShippedDate() != null) {
                shipment.setShippedDate(passedInShipment.getShippedDate());
            }
            if (passedInShipment.getDeliveredDate() != null) {
                shipment.setDeliveredDate(passedInShipment.getDeliveredDate());
            }
            shipmentRepository.save(shipment);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
