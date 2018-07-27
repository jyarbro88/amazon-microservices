package com.microservices.orders.Orders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/orders"})
public class OrderController {

    @GetMapping(produces = {"application/json"})
    public String sayHello(){
        return "hello from orders";
    }


}
