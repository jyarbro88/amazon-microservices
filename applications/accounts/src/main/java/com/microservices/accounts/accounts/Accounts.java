package com.microservices.accounts.accounts;

import com.microservices.accounts.addresses.Addresses;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private List<Addresses> addressIds;


    public Accounts(String firstName, String lastName, String email, List<Addresses> addressIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressIds = addressIds;
    }
}
