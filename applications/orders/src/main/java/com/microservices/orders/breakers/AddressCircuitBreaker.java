package com.microservices.orders.breakers;

import com.microservices.orders.models.Order;
import com.microservices.orders.models.display.DisplayOrderAddress;
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
    public DisplayOrderAddress makeRestCallToGetOrderAddressToDisplay(Order foundOrder, Long shippingAddressId) {
        return restTemplate.getForObject("http://accounts-service/accounts/" + foundOrder.getAccountId() + "/address/" + shippingAddressId, DisplayOrderAddress.class);
    }

    @SuppressWarnings("unused")
    public DisplayOrderAddress addressFallBack(Order foundOrder, Long shippingAddressId){

        return new DisplayOrderAddress();
    }
}
