package com.microservices.orders.breakers;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProduct;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductCircuitBreaker {

    //Todo:  Create master template of all http routes to the base of each application

    private RestTemplate restTemplate;

    public ProductCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "productFallBack")
    public TempProduct getTempProductObject(LineItem lineItem) {
        return restTemplate.getForObject("http://products-service/products/" + lineItem.getProductId(), TempProduct.class);
    }

    @SuppressWarnings("unused")
    private TempProduct productFallBack(LineItem lineItem){

        return new TempProduct();
    }

}
