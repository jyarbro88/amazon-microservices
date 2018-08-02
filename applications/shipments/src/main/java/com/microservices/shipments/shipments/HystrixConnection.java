package com.microservices.shipments.shipments;

import com.microservices.shipments.models.TempProduct;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HystrixConnection {


    private RestTemplate restTemplate;

    public HystrixConnection(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "ordersServiceDefaultFallback")
    public TempProduct connectToOrdersServiceWithLineItemId(Long lineItemId) {
        return restTemplate.getForObject("http://orders-service/orders/getProductInfo/" + lineItemId, TempProduct.class);
    }

    private TempProduct ordersServiceDefaultFallback() throws JSONException {

        TempProduct tempProduct = new TempProduct();

        tempProduct.setName("");
        tempProduct.setDescription("order service currently down");

        return tempProduct;
    }
}
