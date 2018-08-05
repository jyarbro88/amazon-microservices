package com.microservices.accounts.services;

import com.microservices.accounts.models.Account;
import com.microservices.accounts.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findAccountById(Long id){
        return accountRepository.findById(id);
    }

    public Account createNewAccount(Account account){
        return accountRepository.save(account);
    }

    //Todo:  go through and check for values to update
    public Account updateAccount(Account account){
        Optional<Account> byId = accountRepository.findById(account.getId());
        Account foundAccount = byId.get();
        foundAccount.setAccountAddresses(account.getAccountAddresses());
        foundAccount.setEmail(account.getEmail());
        foundAccount.setFirstName(account.getFirstName());
        foundAccount.setLastName(account.getLastName());

        return accountRepository.save(foundAccount);
    }

    public void deleteAccount(Long id){
        accountRepository.deleteById(id);
    }
}
