package com.microservices.accounts.accounts;

import com.microservices.accounts.addresses.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private List<Address> addressIds;

    public Account(){

    }

    public Account(String firstName, String lastName, String email, List<Address> addressIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressIds = addressIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(List<Address> addressIds) {
        this.addressIds = addressIds;
    }
}
