package com.microservices.orders.breakers;

import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.OrderAddressToDisplay;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AddressCircuitBreaker {

    private RestTemplate restTemplate;

    public AddressCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "addressFallBack")
    public OrderAddressToDisplay makeRestCallToGetOrderAddressToDisplay(Order foundOrder, Long shippingAddressId) {
        return restTemplate.getForObject("http://accounts-service/accounts/" + foundOrder.getAccountId() + "/address/" + shippingAddressId, OrderAddressToDisplay.class);
    }

    @SuppressWarnings("unused")
    public OrderAddressToDisplay addressFallBack(Order foundOrder, Long shippingAddressId){

        return new OrderAddressToDisplay();
    }
}
