package com.microservices.accounts.accounts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/accounts"})
public class AccountController {

    @GetMapping(produces = {"application/json"})
    public String sayHello(){
        return "hello from accounts";
    }
}
