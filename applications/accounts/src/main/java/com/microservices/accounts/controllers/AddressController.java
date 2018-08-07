package com.microservices.accounts.controllers;

import com.microservices.accounts.models.Address;
import com.microservices.accounts.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //Todo:  Link Account Address with Account when creating new, then remove from AccountApplication run file

    @GetMapping
    public Iterable<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping
    @RequestMapping("addresses/{id}")
    public Optional<Address> getAddressForId(
            @PathVariable(value = "id") Long addressId
    ){
        return addressService.findAddressById(addressId);
    }

    @GetMapping
    @RequestMapping("addresses/{id}/address/{addressId}")
    public Optional<Address> getSingleAddressWithId(
            @PathVariable(value = "id") Long accountId,
            @PathVariable(value = "addressId") Long addressId
    ){
        return addressService.findAddressById(addressId);
    }

    //Todo:  post address to accounts
    @PostMapping(value = "addresses/{id}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(
            @PathVariable(value = "id") Long accountId,
            @RequestBody Address address
    ){
        return addressService.save(address);
    }

    //Todo:  put new address on accounts record as well
    @PutMapping(value = "addresses/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Address updateAddress(
            @PathVariable(value = "id") Long accountId,
            @PathVariable(value = "addressId") Long addressId,
            @RequestBody Address address
    ){
        return addressService.updateAddress(address);
    }

    //Todo: remove the record in accounts tied to address
    @DeleteMapping(value = "addresses/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(
            @PathVariable(value = "id") Long accountId,
            @PathVariable(value = "addressId") Long addressId
    ){
        addressService.deleteAddress(addressId);
    }
}
