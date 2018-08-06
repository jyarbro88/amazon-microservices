package com.microservices.accounts.services;

import com.microservices.accounts.models.Address;
import com.microservices.accounts.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> findAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {

        Optional<Address> addressById = addressRepository.findById(address.getId());
        Address foundAddress = addressById.get();

        if (address.getAddressOne() != null) {
            foundAddress.setAddressOne(address.getAddressOne());
        }
        if (address.getAddressTwo() != null) {
            foundAddress.setAddressTwo(address.getAddressTwo());
        }
        if (address.getCity() != null) {
            foundAddress.setCity(address.getCity());
        }
        if (address.getZipCode() != null) {
            foundAddress.setZipCode(address.getZipCode());
        }
        if (address.getCountry() != null) {
            foundAddress.setCountry(address.getCountry());
        }
        if (address.getState() != null) {
            foundAddress.setState(address.getState());
        }

        return addressRepository.save(foundAddress);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
