package com.microservices.accounts;

import com.microservices.accounts.accounts.Account;
import com.microservices.accounts.accounts.AccountRepository;
import com.microservices.accounts.addresses.Address;
import com.microservices.accounts.addresses.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(AccountRepository accountRepository, AddressRepository addressRepository) {

		return (args) -> {
		    Account account = new Account("joe", "yarbrough", "email@email.com", null);

		    Address address = new Address("811 E Stone ct", "apt", "Addison", "IL", "60101", "USA");

		    accountRepository.save(account);
		    addressRepository.save(address);
        };
	}
}
