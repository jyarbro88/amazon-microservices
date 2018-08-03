package com.microservices.orders.breakers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void postNewShipment(List<TempShipmentObject> shipmentObjectsToPost){

//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("Content-Type", "application/json");
//
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        for (TempShipmentObject tempShipmentObject : shipmentObjectsToPost) {

            restTemplate.postForObject(SHIPMENT_SERVICE_URL, tempShipmentObject, TempShipmentObject.class);
//            ResponseEntity<String> response = restTemplate.postForEntity(SHIPMENT_SERVICE_URL, tempShipmentObject, String.class);
//            HttpStatus returnStatus = response.getStatusCode();
//            String status = response.getBody();
        }
    }

    @SuppressWarnings("unused")
    public void newShipmentFallBack(List<TempShipmentObject> shipment){


    }
}
