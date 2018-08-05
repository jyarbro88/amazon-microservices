package com.microservices.orders.circuits;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempShipment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.microservices.orders.Routes.SHIPMENT_SERVICE_URL;

@Component
public class ShipmentCircuitBreaker {

    private RestTemplate restTemplate;

    public ShipmentCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "shipmentFallBack")
    public TempShipment getShipmentInformation(LineItem lineItem) {

        return restTemplate.getForObject(SHIPMENT_SERVICE_URL + lineItem.getShipmentId(), TempShipment.class);
    }
    @SuppressWarnings("unused")
    public TempShipment shipmentFallBack(LineItem lineItem) {

        return new TempShipment();
    }

    @HystrixCommand(fallbackMethod = "newShipmentFallBack")
    public TempShipment postNewShipment(TempShipment shipment) {

        return restTemplate.postForObject(SHIPMENT_SERVICE_URL, shipment, TempShipment.class);
    }
    @SuppressWarnings("unused")
    public TempShipment newShipmentFallBack(TempShipment shipment) {
        return new TempShipment();
    }

    @HystrixCommand(fallbackMethod = "updateShipmentFallBack")
    public void updateShipment(TempShipment shipment) {
        restTemplate.put(SHIPMENT_SERVICE_URL, shipment);
    }
    @SuppressWarnings("unused")
    public void updateShipmentFallBack(TempShipment shipment) { }

}
