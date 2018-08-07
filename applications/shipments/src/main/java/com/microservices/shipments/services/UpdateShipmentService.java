package com.microservices.shipments.services;

import com.microservices.shipments.models.Shipment;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateShipmentService {

    private ShipmentRepository shipmentRepository;

    public UpdateShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment updateShipment(Shipment shipment) {
        Optional<Shipment> byId = shipmentRepository.findById(shipment.getId());
        Shipment foundShipment = byId.get();

        if (shipment.getAccountId() != null) {
            foundShipment.setAccountId(shipment.getAccountId());
        }
        if (shipment.getDeliveredDate() != null) {
            foundShipment.setDeliveredDate(shipment.getDeliveredDate());
        }
        if (shipment.getOrderId() != null) {
            foundShipment.setOrderId(shipment.getOrderId());
        }
        if (shipment.getShippedDate() != null) {
            foundShipment.setShippedDate(shipment.getShippedDate());
        }
        if (shipment.getOrderId() != null) {
            foundShipment.setOrderId(shipment.getOrderId());
        }
        if (shipment.getShippingAddressId() != null) {
            foundShipment.setShippingAddressId(shipment.getShippingAddressId());
        }

        return shipmentRepository.save(foundShipment);
    }
}
