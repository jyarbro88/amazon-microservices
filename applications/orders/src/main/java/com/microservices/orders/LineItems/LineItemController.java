package com.microservices.orders.LineItems;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/lineItems"})
public class LineItemController {

    @GetMapping
    public String sayHello(){
        return "hello from line items";
    }
}
