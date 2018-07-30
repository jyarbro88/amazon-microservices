package com.microservices.accounts.accounts;

import com.microservices.accounts.addresses.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private List<Address> accountAddresses;

    public Account(String firstName, String lastName, String email, List<Address> accountAddresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountAddresses = accountAddresses;
    }
}
