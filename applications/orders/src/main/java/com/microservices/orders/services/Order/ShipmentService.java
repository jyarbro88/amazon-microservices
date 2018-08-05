package com.microservices.orders.services.Order;

import com.microservices.orders.circuits.ShipmentCircuitBreaker;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempShipment;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    private ShipmentCircuitBreaker shipmentCircuitBreaker;

    public ShipmentService(ShipmentCircuitBreaker shipmentCircuitBreaker) {
        this.shipmentCircuitBreaker = shipmentCircuitBreaker;
    }

    TempShipment buildAndPostNewShipment(Order order) {

        TempShipment shipmentObjectToSend = new TempShipment();

        shipmentObjectToSend.setOrderId(order.getId());
        shipmentObjectToSend.setShippingAddressId(order.getShippingAddressId());
        shipmentObjectToSend.setAccountId(order.getAccountId());

        return shipmentCircuitBreaker.postNewShipment(shipmentObjectToSend);
    }
}
