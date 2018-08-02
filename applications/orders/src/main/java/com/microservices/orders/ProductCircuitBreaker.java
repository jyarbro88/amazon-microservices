package com.microservices.orders;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.temp.TempProductObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductCircuitBreaker {

    private RestTemplate restTemplate;

    public ProductCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "productFallBack")
    public TempProductObject getTempProductObject(LineItem lineItem) {
        return restTemplate.getForObject("http://products-service/products/" + lineItem.getProductId(), TempProductObject.class);
    }

    @SuppressWarnings("unused")
    private TempProductObject productFallBack(LineItem lineItem){
        TempProductObject blankProduct = new TempProductObject();

        return blankProduct;
    }

}
