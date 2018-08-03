package com.microservices.orders.breakers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ShipmentCircuitBreaker {

    private RestTemplate restTemplate;
    private String SHIPMENT_SERVICE_URL = "http://shipments-service/shipments/";

    public ShipmentCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "shipmentFallBack")
    public TempShipmentObject getShipmentInformation(LineItem lineItem) {

        return restTemplate.getForObject(SHIPMENT_SERVICE_URL + lineItem.getShipmentId(), TempShipmentObject.class);
    }

    @SuppressWarnings("unused")
    public TempShipmentObject shipmentFallBack(LineItem lineItem) {

        return new TempShipmentObject();
    }

    @HystrixCommand(fallbackMethod = "newShipmentFallBack")
    public TempShipmentObject postNewShipment(TempShipmentObject shipmentObjectsToPost) {

        return restTemplate.postForObject(SHIPMENT_SERVICE_URL, shipmentObjectsToPost, TempShipmentObject.class);
    }

    @SuppressWarnings("unused")
    public TempShipmentObject newShipmentFallBack(TempShipmentObject shipment) {
        return new TempShipmentObject();
    }

    @HystrixCommand(fallbackMethod = "updateShipmentFallBack")
    public void updateShipment(TempShipmentObject shipment) {
        restTemplate.put(SHIPMENT_SERVICE_URL, shipment);
    }

    @SuppressWarnings("unused")
    public void updateShipmentFallBack() {
    }


}
