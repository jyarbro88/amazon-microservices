package com.microservices.shipments.circuits;

import com.microservices.shipments.models.temp.TempLineItem;
import com.microservices.shipments.models.temp.TempProduct;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderCircuitBreaker {

    private RestTemplate restTemplate;

    public OrderCircuitBreaker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "tempProductFallBack")
    public TempProduct[] getTempProductInfoForOrder(Long orderId){
        return restTemplate.getForObject("http://orders-service/orders/getProductInfo/" + orderId, TempProduct[].class);
    }

    public TempProduct[] tempProductFallBack(Long orderId){
        return null;
    }

    @HystrixCommand(fallbackMethod = "tempLineItemFallBack")
    public TempLineItem[] getTempLineItemsForOrder(Long orderId){
        return restTemplate.getForObject("http://orders-service/lineItems/" + orderId + "/lookup", TempLineItem[].class);
    }

    public TempLineItem[] tempLineItemFallBack(Long orderId){
        TempLineItem tempLineItem = new TempLineItem();

        return null;
    }
}
