package com.microservices.shipments.shipments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/shipments"})
public class ShipmentsController {

    @GetMapping(produces = {"application/json"})
    public String sayHello(){
        return "hello from shipments";
    }
}
