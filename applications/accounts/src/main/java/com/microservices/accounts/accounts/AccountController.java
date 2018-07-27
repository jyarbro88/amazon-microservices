package com.microservices.accounts.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class AccountController {

//    @Autowired
//    private AccountRepository accountRepository;
//
//    @GetMapping(produces = "application/json")
//    public String sayHello(){
//        return "hello";
//    }
//
//    @GetMapping(value = "/all")
//    @ResponseBody
//    public List<Account> showAllAccounts(){
//        return accountRepository.findAll();
//    }
//
//    @GetMapping(value = "/{accountId}", produces = "application/json")
//    public Optional<Account> searchById(
//            @Valid
//            @PathVariable(value = "accountId") Long accountId
//    ){
//
//        return accountRepository.findById(accountId);
//    }

}
