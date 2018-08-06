package com.microservices.accounts.controllers;

import com.microservices.accounts.models.Account;
import com.microservices.accounts.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Iterable<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity save(
            @RequestBody Account account
    ){
        return accountService.save(account);
    }

    @PutMapping
    public Account updateAccount(
            @RequestBody Account account
    ){
        return accountService.updateAccount(account);
    }

    @DeleteMapping(value = "accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @PathVariable(value = "id") Long accountId
    ){
        accountService.deleteAccount(accountId);
    }
}
