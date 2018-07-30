package com.microservices.accounts.accounts;

import com.microservices.accounts.addresses.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    private List<Address> addressList = new ArrayList<>();

    @InjectMocks
    private AccountController accountController;

//    @Test
//    public void testSave(){
//
//        Address address = new Address("811 E Stone ct", "apt", "Addison", "IL", "60101", "USA");
//        Address address2 = new Address("777 e howard", "apt 2", "Elmhurst", "IL", "60777", "USA");
//        Address address3 = new Address("342 Woodridge Dr", "---", "Wooddale", "IL", "60765", "USA");
//
//        addressList.add(address);
//        addressList.add(address2);
//        addressList.add(address3);
//
//        Account account = new Account("joe", "yarbrough", "email@email.com", addressList);
//
//        accountRepository.save(account);
//
//        when(restTemplate.getForObject("/accounts/1", Account.class)).thenReturn(account);
//
//
//    }

    @Test
    public void findAddressesByAccountId(){

    }

}