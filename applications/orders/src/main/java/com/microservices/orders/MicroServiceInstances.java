package com.microservices.orders;

import org.springframework.stereotype.Component;

@Component
public class MicroServiceInstances {


    public String getShipmentForLineItemId(Long lineItemId){
        return "http://shipments-service/shipments/lineItems/" + lineItemId;
    }

    public String getOrderShippingAddress(Long accountIdToSearch, Long addressIdToSearch){

        return "http://accounts-service/accounts/" + accountIdToSearch + "/accountAddresses/" + addressIdToSearch;
    }

    public String getProductName(Long productId) {

        return "http://products-service/products/" + productId;
    }
}
