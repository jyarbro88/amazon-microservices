package com.microservices.orders;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShipmentCircuitBreaker {

    private RestTemplate restTemplate;

    public ShipmentCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "shipmentFallBack")
    public TempShipmentObject getShipmentInformation(LineItem lineItem) {
        return restTemplate.getForObject("http://shipments-service/shipments/" + lineItem.getShipmentId(), TempShipmentObject.class);
    }

    @SuppressWarnings("unused")
    public TempShipmentObject shipmentFallBack(LineItem lineItem){
        TempShipmentObject blankShipment = new TempShipmentObject();

        return blankShipment;
    }
}
