package com.microservices.accounts.services;

import com.microservices.accounts.models.Account;
import com.microservices.accounts.models.Address;
import com.microservices.accounts.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AddressService addressService;

    public AccountService(AccountRepository accountRepository, AddressService addressService) {
        this.accountRepository = accountRepository;
        this.addressService = addressService;
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public ResponseEntity save(Account account) {

        List<Address> accountAddresses = account.getAccountAddresses();

        if(accountAddresses == null){
            accountRepository.save(account);
        } else {
            for (Address accountAddress : accountAddresses) {
                addressService.save(accountAddress);
            }
            accountRepository.save(account);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public Account updateAccount(Account account) {

        Optional<Account> byId = accountRepository.findById(account.getId());
        Account foundAccount = byId.get();

        if (account.getAccountAddresses() != null) {
            foundAccount.setAccountAddresses(account.getAccountAddresses());
        }
        if (account.getEmail() != null) {
            foundAccount.setEmail(account.getEmail());
        }
        if (account.getFirstName() != null) {
            foundAccount.setFirstName(account.getFirstName());
        }
        if (account.getLastName() != null) {
            foundAccount.setLastName(account.getLastName());
        }

        return accountRepository.save(foundAccount);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
