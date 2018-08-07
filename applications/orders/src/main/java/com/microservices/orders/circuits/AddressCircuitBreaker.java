package com.microservices.orders.circuits;

import com.microservices.orders.models.display.DisplayOrderAddress;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.microservices.orders.Routes.ADDRESS_SERVICE_URL;

@Component
public class AddressCircuitBreaker {

    private RestTemplate restTemplate;

    public AddressCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "addressFallBack")
    public DisplayOrderAddress getOrderAddress(Long accountId, Long shippingAddressId) {
        return restTemplate.getForObject(ADDRESS_SERVICE_URL + shippingAddressId, DisplayOrderAddress.class);
    }
    @SuppressWarnings("unused")
    public DisplayOrderAddress addressFallBack(Long accountId, Long shippingAddressId){

        return new DisplayOrderAddress();
    }
}
