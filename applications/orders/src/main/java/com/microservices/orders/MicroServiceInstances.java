package com.microservices.orders;

import org.springframework.stereotype.Component;

@Component
public class MicroServiceInstances {


    public String getAllShipmentsForOrderId(Long orderId){
        return "http://shipments-service/shipments/" + orderId;
    }

    public String getOrderShippingAddress(Long accountIdToSearch, Long addressIdToSearch){

        return "http://accounts-service/accounts/" + accountIdToSearch + "/accountAddresses/" + addressIdToSearch;
    }

}
