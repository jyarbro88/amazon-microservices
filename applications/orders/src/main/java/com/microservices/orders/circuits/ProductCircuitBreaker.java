package com.microservices.orders.circuits;

import com.microservices.orders.models.temp.TempProduct;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.microservices.orders.Routes.PRODUCTS_SERVICE_URL;

@Component
public class ProductCircuitBreaker {

    private RestTemplate restTemplate;

    public ProductCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "productFallBack")
    public TempProduct getTempProductWithId(Long productId) {
        return restTemplate.getForObject( PRODUCTS_SERVICE_URL + productId, TempProduct.class);
    }

    @SuppressWarnings("unused")
    private TempProduct productFallBack(Long productId){

        return new TempProduct();
    }

}
