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

            List<Address> addressList1 = new ArrayList<>();
            List<Address> addressList2 = new ArrayList<>();
            List<Address> addressList3 = new ArrayList<>();

            Address address = new Address("811 E Stone ct", "apt", "Addison", "IL", "60101", "USA");
            Address address2 = new Address("777 e howard", "apt 2", "Elmhurst", "IL", "60777", "USA");
            Address address3 = new Address("342 Woodridge Dr", "---", "Wooddale", "IL", "60765", "USA");
            Address address4 = new Address("111 E Washington", "Suite 3221", "Itasca", "IL", "60705", "USA");
            Address address5 = new Address("549 Supreme Dr.", "Suite 3B", "Bensenville", "IL", "60703", "USA");
            Address address6 = new Address("321 Hillside Dr.", "", "Wooddale", "IL", "60701", "USA");

            addressList1.add(address);
            addressList1.add(address4);
            addressList2.add(address2);
            addressList2.add(address6);
            addressList3.add(address5);
            addressList3.add(address3);

            addressRepository.saveAll(addressList1);
            addressRepository.saveAll(addressList2);
            addressRepository.saveAll(addressList3);

            Account account = new Account("Joe", "Yarbrough", "email@email.com", addressList1);
            Account account1 = new Account("Evan", "Freemon", "test@email.com", addressList2);
            Account account2 = new Account("Chris", "LaVine", "temp@email.com", addressList3);

            accountRepository.save(account);
            accountRepository.save(account1);
            accountRepository.save(account2);
        };
	}

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
