package com.microservices.accounts;

import com.microservices.accounts.models.Account;
import com.microservices.accounts.repositories.AccountRepository;
import com.microservices.accounts.models.Address;
import com.microservices.accounts.repositories.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabase(AccountRepository accountRepository, AddressRepository addressRepository) {

		return (args) -> {

            List<Address> addressList = new ArrayList<>();

            Address address = new Address("811 E Stone ct", "apt", "Addison", "IL", "60101", "USA");
            Address address2 = new Address("777 e howard", "apt 2", "Elmhurst", "IL", "60777", "USA");
            Address address3 = new Address("342 Woodridge Dr", "---", "Wooddale", "IL", "60765", "USA");

            addressList.add(address);
            addressList.add(address2);
            addressList.add(address3);
            addressRepository.save(address);
            addressRepository.save(address2);
            addressRepository.save(address3);


            Account account = new Account("joe", "yarbrough", "email@email.com", addressList);


            accountRepository.save(account);
        };
	}

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
